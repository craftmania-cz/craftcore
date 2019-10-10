package cz.craftmania.craftcore.spigot.listener.extended;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import cz.craftmania.craftcore.spigot.Main;
import cz.craftmania.craftcore.spigot.events.spigot.PlayerCameraChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerCameraChangeEventListener extends PacketAdapter {

    private Plugin plugin;

    public PlayerCameraChangeEventListener(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.CAMERA);
        this.plugin = plugin;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        int entityId = event.getPacket().getIntegers().read(0);
        Entity entity = null;
        Player player = event.getPlayer();

        for (Entity entityWorld : player.getWorld().getEntities()) {
            if (entityWorld.getEntityId() == entityId) {
                entity = entityWorld;
                break;
            }
        }

        if (entity == null) {
            return;
        }

        PlayerCameraChangeEvent cameraEvent = new PlayerCameraChangeEvent(event.getPlayer(), entity);
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> Bukkit.getPluginManager().callEvent(cameraEvent));
        if (cameraEvent.isCancelled()) {
            Bukkit.getScheduler().runTask(plugin, () -> player.setSpectatorTarget(null));
        }
    }
}
