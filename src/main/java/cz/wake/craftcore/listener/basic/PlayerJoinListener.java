package cz.wake.craftcore.listener.basic;

import cz.wake.craftcore.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();

        Main.getInstance().addEffectPlayers(p);
    }
}
