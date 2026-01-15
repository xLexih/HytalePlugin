@file:Suppress("PropertyName", "SpellCheckingInspection", "LocalVariableName")

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    java
    kotlin("jvm") version "2.3.0"
    id("com.gradleup.shadow") version "9.3.1"
}

val java_version: String by project
val patch_line: String by project
val includes_pack: String by project
val load_user_mods: String by project

val hytale_home: String = providers.gradleProperty("hytale_home")
    .map { it.replace("\${user.home}", System.getProperty("user.home")) }
    .orElse(provider {
        val user_home = System.getProperty("user.home")
        System.getenv("APPDATA")?.let { "$it/Hytale" }
            ?: "$user_home/.var/app/com.hypixel.HytaleLauncher/data/Hytale".takeIf { file(it).exists() }
            ?: "$user_home/.local/share/Hytale"
    }).get()

val hytale_install = "$hytale_home/install/$patch_line/package/game/latest"
val server_run_dir = layout.projectDirectory.dir("run")

java {
    toolchain.languageVersion = JavaLanguageVersion.of(java_version)
    withSourcesJar()
    withJavadocJar()
}

kotlin { compilerOptions { jvmTarget = JvmTarget.fromTarget(java_version) } }

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly(files("$hytale_install/Server/HytaleServer.jar"))
    implementation(kotlin("stdlib"))
}

sourceSets.configureEach { resources.exclude(".idea/**") }

tasks {
    withType<AbstractCopyTask> { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }

    val updatePluginManifest by registering {
        val manifest = layout.projectDirectory.file("src/main/resources/manifest.json").asFile
        inputs.property("version", version)
        inputs.property("includes_pack", includes_pack)
        inputs.file(manifest).optional()
        outputs.file(manifest)

        doLast {
            check(manifest.exists()) { "manifest.json not found at ${manifest.path}" }
            @Suppress("UNCHECKED_CAST")
            val json = JsonSlurper().parseText(manifest.readText()) as MutableMap<String, Any>
            json["Name"] = rootProject.name
            json["Version"] = version.toString()
            json["IncludesAssetPack"] = includes_pack.toBoolean()
            manifest.writeText(JsonOutput.prettyPrint(JsonOutput.toJson(json)))
        }
    }

    processResources { dependsOn(updatePluginManifest) }
    named<Jar>("sourcesJar") { dependsOn(updatePluginManifest) }
    javadoc { enabled = false }

    shadowJar {
        archiveClassifier = ""
        mergeServiceFiles()
    }

    assemble { dependsOn(shadowJar) }

    register<JavaExec>("runServer") {
        group = "hytale"
        description = "Run Hytale server with plugin"
        mainClass = "com.hypixel.hytale.Main"
        classpath = files("$hytale_install/Server/HytaleServer.jar")
        workingDir = server_run_dir.asFile
        standardInput = System.`in`
        jvmArgs(
            "-XX:+UnlockDiagnosticVMOptions",
            "--enable-native-access=ALL-UNNAMED",
            "--add-opens=java.base/sun.misc=ALL-UNNAMED"
        )
        args = buildList {
            add("--allow-op")
            add("--assets=$hytale_install/Assets.zip")
            if (includes_pack.toBoolean()) add("--mods=${layout.buildDirectory.dir("libs").get().asFile.absolutePath}")
            if (load_user_mods.toBoolean()) add("--mods=$hytale_home/UserData/Mods")
        }
        dependsOn(shadowJar)
        doFirst { workingDir.mkdirs() }
    }

    clean { delete(server_run_dir) }
}