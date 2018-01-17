package cz.wake.craftcore;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import cz.wake.craftcore.listener.basic.PlayerJoinListener;
import cz.wake.craftcore.listener.basic.PlayerLeaveListener;
import cz.wake.craftcore.listener.extended.*;
import cz.wake.craftcore.listener.worldguard.WGRegionEventsListener;
import cz.wake.craftcore.tasks.TpsPollerTask;
import cz.wake.craftcore.utils.Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private String idServer;
    private WGRegionEventsListener wgListener;
    private WorldGuardPlugin wgPlugin;
    private static List<Player> effectPlayers = new ArrayList<>();

    private static Main instance;

    public void onEnable() {

        // Instance
        instance = this;

        // Logo
        startupLogo();

        // Config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Register eventu a prikazu
        loadListeners();
        loadCommands();

        // WorldGuard Addons
        this.wgPlugin = this.getWGPlugin();
        if (this.wgPlugin != null) {
            this.wgListener = new WGRegionEventsListener(this, this.wgPlugin);
            this.getServer().getPluginManager().registerEvents(this.wgListener, this.wgPlugin);
            Log.withPrefix("Detekce WorldGuard");
            Log.withPrefix("Pridavne Eventy byly aktivovany!");
        } else {
            Log.withPrefix("WorldGuard neni detekovan! Eventy nebudou aktivni!");
        }

        // Bungee ID z configu
        idServer = getConfig().getString("server");
        Log.withPrefix("Server zaevidovany jako: " + idServer);

        //Detekce TPS
        if (getConfig().getBoolean("tps-detector")) {
            Log.withPrefix("Detekce TPS byla zapnuta.");
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TpsPollerTask(), 100L, 1L);
        }

    }

    public void onDisable() {
        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }

    private void loadListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerLeaveListener(), this);

        if (pm.isPluginEnabled("ProtocolLib")) {
            registerPacketListeners();
            Log.withPrefix("Registrace Packet eventu s ProtocolLibs.");
        } else {
            Log.withPrefix(ChatColor.RED + "Registrace Packet eventu je deaktivovana! Chybi ProtocolLibs!");
        }
    }

    private void loadCommands() {
    }

    @Deprecated
    public String getIdServer() {
        return idServer;
    }

    private void registerPacketListeners() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PlayerCameraChangeEventListener(this));
        protocolManager.addPacketListener(new PlayerListUpdateEventListener(this));
        protocolManager.addPacketListener(new PlayerOpenSignEditorEventListener(this));
        protocolManager.addPacketListener(new PlayerChangeGameStateListener(this));
        protocolManager.addPacketListener(new PlayerReceiveStatisticsEventListener(this));
        protocolManager.addPacketListener(new PlayerReceiveMessageEventListener(this));
    }

    private WorldGuardPlugin getWGPlugin() {
        final Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }

    private void startupLogo(){
        Log.normalMessage("   ______           ______  ______              ");
        Log.normalMessage("  / ____/________ _/ __/ /_/ ____/___  ________ ");
        Log.normalMessage(" / /   / ___/ __ `/ /_/ __/ /   / __ \\/ ___/ _ \\");
        Log.normalMessage("/ /___/ /  / /_/ / __/ /_/ /___/ /_/ / /  /  __/");
        Log.normalMessage("\\____/_/   \\__,_/_/  \\__/\\____/\\____/_/   \\___/ ");
        Log.normalMessage("                                                ");
    }

    public List<Player> getEffectPlayers() {
        return effectPlayers;
    }

    public void addEffectPlayers(Player p) {
        effectPlayers.add(p);
    }

    public void removePlayer(Player p){
        effectPlayers.remove(p);
    }
}
