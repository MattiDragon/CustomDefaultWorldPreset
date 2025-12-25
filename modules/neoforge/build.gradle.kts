plugins {
    alias(libs.plugins.neoforge.mdg)
    `java-library`
}

version = rootProject.version
group = rootProject.group
base.archivesName = rootProject.base.archivesName.map { "$it-neoforge" }

val commonProject by configurations.creating {
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
    }
}

dependencies {
    commonProject(project(":common"))
}
