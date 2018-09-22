package cz.craftmania.craftcore.spigot.messages.chat;

import org.bukkit.entity.Player;

/**
   This class contains shortcuts for CM basic messages for players
 */
public class ChatInfo {

    /**
     * Informative message about everything
     *
     * @param player Player that receive message
     * @param message Text of message
     */
    public static void info(Player player, String message) {
        player.sendMessage("§7§l> §7" + message);
    }

    /**
     * Successful message if process will be successfully :D
     *
     * @param player Player that receive message
     * @param message Text of successful message
     */
    public static void success(Player player, String message) {
        player.sendMessage("§a§l✔ §a" + message);
    }

    /**
     * Whatever some action fails this can inform player about it
     *
     * @param player Player that receive message
     * @param message Text of error message
     */
    public static void error(Player player, String message) {
        player.sendMessage("§c§l✖ §c" + message);
    }

    /**
     * Some warning for player, be careful!
     *
     * @param player Player that receive message
     * @param message Text of warning message
     */
    public static void warning(Player player, String message) {
        player.sendMessage("§6§l⚠ §6" + message);
    }

    /**
     * Debug message for developers and admin team
     *
     * @param player Player that receive message
     * @param message Text of debug message
     */
    public static void debug(Player player, String message) {
        player.sendMessage("§b§l… §b" + message);
    }
}
