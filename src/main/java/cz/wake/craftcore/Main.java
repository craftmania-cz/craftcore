package cz.wake.craftcore;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import cz.wake.craftcore.listener.extended.*;
import cz.wake.craftcore.services.prometheus.MetricsController;
import cz.wake.craftcore.tasks.TpsPollerTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.eclipse.jetty.server.Server;

public class Main extends JavaPlugin {

    private Server server;
    private String idServer;

    private static Main instance;

    public void onEnable() {

        // Instance
        instance = this;

        // Config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Register eventu a prikazu
        loadListeners();
        loadCommands();

        // Bungee ID z configu
        idServer = getConfig().getString("server");

        //Detekce TPS
        //TODO: Volitelný
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TpsPollerTask(), 100L, 1L);

        // Nastaveni Prometheus serveru
        if (getConfig().getBoolean("prometheus.state")) {
            //Log.withPrefix("Probehne aktivace Prometheus endpointu a TPS detekce!");
            getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new TpsPollerTask(), 0, 40);
            int port = getConfig().getInt("prometheus.port");
            server = new Server(port);
            server.setHandler(new MetricsController(this));
            try {
                server.start();
                //Log.withPrefix("Spusten Prometheus endpoint na portu " + port);
            } catch (Exception e) {
                //log.error("", e);
                //Log.withPrefix("Nelze spustit Jetty Endpoint pro Prometheus.");
            }
        }

    }

    public void onDisable() {

        // Deaktivace Jetty portu
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                //TODO: log
                //log.error("", e);
            }
        }

        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }

    private void loadListeners() {
        PluginManager pm = getServer().getPluginManager();

        if(pm.isPluginEnabled("ProtocolLib")){
            registerPacketListeners();
        } else{
            //TODO: Log o vypnuti
        }
    }

    private void loadCommands() {
    }

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
}
