@file:Suppress("PropertyName", "SpellCheckingInspection")

import app.ultradev.hytalegradle.manifest.ManifestExtension


plugins {
    kotlin("jvm") version "2.3.0"
    id("com.gradleup.shadow") version "9.3.1"
    id("app.ultradev.hytalegradle") version "1.6.7"
}

group = "dev.lexih.mod"
version = "0.0.1"

repositories { mavenCentral() }
dependencies { testImplementation(kotlin("test")) }

hytale {
    patchline = "pre-release"
    allowOp = true
    manifest {
        group = "Lexih"
        name = "TestPlugin"
        version = project.version.toString()
        mainClass = project.group.toString() + '.' + name.get()
        description = "Example plugin"
        website = "https://lexih.dev"
        author("Lexih")
        includesAssetPack = true
    }
}

kotlin { jvmToolchain(25) }
tasks.test { useJUnitPlatform() }
