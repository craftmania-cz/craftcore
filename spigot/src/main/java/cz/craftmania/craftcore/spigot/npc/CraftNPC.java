package cz.craftmania.craftcore.spigot.npc;

import cz.craftmania.craftcore.spigot.npc.skin.CraftNPCSkin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface CraftNPC {

    void delete();
    Location getLocation();
    int getEntityID(Player p);
    boolean isDeleted();
    int getNpcID();
    CraftNPCSkin getSkin();
}
