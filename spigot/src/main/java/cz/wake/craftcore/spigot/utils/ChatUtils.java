package cz.wake.craftcore.spigot.utils;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A class helps you to send messages or color them
 */
public class ChatUtils {

    /**
     * Colors the given string
     *
     * @param text a text string
     * @return the colored message
     */
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Colors the given text component
     *
     * @param text a text component
     * @return the colored text component
     */
    public static TextComponent color(TextComponent text) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', text.getText()));
    }

    private String prefix;
    private boolean placeholder;

    public ChatUtils(String prefix) {
        this.prefix = prefix;
        this.placeholder = false;
    }

    public ChatUtils(String prefix, boolean placeholder) {
        this.prefix = prefix;
        this.placeholder = placeholder;
    }

    public void sendSender(String a) {
        Bukkit.getConsoleSender().sendMessage(replace(prefix + a));
    }

    public void sendSenderNoPrefix(String a) {
        Bukkit.getServer().getConsoleSender().sendMessage(replace(a));
    }

    public void sendPlayer(String a, Player p) {
        p.sendMessage(replace(prefix + a));
    }

    public void sendPlayerNoPrefix(String a, Player p) {
        p.sendMessage(replace(a));
    }

    public void sendSender(String a, CommandSender s) {
        if (s instanceof Player) {
            s.sendMessage(replace(prefix + a));
        }
    }

    public void sendSenderNoPrefix(String a, CommandSender s) {
        if (s instanceof Player) {
            s.sendMessage(replace(a));
        }
    }

    public void sendAllPlayers(String a) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(replace(a));
        }
    }

    public void sendAllPlayersNoPrefix(String a) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(replace(a));
        }
    }

    public void sendGlobal(String a) {
        Bukkit.getServer().broadcastMessage(replace(prefix + a));
    }

    public void sendGlobalNoPrefix(String a) {
        Bukkit.getServer().broadcastMessage(replace(a));
    }

    public void sendGlobal(String a, World w) {
        for (Player p : w.getPlayers()) {
            sendPlayer(a, p);
        }
    }

    public void sendGlobalNoPrefix(String a, World w) {
        for (Player p : w.getPlayers()) {
            sendPlayerNoPrefix(a, p);
        }
    }

    private String replace(String s) {
        return color(s);
    }

    private TextComponent replace(TextComponent s) {
        return color(s);
    }

    private TextComponent buildChatPrefix(TextComponent text) {
        TextComponent str = new TextComponent(prefix);
        str.addExtra(text);
        return str;
    }
}
