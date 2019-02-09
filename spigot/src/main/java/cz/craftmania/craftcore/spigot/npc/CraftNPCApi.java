package cz.craftmania.craftcore.spigot.npc;

import cz.craftmania.craftcore.spigot.npc.nms.CraftNPC_v_12_r1;
import cz.craftmania.craftcore.spigot.npc.skin.CraftNPCSkin;
import cz.craftmania.craftcore.spigot.npc.tinyprotocol.NPCPacketListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.logging.Level;

public class CraftNPCApi {

    private static String version;
    private static Plugin plugin;
    private static ArrayList<CraftNPC> npcs = new ArrayList<CraftNPC>();
    private static Boolean cache = true;

    public static Plugin getPlugin(){
        return plugin;
    }

    public static Boolean getCache(){
        return cache;
    }

    public static void useCache(boolean bol){
        cache = bol;
    }

    private static void setupVersion(){
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    public static void removeCacheFile(){
        if (version.equals("v1_12_R1")) {
            CraftNPC_v_12_r1.removeCacheFile();
        }
    }

    public static ArrayList<CraftNPC> getNPCs(){
        ArrayList<CraftNPC> list = new ArrayList<CraftNPC>();
        for(CraftNPC npc : npcs){
            if(!npc.isDeleted()){
                list.add(npc);
            }
        }
        return list;
    }

    /**
     * Create a NPC
     * @param plugin
     * @param location NPC Location
     * @param skin NPC skin ussing a playername
     */

    public static CraftNPC createNPC(Plugin plugin, Location location, CraftNPCSkin skin){
        CraftNPCApi.plugin = plugin;
        if(version==null){
            setupVersion();
        }
        if (version.equals("v1_12_R1")) {
            CraftNPC_v_12_r1.startTask(plugin);
            CraftNPC npc = new CraftNPC_v_12_r1(location, skin);
            npcs.add(npc);
            NPCPacketListener.startListening(plugin);
            return npc;
        }else{
            Bukkit.getLogger().log(Level.SEVERE, ChatColor.RED+"Unsupported server version.");
            return null;
        }
    }

}
