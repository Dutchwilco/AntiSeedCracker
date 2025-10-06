package nl.dutchcoding.antiseedcracker.managers;

import nl.dutchcoding.antiseedcracker.AntiSeedCracker;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {

    private final AntiSeedCracker plugin;
    private FileConfiguration config;

    public ConfigManager(AntiSeedCracker plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        // Save default config if not exists
        if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
            plugin.saveResource("config.yml", false);
        }
        if (!new File(plugin.getDataFolder(), "messages.yml").exists()) {
            plugin.saveResource("messages.yml", false);
        }

        // Reload config
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public boolean isEnabled() {
        return config.getBoolean("settings.enabled", true);
    }

    public boolean isDebug() {
        return config.getBoolean("settings.debug", false);
    }

    public boolean shouldBlockSeedCommand() {
        return config.getBoolean("protection.block-seed-command", true);
    }

    public boolean shouldBlockDebugCommands() {
        return config.getBoolean("protection.block-debug-commands", true);
    }

    public boolean shouldBlockLocateBiome() {
        return config.getBoolean("protection.block-locatebiome", true);
    }

    public boolean shouldBlockLocate() {
        return config.getBoolean("protection.block-locate", true);
    }

    public boolean shouldBlockSeedPackets() {
        return config.getBoolean("protection.block-seed-packets", true);
    }

    public boolean shouldBlockExecuteSeed() {
        return config.getBoolean("protection.block-execute-seed", true);
    }

    public List<String> getBlockedCommands() {
        return config.getStringList("blocked-commands");
    }

    public List<String> getProtectedWorlds() {
        return config.getStringList("protected-worlds");
    }

    public boolean isWorldProtected(String worldName) {
        List<String> protectedWorlds = getProtectedWorlds();
        return protectedWorlds.isEmpty() || protectedWorlds.contains(worldName);
    }
}
