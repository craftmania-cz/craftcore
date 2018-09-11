package cz.wake.craftcore.messages.chat;

import org.bukkit.entity.Player;

public class ChatInfo {

    public static void info(Player player, String message) {
        player.sendMessage("§7§l> §7" + message);
    }

    public static void success(Player player, String message) {
        player.sendMessage("§a§l✔ §a" + message);
    }

    public static void error(Player player, String message) {
        player.sendMessage("§c§l✖ §c" + message);
    }

    public static void warning(Player player, String message) {
        player.sendMessage("§6§l⚠ §6" + message);
    }

    public static void debug(Player player, String message) {
        player.sendMessage("§b§l… §b" + message);
    }
}
