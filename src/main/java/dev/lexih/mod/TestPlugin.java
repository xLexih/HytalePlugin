package dev.lexih.mod;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

@SuppressWarnings("unused")
public class TestPlugin extends JavaPlugin {
    private static final HytaleLogger logger = HytaleLogger.forEnclosingClass();

    public TestPlugin(JavaPluginInit init) {
        super(init);
        logger.atInfo().log("Hella from %s version %s".formatted(getName(), getManifest().getVersion()));
    }

    @Override
    public void setup() {
        logger.atInfo().log("Setting up " + getName());
    }
}