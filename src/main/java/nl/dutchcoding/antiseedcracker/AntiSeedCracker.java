package nl.dutchcoding.antiseedcracker;

import nl.dutchcoding.antiseedcracker.commands.MainCommand;
import nl.dutchcoding.antiseedcracker.listeners.CommandListener;
import nl.dutchcoding.antiseedcracker.listeners.PacketListener;
import nl.dutchcoding.antiseedcracker.managers.ConfigManager;
import nl.dutchcoding.antiseedcracker.managers.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiSeedCracker extends JavaPlugin {

    private static AntiSeedCracker instance;
    private ConfigManager configManager;
    private MessageManager messageManager;
    private PacketListener packetListener;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.messageManager = new MessageManager(this);

        // Load configurations
        configManager.loadConfigs();

        // Register listeners
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        // Initialize packet listener if enabled
        if (configManager.getConfig().getBoolean("protection.block-seed-packets", true)) {
            this.packetListener = new PacketListener(this);
            packetListener.register();
        }

        // Register commands
        getCommand("antiseedcracker").setExecutor(new MainCommand(this));

        // Startup message
        getLogger().info("AntiSeedCracker v" + getDescription().getVersion() + " has been enabled!");
        getLogger().info("World seed protection is now active.");
    }

    @Override
    public void onDisable() {
        // Unregister packet listener
        if (packetListener != null) {
            packetListener.unregister();
        }

        getLogger().info("AntiSeedCracker has been disabled!");
    }

    public void reload() {
        configManager.loadConfigs();
        
        // Re-register packet listener if needed
        if (packetListener != null) {
            packetListener.unregister();
            packetListener = null;
        }
        
        if (configManager.getConfig().getBoolean("protection.block-seed-packets", true)) {
            this.packetListener = new PacketListener(this);
            packetListener.register();
        }
    }

    public static AntiSeedCracker getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}
