package cz.craftmania.craftcore.spigot.listener.extended;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import cz.craftmania.craftcore.spigot.Main;
import cz.craftmania.craftcore.spigot.events.spigot.PlayerListUpdateEvent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PlayerListUpdateEventListener extends PacketAdapter {

    public PlayerListUpdateEventListener(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        StructureModifier<WrappedChatComponent> chatComponents = event.getPacket().getChatComponents();
        PlayerListUpdateEvent listUpdateEvent = new PlayerListUpdateEvent(event.getPlayer(),
                ComponentSerializer.parse(chatComponents.read(0).getJson()),
                ComponentSerializer.parse(chatComponents.read(1).getJson()));
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> Bukkit.getPluginManager().callEvent(listUpdateEvent));
        if (listUpdateEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
}
