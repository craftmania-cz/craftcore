package cz.craftmania.craftcore.spigot.listener.extended;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import cz.craftmania.craftcore.spigot.Main;
import cz.craftmania.craftcore.spigot.events.spigot.PlayerReceiveMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PlayerReceiveMessageEventListener extends PacketAdapter {

    public PlayerReceiveMessageEventListener(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        EnumWrappers.ChatType type = packet.getChatTypes().read(0);
        WrappedChatComponent message = packet.getChatComponents().read(0);

        PlayerReceiveMessageEvent receiveMessageEvent = new PlayerReceiveMessageEvent(event.getPlayer(), type, message);
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> Bukkit.getPluginManager().callEvent(receiveMessageEvent));
        if (receiveMessageEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
}
