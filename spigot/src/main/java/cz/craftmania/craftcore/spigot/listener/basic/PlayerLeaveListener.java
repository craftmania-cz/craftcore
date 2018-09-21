package cz.craftmania.craftcore.spigot.listener.basic;

import cz.craftmania.craftcore.spigot.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();

        Main.getInstance().removePlayer(p);
    }

    @EventHandler(ignoreCancelled = true)
    public void onKick(PlayerKickEvent e) {
        final Player p = e.getPlayer();

        Main.getInstance().removePlayer(p);
    }
}
