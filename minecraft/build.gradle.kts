import dev.deftu.gradle.utils.version.MinecraftVersions
import dev.deftu.gradle.utils.includeOrShade

plugins {
    java
    kotlin("jvm")
    id("dev.deftu.gradle.multiversion")
    id("dev.deftu.gradle.tools")
    id("dev.deftu.gradle.tools.resources")
    id("dev.deftu.gradle.tools.bloom")
    id("dev.deftu.gradle.tools.publishing.maven")
    id("dev.deftu.gradle.tools.minecraft.loom")
    id("dev.deftu.gradle.tools.minecraft.api")
    id("dev.deftu.gradle.tools.minecraft.releases")
    id("dev.deftu.gradle.tools.shadow")
}

kotlin.explicitApi()
toolkitMavenPublishing.forceLowercase.set(true)
toolkitLoomApi.setupTestClient()
toolkitMultiversion.moveBuildsToRootProject.set(true)
if (mcData.isForgeLike && mcData.version >= MinecraftVersions.VERSION_1_16_5) {
    toolkitLoomHelper.useKotlinForForge()
}

toolkitReleases {
    detectVersionType.set(true)

    modrinth {
        projectId.set("T0Zb6DLv")
    }

    curseforge {
        projectId.set("1215303")
    }
}

if (mcData.isNeoForge && mcData.version > MinecraftVersions.VERSION_1_21_8) {
    repositories {
        maven {
            name = "Maven for PR #2639" // https://github.com/neoforged/NeoForge/pull/2639
            url = uri("https://prmaven.neoforged.net/NeoForge/pr2639")
            content {
                includeModule("net.neoforged", "neoforge")
                includeModule("net.neoforged", "testframework")
            }
        }
    }
}

dependencies {
    api(includeOrShade(project(":")) {
        isTransitive = false
    })
}
