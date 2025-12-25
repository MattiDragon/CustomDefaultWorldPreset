plugins {
    alias(libs.plugins.fabric.loom)
}

version = rootProject.version
group = rootProject.group
base.archivesName = rootProject.base.archivesName.map { "$it-fabric" }

val commonProject by configurations.creating {
    isTransitive = false
}
configurations.implementation {
    extendsFrom(commonProject)
}

dependencies {
    minecraft(libs.minecraft)
    implementation(libs.fabric.loader)

    implementation(libs.fabric.api)

    commonProject(project(":common"))
}

loom {
    runs {
        configureEach {
            ideConfigGenerated(true)
            runDir("run/${name}")
            configName = "Fabric ${name.replaceFirstChar(Char::uppercaseChar)}"
        }
    }

    mods.register("custom_default_world_preset") {
        sourceSet(sourceSets.main.get())
        configuration(commonProject)
    }
}

tasks.processResources {
    val properties = mapOf(
        "version" to project.version,
        "loader_version" to libs.versions.fabric.loader.get(),
        "api_version" to libs.versions.fabric.api.get()
    )

    inputs.properties(properties)
    filesMatching("fabric.mod.json") {
        expand(properties)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
}

java {
    withSourcesJar()
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${base.archivesName}" }
    }
}
