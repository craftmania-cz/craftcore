package cz.craftmania.craftcore.spigot.npc.skin;

import org.bukkit.plugin.Plugin;

public class CraftNPCSkinBuilder {

    public static CraftNPCSkin fromUsername(Plugin plugin, String username){
        return new CraftNPCSkin(plugin, SkinType.IDENTIFIER, username);
    }

    public static CraftNPCSkin fromUUID(Plugin plugin, String uuid){
        return new CraftNPCSkin(plugin, SkinType.IDENTIFIER, uuid);
    }

    public static CraftNPCSkin fromMineskin(Plugin plugin, int mineskinid){
        return new CraftNPCSkin(plugin, SkinType.MINESKINID, String.valueOf(mineskinid));
    }

    public static CraftNPCSkin fromPlayer(Plugin plugin){
        return new CraftNPCSkin(plugin, SkinType.PLAYER);
    }
}
