import dev.deftu.gradle.utils.MinecraftVersion
import dev.deftu.gradle.utils.includeOrShade

plugins {
    java
    kotlin("jvm")
    id("dev.deftu.gradle.multiversion")
    id("dev.deftu.gradle.tools")
    id("dev.deftu.gradle.tools.resources")
    id("dev.deftu.gradle.tools.bloom")
    id("dev.deftu.gradle.tools.shadow")
    id("dev.deftu.gradle.tools.publishing.maven")
    id("dev.deftu.gradle.tools.minecraft.loom")
    id("dev.deftu.gradle.tools.minecraft.api")
    id("dev.deftu.gradle.tools.minecraft.releases")
}

kotlin.explicitApi()
toolkitMavenPublishing.forceLowercase.set(true)
toolkitLoomApi.setupTestClient()
toolkitMultiversion.moveBuildsToRootProject.set(true)
if (mcData.isForgeLike && mcData.version >= MinecraftVersion.VERSION_1_16_5) {
    toolkitLoomHelper.useKotlinForForge()
}

toolkitReleases {
    detectVersionType.set(true)
    modrinth {
        projectId.set("T0Zb6DLv")
    }
}

dependencies {
    api(includeOrShade(project(":api")) {
        isTransitive = false
    })

    if (mcData.isFabric) {
        modImplementation("net.fabricmc.fabric-api:fabric-api:${mcData.dependencies.fabric.fabricApiVersion}")
        modImplementation(mcData.dependencies.fabric.modMenuDependency)
    }
}
