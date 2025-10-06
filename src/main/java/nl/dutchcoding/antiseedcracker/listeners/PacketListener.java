package nl.dutchcoding.antiseedcracker.listeners;

import nl.dutchcoding.antiseedcracker.AntiSeedCracker;
import nl.dutchcoding.antiseedcracker.managers.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Handles packet-level protection against seed cracking
 * Note: Full packet interception requires ProtocolLib
 * This is a basic implementation that works with vanilla Paper/Spigot
 */
public class PacketListener implements Listener {

    private final AntiSeedCracker plugin;
    private final ConfigManager configManager;

    public PacketListener(AntiSeedCracker plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void unregister() {
        // Basic implementation - advanced packet handling would require ProtocolLib
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!configManager.isEnabled()) {
            return;
        }

        Player player = event.getPlayer();

        // Log debug info
        if (configManager.isDebug()) {
            plugin.getLogger().info("Player " + player.getName() + " joined. Seed protection active.");
        }

        // Additional packet-level protection would go here with ProtocolLib
        // For now, this serves as a placeholder for future enhancement
    }
}
