plugins {
    alias(libs.plugins.fabric.loom) apply false
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

    moduleJars.resolve().forEach {
        from(zipTree(it)) {
            exclude("META-INF/MANIFEST.MF")
        }
    }

    from(zipTree(manifestJar.resolve().single())) {
        include("META-INF/MANIFEST.MF")
    }
}

val mergeSourcesJar by tasks.registering(Zip::class) {
    dependsOn(moduleSourceJars)
    archiveExtension = "jar"
    archiveClassifier = "sources"

    moduleSourceJars.resolve().forEach {
        from(zipTree(it))
    }

    exclude("META-INF/MANIFEST.MF")
}

tasks.build {
    dependsOn(mergeJar)
    dependsOn(mergeSourcesJar)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifact(mergeJar)
            artifact(mergeSourcesJar)
        }
    }
}
