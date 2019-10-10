package cz.craftmania.craftcore.spigot.listener.extended;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import cz.craftmania.craftcore.spigot.Main;
import cz.craftmania.craftcore.spigot.events.spigot.PlayerElderGuardianEffectEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PlayerChangeGameStateListener extends PacketAdapter {

    public PlayerChangeGameStateListener(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.GAME_STATE_CHANGE);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        int reason = event.getPacket().getIntegers().read(0);
        if (reason == 10) {
            PlayerElderGuardianEffectEvent elderGuardianEffectEvent = new PlayerElderGuardianEffectEvent(event.getPlayer());
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> Bukkit.getPluginManager().callEvent(elderGuardianEffectEvent));
            if (elderGuardianEffectEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }
}