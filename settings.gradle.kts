pluginManagement {
    repositories {
        // Snapshots
        maven("https://maven.deftu.dev/snapshots")
        maven("https://s01.oss.sonatype.org/content/groups/public/")

        // Repositories
        maven("https://maven.deftu.dev/releases")
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://jitpack.io/")

        // Default repositories
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }

    plugins {
        id("dev.deftu.gradle.multiversion-root") version("2.34.0")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.8.+")
}

rootProject.name = extra["project.name"]?.toString() ?: throw IllegalStateException("project.name is not defined")

// Setup Minecraft
include(":minecraft")
project(":minecraft").buildFileName = "root.gradle.kts"
listOf(
    "1.8.9-forge",
    "1.8.9-fabric",

    "1.12.2-forge",
    "1.12.2-fabric",

    "1.16.5-forge",
    "1.16.5-fabric",

    "1.17.1-forge",
    "1.17.1-fabric",

    "1.18.2-forge",
    "1.18.2-fabric",

    "1.19.2-forge",
    "1.19.2-fabric",

    "1.19.4-forge",
    "1.19.4-fabric",

    "1.20.1-forge",
    "1.20.1-fabric",

    "1.20.4-forge",
    "1.20.4-neoforge",
    "1.20.4-fabric",

    "1.20.6-neoforge",
    "1.20.6-fabric",

    "1.21.1-neoforge",
    "1.21.1-fabric",

    "1.21.2-neoforge",
    "1.21.2-fabric",

    "1.21.3-neoforge",
    "1.21.3-fabric",

    "1.21.4-neoforge",
    "1.21.4-fabric",

    "1.21.5-neoforge",
    "1.21.5-fabric"
).forEach { version ->
    include(":minecraft:$version")
    project(":minecraft:$version").apply {
        projectDir = file("minecraft/versions/$version")
        buildFileName = "../../build.gradle.kts"
    }
}
