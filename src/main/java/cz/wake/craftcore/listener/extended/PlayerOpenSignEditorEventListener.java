package cz.wake.craftcore.listener.extended;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import cz.wake.craftcore.events.PlayerOpenSignEditorEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PlayerOpenSignEditorEventListener extends PacketAdapter {

    public PlayerOpenSignEditorEventListener(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.OPEN_SIGN_EDITOR);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PlayerOpenSignEditorEvent openSignEditorEvent = new PlayerOpenSignEditorEvent(
                event.getPlayer(),
                event.getPacket().getPositionModifier().read(0));
        Bukkit.getPluginManager().callEvent(openSignEditorEvent);
        if(openSignEditorEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
}
