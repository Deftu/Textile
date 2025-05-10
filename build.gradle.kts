import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform") version("2.0.10")
    val dgtVersion = "2.34.0"
    id("dev.deftu.gradle.tools") version(dgtVersion)
    id("dev.deftu.gradle.tools.publishing.maven") version(dgtVersion)
}

kotlin {
    explicitApi()

    // --- JVM (Desktop, Android, Server) ---
    jvm {
        // Compile to Java 8 bytecode
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }

        withJava()
        withSourcesJar()
    }

    // --- JavaScript (Browser, Node.js) ---
    js(IR) {
        generateTypeScriptDefinitions()
        binaries.library()
        browser()
        nodejs()
    }

    // --- WebAssembly (Experimental) ---
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        generateTypeScriptDefinitions()
        binaries.library()
        browser()
    }

    // --- Native (Commonly Used Platforms) ---
    linuxX64()       // Desktop Linux
    mingwX64()       // Windows native
    macosX64()       // macOS Intel
    macosArm64()     // macOS Apple Silicon

    iosArm64()       // iOS devices
    iosSimulatorArm64() // iOS simulator for Apple Silicon

    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
