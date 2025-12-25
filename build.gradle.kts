plugins {
    alias(libs.plugins.fabric.loom) apply false
    alias(libs.plugins.mod.publish)
    id("maven-publish")
    base
}

version = properties["mod_version"] as String + "+mc." + libs.versions.minecraft.get()
group = properties["maven_group"] as String
base.archivesName = properties["archives_base_name"] as String

val moduleJars: Configuration by configurations.creating {
    isTransitive = false
    isCanBeResolved = true
}

val moduleSourceJars: Configuration by configurations.creating {
    isTransitive = false
    isCanBeResolved = true
    extendsFrom(moduleJars)
    attributes {
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named(DocsType.SOURCES))
    }
}

val manifestJar: Configuration by configurations.creating {
    isTransitive = false
    isCanBeResolved = true
}

dependencies {
    moduleJars(project(":common"))
    moduleJars(project(":fabric"))
    moduleJars(project(":neoforge"))
    manifestJar(project(":fabric"))
}

val mergeJar by tasks.registering(Zip::class) {
    dependsOn(moduleJars)
    archiveExtension = "jar"
    destinationDirectory = layout.buildDirectory.dir("libs")

    moduleJars.resolve().forEach {
        from(zipTree(it)) {
            exclude("META-INF/MANIFEST.MF")
        }
    }

    from(zipTree(manifestJar.resolve().single())) {
        include("META-INF/MANIFEST.MF")
    }

    from("LICENSE")
}

val mergeSourcesJar by tasks.registering(Zip::class) {
    dependsOn(moduleSourceJars)
    archiveExtension = "jar"
    archiveClassifier = "sources"
    destinationDirectory = layout.buildDirectory.dir("libs")

    moduleSourceJars.resolve().forEach {
        from(zipTree(it))
    }

    exclude("META-INF/MANIFEST.MF")

    from("LICENSE")
}

tasks.build {
    dependsOn(mergeJar)
    dependsOn(mergeSourcesJar)
}

publishMods {
    val mcVersion = libs.versions.minecraft.get()

    file = mergeJar.flatMap { it.archiveFile }
    additionalFiles.from(mergeSourcesJar)

    displayName = "v$version [$mcVersion]"
    changelog = providers.fileContents(layout.projectDirectory.file("changelog/$version+$mcVersion")).asText

    type.set(providers.environmentVariable("RELEASE_TYPE").map { me.modmuss50.mpp.ReleaseType.of(it) })
    modLoaders.addAll("fabric", "neoforge")

    dryRun = providers.gradleProperty("publish_dry_run").isPresent

    modrinth {
        projectId = "1BduonQp"
        accessToken.set(providers.environmentVariable("MODRINTH_TOKEN"))

        requires("fabric-api")
        minecraftVersions.add(mcVersion)
    }

    curseforge {
        projectId = "847463"
        accessToken.set(providers.environmentVariable("CURSEFORGE_TOKEN"))

        requires("fabric-api")
        minecraftVersions.add(mcVersion)
    }

    github {
        repository = "MattiDragon/CustomDefaultWorldPreset"
        accessToken.set(providers.environmentVariable("GITHUB_TOKEN"))

        commitish.set(providers.environmentVariable("GITHUB_BRANCH"))
        tagName.set(version.map { it.replace('+', '-') })
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifact(mergeJar)
            artifact(mergeSourcesJar)
        }
    }
}
