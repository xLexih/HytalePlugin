@file:Suppress("PropertyName", "SpellCheckingInspection")

plugins {
    kotlin("jvm") version "2.3.0"
    id("com.gradleup.shadow") version "9.3.1"
    id("app.ultradev.hytalegradle") version "1.5.3"
}

group = "dev.lexih.mod"
version = "0.0.1"

repositories { mavenCentral() }
dependencies { testImplementation(kotlin("test")) }

hytale {
    patchline = "pre-release"
    allowOp = true
}

kotlin { jvmToolchain(25) }
tasks.test { useJUnitPlatform() }