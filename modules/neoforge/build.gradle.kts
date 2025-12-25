plugins {
    alias(libs.plugins.neoforge.mdg)
    `java-library`
}

version = rootProject.version
group = rootProject.group
base.archivesName = rootProject.base.archivesName.map { "$it-neoforge" }

val commonProject: Configuration by configurations.creating {
    isTransitive = false
}
configurations.implementation {
    extendsFrom(commonProject)
}

neoForge {
    version = libs.versions.neoforge.get()

    runs {
        register("client") {
            client()
        }

        register("server") {
            server()
            programArgument("--nogui")
        }

        configureEach {
            gameDirectory = file("run/${name}")
            ideName = "NeoForge ${name.replaceFirstChar(Char::uppercaseChar)}"
        }
    }

    mods.register("custom_default_world_preset") {
        sourceSet(sourceSets.main.get())
        // I don't understand why this works, but it does
        sourceSet(project(":common").sourceSets["main"])
    }
}

dependencies {
    commonProject(project(":common"))
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
}

java {
    withSourcesJar()
}

tasks.processResources {
    val properties = mapOf(
        "version" to project.version,
        "neo_version" to libs.versions.neoforge.get()
    )

    inputs.properties(properties)
    filesMatching("META-INF/neoforge.mods.toml") {
        expand(properties)
    }
}