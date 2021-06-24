package cz.craftmania.craftcore;

import cz.craftmania.craftcore.internal.ServerData;
import cz.craftmania.craftcore.internal.listener.ServerListener;
import cz.craftmania.craftcore.inventory.builder.InventoryManager;
import cz.craftmania.craftcore.listener.basic.PlayerJoinListener;
import cz.craftmania.craftcore.listener.basic.PlayerLeaveListener;
import cz.craftmania.craftcore.listener.bungee.BungeeListener;
import cz.craftmania.craftcore.listener.worldguard.WGRegionEventsListener;
import cz.craftmania.craftcore.tasks.TpsPollerTask;
import cz.craftmania.craftcore.utils.CoreLogger;
import cz.craftmania.craftcore.utils.effects.FireworkHandler;
import cz.craftmania.craftcore.utils.time.TimeChecker;
import cz.craftmania.craftcore.files.DirectoryManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public final class Main extends JavaPlugin {

    private String idServer;
    public final static String CRAFTCORE_CHANNEL = "craftcore:plugin";
    public final static File ROOT_FOLDER = new File("plugins/CraftCore/");
    public final static File SKINS_FOLDER = new File(ROOT_FOLDER, "skins/");
    public final static File CONFIG_FILE = new File(ROOT_FOLDER, "src/main/resources/config.yml");
    private static CoreLogger coreLogger;
    private static List<Player> effectPlayers = new ArrayList<>();
    private int timeHourOffSet = 0;
    private boolean timerLoaded = false;

    private static InventoryManager invManager;

    private static Main instance;

    @Override
    public void onEnable() {

        // Checkup for Spigot
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException e) {
            getLogger().info("CraftCore funguje pouze na Spigot nebo PaperSpigot serverech.");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }

        // Instance
        instance = this;

        // Logger
        coreLogger = new CoreLogger();

        // Logo
        startupLogo();

        // Config
        getCoreLogger().info("Generovani a nacitani configuracnich souboru...");
        new DirectoryManager(ROOT_FOLDER).mkdirs();
        new DirectoryManager(SKINS_FOLDER).mkdirs();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Register eventu a prikazu
        getCoreLogger().info("Nacitani prikazu a listeneru...");
        loadListeners();
        loadCommands();

        // Timer + events
        timeHourOffSet = getConfig().getInt("timehouroffset", 0);
        loadBackgroundTimer(2);

        // Server data
        getCoreLogger().info("Nacitani internich API...");
        ServerData.getInstance().setup();
        ServerData.getInstance().setPluginVersion(this);

        // Inventory Builder
        invManager = new InventoryManager(this);
        invManager.init();

        // WorldGuard Addons
        if (getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
            WGRegionEventsListener.initialize();
            getCoreLogger().info("Detekce WorldGuard");
            getCoreLogger().info("Pridavne Eventy byly aktivovany!");
        } else {
            getCoreLogger().error(ChatColor.RED + "WorldGuard neni detekovan! Eventy nebudou aktivni!");
        }

        //Detekce TPS
        if (getConfig().getBoolean("tps-detector", false)) {
            getCoreLogger().info("Detekce TPS byla zapnuta.");
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TpsPollerTask(), 100L, 1L);
        }

        // Bungee register
        getServer().getMessenger().registerOutgoingPluginChannel(this, CRAFTCORE_CHANNEL);
        getServer().getMessenger().registerIncomingPluginChannel(this, CRAFTCORE_CHANNEL, new BungeeListener());

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
        pm.registerEvents(new ServerListener(), this);
    }

    private void loadCommands() {
    }

    private void startupLogo() {
        Bukkit.getConsoleSender().sendMessage("   ______           ______  ______              ");
        Bukkit.getConsoleSender().sendMessage("  / ____/________ _/ __/ /_/ ____/___  ________ ");
        Bukkit.getConsoleSender().sendMessage(" / /   / ___/ __ `/ /_/ __/ /   / __ \\/ ___/ _ \\");
        Bukkit.getConsoleSender().sendMessage("/ /___/ /  / /_/ / __/ /_/ /___/ /_/ / /  /  __/");
        Bukkit.getConsoleSender().sendMessage("\\____/_/   \\__,_/_/  \\__/\\____/\\____/_/   \\___/ ");
        Bukkit.getConsoleSender().sendMessage("                                                ");
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
            getCoreLogger().warn("Timer je jiz nacten!");
        }
    }

    public static InventoryManager getInventoryManager() {
        return invManager;
    }

    /**
     * Get basic Core logger for console messages
     * @return Logger instance
     */
    public static CoreLogger getCoreLogger() {
        return coreLogger;
    }
}
