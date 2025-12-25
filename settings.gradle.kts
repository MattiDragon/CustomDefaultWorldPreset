pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

fun subproject(name: String) {
    include(":$name")
    project(":$name").projectDir = file("modules/$name")
}

subproject("common")
subproject("fabric")
subproject("neoforge")
