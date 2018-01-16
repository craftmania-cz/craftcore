package cz.wake.craftcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Log {

    /**
     * Sends message into console with predefined prefix CraftCore
     *
     * @param text Message
     */
    public static void withPrefix(String text) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[CraftCore] " + ChatColor.WHITE + text);
    }

    /**
     * Sends message into console with own prefix
     *
     * @param customPrefix Prefix
     * @param text         Message
     */
    public static void withCustomPrefix(String customPrefix, String text) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[" + customPrefix + "]" + ChatColor.WHITE + text);
    }

    /**
     * Sends message into console without any formats and prefix
     *
     * @param text Message
     */
    public static void normalMessage(String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }

}
