package cz.wake.craftcore;

import cz.wake.craftcore.internal.ServerData;
import cz.wake.craftcore.internal.registry.ProtocolLibsRegister;
import cz.wake.craftcore.internal.registry.WorldGuardRegister;
import cz.wake.craftcore.inventory.InventoryManager;
import cz.wake.craftcore.listener.basic.PlayerJoinListener;
import cz.wake.craftcore.listener.basic.PlayerLeaveListener;
import cz.wake.craftcore.nms.NMSManager;
import cz.wake.craftcore.nms.NMSPackages;
import cz.wake.craftcore.tasks.TpsPollerTask;
import cz.wake.craftcore.utils.Log;
import cz.wake.craftcore.utils.effects.FireworkHandler;
import cz.wake.craftcore.utils.time.TimeChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JavaPlugin {

    private String idServer;
    private static List<Player> effectPlayers = new ArrayList<>();
    private int timeHourOffSet = 0;
    private boolean timerLoaded = false;
    private static InventoryManager invManager;
    protected NMSPackages nms;

    private static Main instance;

    @Override
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

        // Load NMS Packages
        loadNMSPackages();

        // Timer + events
        timeHourOffSet = getConfig().getInt("timehouroffset", 0);
        loadBackgroundTimer(2);

        // Server data
        ServerData.getInstance().setup();
        ServerData.getInstance().setPluginVersion(this);

        // WorldGuard Addons
        if (getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
            WorldGuardRegister.registerWorldGuard();
            Log.withPrefix("Detekce WorldGuard");
            Log.withPrefix("Pridavne Eventy byly aktivovany!");
        } else {
            Log.withPrefix(ChatColor.RED + "WorldGuard neni detekovan! Eventy nebudou aktivni!");
        }

        // Bungee ID z configu
        idServer = getConfig().getString("server");
        Log.withPrefix("Server zaevidovany jako: " + idServer);

        //Detekce TPS
        if (getConfig().getBoolean("tps-detector")) {
            Log.withPrefix("Detekce TPS byla zapnuta.");
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TpsPollerTask(), 100L, 1L);
        }

        // Inventory Manager API
        invManager = new InventoryManager(this);
        invManager.init();

    }

    @Override
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
        pm.registerEvents(new FireworkHandler(), this);

        if (pm.isPluginEnabled("ProtocolLib")) {
            ProtocolLibsRegister.registerPacketListeners();
            Log.withPrefix("Registrace Packet eventu s ProtocolLibs.");
        } else {
            Log.withPrefix(ChatColor.RED + "Registrace Packet eventu je deaktivovana! Chybi ProtocolLibs!");
        }
    }

    private void loadCommands() {
    }

    public String getIdServer() {
        return idServer;
    }

    private void startupLogo() {
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

    public void removePlayer(Player p) {
        effectPlayers.remove(p);
    }

    public int getTimeHourOffSet() {
        return timeHourOffSet;
    }

    /**
     * Update.
     */
    public void update() {
        TimeChecker.getInstance().update();
    }

    /**
     * Load background
     *
     * @param minutes Minutes
     */
    public void loadBackgroundTimer(int minutes) {
        if (!timerLoaded) {
            timerLoaded = true;
            new Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    if (getInstance() != null) {
                        update();
                    } else {
                        cancel();
                    }

                }
            }, 60000, minutes * 60000L);
        } else {
            Log.withPrefix("Timer je jiz nacten!");
        }
    }

    public NMSPackages getNMS() {
        return this.nms;
    }

    private void loadNMSPackages() {
        try {
            final Class<?> forName = Class.forName("cz.wake.craftcore.nms.packages." + NMSManager.getVersion());
            if (NMSPackages.class.isAssignableFrom(forName)) {
                getInstance().nms = (NMSPackages) forName.getConstructor((Class<?>[]) new Class[0]).newInstance(new Object[0]);
                Log.withPrefix("Detekovano NMS verze: " + NMSManager.getVersion());
            } else {
                Log.withPrefix("Nepodarilo se detekovat verzi NMS! Nektere funkce budou vypnuty!");
                getInstance().nms = null;
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex5) {
            Log.withPrefix(ChatColor.RED + "NMS (" + NMSManager.getVersion() + ") nejsou kompatibilni s touto verzi serveru! Zkontroluj update CraftCore nebo pockej na opravu.");
            getInstance().nms = null;
        }
    }

    public static InventoryManager getInvManager() {
        return invManager;
    }
}
