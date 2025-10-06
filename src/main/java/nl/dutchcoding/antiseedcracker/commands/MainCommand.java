package nl.dutchcoding.antiseedcracker.commands;

import nl.dutchcoding.antiseedcracker.AntiSeedCracker;
import nl.dutchcoding.antiseedcracker.managers.ConfigManager;
import nl.dutchcoding.antiseedcracker.managers.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {

    private final AntiSeedCracker plugin;
    private final MessageManager messageManager;
    private final ConfigManager configManager;

    public MainCommand(AntiSeedCracker plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
        this.configManager = plugin.getConfigManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("antiseedcracker.admin")) {
            messageManager.sendMessageWithPrefix(sender, "no-permission");
            return true;
        }

        if (args.length == 0) {
            messageManager.sendMessages(sender, "help");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                handleReload(sender);
                break;

            case "info":
                handleInfo(sender);
                break;

            case "help":
                messageManager.sendMessages(sender, "help");
                break;

            default:
                messageManager.sendMessageWithPrefix(sender, "unknown-command");
                break;
        }

        return true;
    }

    private void handleReload(CommandSender sender) {
        try {
            plugin.reload();
            messageManager.sendMessageWithPrefix(sender, "reload-success");
        } catch (Exception e) {
            messageManager.sendMessageWithPrefix(sender, "reload-failed");
            e.printStackTrace();
        }
    }

    private void handleInfo(CommandSender sender) {
        String status = configManager.isEnabled() ? "Enabled" : "Disabled";
        List<String> protectedWorlds = configManager.getProtectedWorlds();
        String worlds = protectedWorlds.isEmpty() ? "All worlds" : String.join(", ", protectedWorlds);

        List<String> infoMessages = messageManager.getMessageList("info");
        for (String line : infoMessages) {
            line = line.replace("{status}", status);
            line = line.replace("{worlds}", worlds);
            line = line.replace("${project.version}", plugin.getDescription().getVersion());
            sender.sendMessage(line);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("reload", "info", "help"));
        }

        return completions;
    }
}
