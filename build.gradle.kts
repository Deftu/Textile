plugins {
    java
    kotlin("jvm")
    id("xyz.deftu.gradle.multiversion")
    id("xyz.deftu.gradle.tools")
    id("xyz.deftu.gradle.tools.maven-publishing")
    id("xyz.deftu.gradle.tools.minecraft.api")
    id("xyz.deftu.gradle.tools.minecraft.loom")
    id("xyz.deftu.gradle.tools.minecraft.releases")
}

toolkitLoomApi.setupTestClient()
kotlin.explicitApi()
