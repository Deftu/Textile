import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform") version("2.0.20")
    val dgtVersion = "2.39.0"
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

    // --- Native (Desktop + Apple ecosystem) ---
    linuxX64()         // Desktop Linux
    mingwX64()         // Windows native
    macosX64()         // macOS Intel (x86_64)
    macosArm64()       // macOS Apple Silicon (ARM64)

    // --- iOS ---
    iosArm64()         // iOS physical devices (ARM64)
    iosSimulatorArm64()// iOS simulator on Apple Silicon (ARM64)

    // --- tvOS ---
    tvosArm64()        // tvOS physical devices (Apple TV)
    tvosX64()          // tvOS simulator on Intel
    tvosSimulatorArm64() // tvOS simulator on Apple Silicon (ARM64)

    // --- watchOS ---
    watchosArm64()       // watchOS physical devices (Apple Watch)
    watchosX64()         // watchOS simulator on Intel
    watchosSimulatorArm64() // watchOS simulator on Apple Silicon (ARM64)

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
