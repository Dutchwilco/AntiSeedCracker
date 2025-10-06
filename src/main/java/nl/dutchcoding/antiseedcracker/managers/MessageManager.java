package nl.dutchcoding.antiseedcracker.managers;

import nl.dutchcoding.antiseedcracker.AntiSeedCracker;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class MessageManager {

    private final AntiSeedCracker plugin;
    private FileConfiguration messages;

    public MessageManager(AntiSeedCracker plugin) {
        this.plugin = plugin;
        loadMessages();
    }

    public void loadMessages() {
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        this.messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public String getMessage(String path) {
        String message = messages.getString(path, "&cMessage not found: " + path);
        return colorize(message);
    }

    public List<String> getMessageList(String path) {
        return messages.getStringList(path).stream()
                .map(this::colorize)
                .collect(Collectors.toList());
    }

    public void sendMessage(CommandSender sender, String path) {
        sender.sendMessage(getMessage(path));
    }

    public void sendMessageWithPrefix(CommandSender sender, String path) {
        String prefix = getMessage("prefix");
        sender.sendMessage(prefix + " " + getMessage(path));
    }

    public void sendMessages(CommandSender sender, String path) {
        getMessageList(path).forEach(sender::sendMessage);
    }

    public String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getPrefix() {
        return getMessage("prefix");
    }
}
