import dev.deftu.gradle.utils.VersionType

plugins {
    java
    kotlin("jvm")
    id("dev.deftu.gradle.multiversion")
    id("dev.deftu.gradle.tools")
    id("dev.deftu.gradle.tools.resources")
    id("dev.deftu.gradle.tools.maven-publishing")
    id("dev.deftu.gradle.tools.minecraft.api")
    id("dev.deftu.gradle.tools.minecraft.loom")
    id("dev.deftu.gradle.tools.minecraft.releases")
}

toolkitLoomApi.setupTestClient()
kotlin.explicitApi()

toolkitMavenPublishing {
    artifactName.set(modData.name.lowercase())
}

toolkitReleases {
    if (modData.version.startsWith("0.")) {
        versionType.set(VersionType.BETA)
    }

    modrinth {
        projectId.set("T0Zb6DLv")
    }
}
