package cz.craftmania.craftcore.spigot.internal.listener;

import cz.craftmania.craftcore.spigot.annotations.PacketHandler;
import cz.craftmania.craftcore.spigot.builders.npc.NPCBuilder;
import cz.craftmania.craftcore.spigot.events.NPCInteractEvent;
import cz.craftmania.craftcore.spigot.inventory.EquipSlot;
import cz.craftmania.craftcore.spigot.utils.GameVersion;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class NPCInteractEventListener {
    public static List<NPCBuilder> data = new ArrayList<>();

    @PacketHandler
    public static void packetHandler(PacketListener.Handler handler) {
        if (handler.getBound().equals(PacketListener.BoundType.SERVER_BOUND) &&
                handler.getPacket().getClass().getSimpleName().equals("PacketPlayInUseEntity")) {
            for (NPCBuilder n : data) {
                if (n.getEntityId() == (int) handler.getPacketValue("a")) {
                    EquipSlot es = EquipSlot.MAINHAND;
                    if (GameVersion.is1_9Above()) {
                        Object d = handler.getPacketValue("d");
                        if ((d != null) && (d.toString().equals("OFF_HAND"))) {
                            es = EquipSlot.OFFHAND;
                        }
                    }
                    NPCInteractEvent e = new NPCInteractEvent(handler.getPlayer(), n,
                            NPCInteractEvent.Action.valueOf(handler.getPacketValue("action").toString()), es);
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    handler.setCancelled(true);
                    break;
                }
            }
        }
    }
}
