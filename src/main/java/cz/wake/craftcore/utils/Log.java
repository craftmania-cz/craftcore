package cz.wake.craftcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Log {

    public static void withPrefix(String s) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[CraftCore] " + ChatColor.WHITE + s);
    }

    public static void normalMessage(String s) {
        Bukkit.getConsoleSender().sendMessage(s);
    }

}
