import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform") version("2.0.10")
    val dgtVersion = "2.33.3"
    id("dev.deftu.gradle.tools") version(dgtVersion)
    id("dev.deftu.gradle.tools.publishing.maven") version(dgtVersion)
}

kotlin {
    explicitApi()

    // --- JVM (Desktop, Android, Server) ---
    jvm()

    // --- JavaScript (Browser, Node.js) ---
    js(IR) {
        generateTypeScriptDefinitions()
        browser()
        nodejs()
        binaries.library()
    }

    // --- WebAssembly (Experimental) ---
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        generateTypeScriptDefinitions()
        browser()
        binaries.library()
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
