package cz.craftmania.craftcore.spigot.npc.events;

import cz.craftmania.craftcore.spigot.npc.CraftNPC;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class CraftNPCEvent extends Event {

    private Player player;
    private CraftNPC npc;

    public CraftNPCEvent(Player player, CraftNPC npc) {
        this.player = player;
        this.npc = npc;
    }

    public Player getPlayer() {
        return this.player;
    }

    public CraftNPC getNPC(){
        return npc;
    }
}
