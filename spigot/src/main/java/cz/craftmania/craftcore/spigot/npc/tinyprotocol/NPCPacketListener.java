package cz.craftmania.craftcore.spigot.npc.tinyprotocol;

import cz.craftmania.craftcore.spigot.npc.CraftNPC;
import cz.craftmania.craftcore.spigot.npc.CraftNPCApi;
import cz.craftmania.craftcore.spigot.npc.events.CraftNPCInteractEvent;
import io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class NPCPacketListener {

    private static TinyProtocol protocol = null;

    private static Class<?> EntityInteractClass = CustomReflection.getClass("{nms}.PacketPlayInUseEntity");
    private static CustomReflection.FieldAccessor<Integer> EntityID = CustomReflection.getField(EntityInteractClass, int.class, 0);
    private static ArrayList<Player> playerswhointeract = new ArrayList<Player>();



    public static void startListening(Plugin plugin){
        if(protocol==null) {
            protocol = new TinyProtocol(plugin) {
                @Override
                public Object onPacketInAsync(Player sender, Channel channel, Object packet) {
                    if(EntityInteractClass.isInstance(packet)){
                        if(!playerswhointeract.contains(sender)){
                            for(CraftNPC npc : CraftNPCApi.getNPCs()){
                                if(npc.getEntityID(sender)==EntityID.get(packet)){
                                    CraftNPCInteractEvent event = new CraftNPCInteractEvent(sender, npc);
                                    Bukkit.getPluginManager().callEvent(event);
                                    break;
                                }
                            }
                            playerswhointeract.add(sender);
                            Bukkit.getScheduler().runTaskLaterAsynchronously(CraftNPCApi.getPlugin(), new Runnable(){
                                @Override
                                public void run() {
                                    playerswhointeract.remove(sender);
                                }
                            }, 2);
                        }
                    }
                    return super.onPacketInAsync(sender, channel, packet);
                }
            };
        }
    }
}
