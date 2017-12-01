package cz.wake.craftcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Log {

    /**
     * Metoda zasilajici do konzole zpravu s preddefinovanym prefixem CraftCore
     *
     * @param text Text zprávy
     */
    public static void withPrefix(String text) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[CraftCore] " + ChatColor.WHITE + text);
    }

    /**
     * Metoda zasilajici do konzole zpravu s vlastnim prefixem.
     *
     * @param customPrefix Prefix
     * @param text Text zprávy
     */
    public static void withCustomPrefix (String customPrefix, String text){
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[" + customPrefix + "]" + ChatColor.WHITE + text);
    }

    /**
     * Zprava zasilajici do konzole text bez prefixu a formátování
     *
     * @param text Text zprávy
     */
    public static void normalMessage(String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }

}
