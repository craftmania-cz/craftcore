package cz.craftmania.craftcore.spigot.npc.events;

import cz.craftmania.craftcore.spigot.npc.CraftNPC;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class CraftNPCDespawnEvent extends CraftNPCEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CraftNPCDespawnEvent(Player player, CraftNPC npc){
        super(player, npc);
    }
}
