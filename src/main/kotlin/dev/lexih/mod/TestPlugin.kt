package dev.lexih.mod

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit

@Suppress("unused")
class TestPlugin(init: JavaPluginInit) : JavaPlugin(init) {
    companion object {
        private val logger = HytaleLogger.forEnclosingClass()
    }
    init {
        TestPlugin.logger.atInfo().log("Hello from ${this.name} version ${this.manifest.version}")
    }

    override fun setup() {
        TestPlugin.logger.atInfo().log("Setting up ${this.name}")
    }
}