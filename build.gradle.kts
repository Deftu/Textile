import dev.deftu.gradle.utils.VersionType

plugins {
    java
    kotlin("jvm")
    id("dev.deftu.gradle.multiversion")
    id("dev.deftu.gradle.tools")
    id("dev.deftu.gradle.tools.resources")
    id("dev.deftu.gradle.tools.minecraft.api")
    id("dev.deftu.gradle.tools.minecraft.loom")
    id("dev.deftu.gradle.tools.publishing.maven")
    id("dev.deftu.gradle.tools.minecraft.releases")
}

toolkitLoomApi.setupTestClient()
toolkitMavenPublishing.forceLowercase.set(true)
kotlin.explicitApi()

toolkitReleases {
    detectVersionType.set(true)
    modrinth {
        projectId.set("T0Zb6DLv")
    }
}
