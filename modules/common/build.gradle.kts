plugins {
    alias(libs.plugins.fabric.loom)
}

version = rootProject.version
group = rootProject.group
base.archivesName = rootProject.base.archivesName.map { "$it-common" }

dependencies {
    minecraft(libs.minecraft)

    implementation(libs.mixin)
    implementation(libs.mixin.extras)
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
