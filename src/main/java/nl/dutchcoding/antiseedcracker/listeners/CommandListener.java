package nl.dutchcoding.antiseedcracker.listeners;

import nl.dutchcoding.antiseedcracker.AntiSeedCracker;
import nl.dutchcoding.antiseedcracker.managers.ConfigManager;
import nl.dutchcoding.antiseedcracker.managers.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {

    private final AntiSeedCracker plugin;
    private final ConfigManager configManager;
    private final MessageManager messageManager;

    public CommandListener(AntiSeedCracker plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.messageManager = plugin.getMessageManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (!configManager.isEnabled()) {
            return;
        }

        Player player = event.getPlayer();

        // Bypass permission check
        if (player.hasPermission("antiseedcracker.bypass")) {
            return;
        }

        // Check if world is protected
        if (!configManager.isWorldProtected(player.getWorld().getName())) {
            return;
        }

        String command = event.getMessage().toLowerCase().substring(1);
        String[] parts = command.split(" ");
        String baseCommand = parts[0];

        // Block seed command
        if (configManager.shouldBlockSeedCommand() && isSeedCommand(command)) {
            event.setCancelled(true);
            messageManager.sendMessageWithPrefix(player, "seed-protection");
            if (configManager.isDebug()) {
                plugin.getLogger().info("Blocked seed command from " + player.getName() + ": " + command);
            }
            return;
        }

        // Block locatebiome command
        if (configManager.shouldBlockLocateBiome() && isLocateBiomeCommand(baseCommand)) {
            event.setCancelled(true);
            messageManager.sendMessageWithPrefix(player, "locate-blocked");
            if (configManager.isDebug()) {
                plugin.getLogger().info("Blocked locatebiome command from " + player.getName() + ": " + command);
            }
            return;
        }

        // Block locate command
        if (configManager.shouldBlockLocate() && isLocateCommand(baseCommand)) {
            event.setCancelled(true);
            messageManager.sendMessageWithPrefix(player, "locate-blocked");
            if (configManager.isDebug()) {
                plugin.getLogger().info("Blocked locate command from " + player.getName() + ": " + command);
            }
            return;
        }

        // Block execute commands that contain seed
        if (configManager.shouldBlockExecuteSeed() && command.contains("execute") && command.contains("seed")) {
            event.setCancelled(true);
            messageManager.sendMessageWithPrefix(player, "command-blocked");
            if (configManager.isDebug()) {
                plugin.getLogger().info("Blocked execute seed command from " + player.getName() + ": " + command);
            }
            return;
        }

        // Check custom blocked commands
        for (String blocked : configManager.getBlockedCommands()) {
            if (command.startsWith(blocked.toLowerCase())) {
                event.setCancelled(true);
                messageManager.sendMessageWithPrefix(player, "command-blocked");
                if (configManager.isDebug()) {
                    plugin.getLogger().info("Blocked custom command from " + player.getName() + ": " + command);
                }
                return;
            }
        }
    }

    private boolean isSeedCommand(String command) {
        return command.equals("seed") || 
               command.startsWith("seed ") || 
               command.equals("minecraft:seed") || 
               command.startsWith("minecraft:seed ");
    }

    private boolean isLocateBiomeCommand(String command) {
        return command.equals("locatebiome") || 
               command.equals("minecraft:locatebiome");
    }

    private boolean isLocateCommand(String command) {
        return command.equals("locate") || 
               command.equals("minecraft:locate");
    }
}
