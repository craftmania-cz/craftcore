package cz.craftmania.craftcore.spigot;

import cz.craftmania.craftcore.spigot.internal.ServerData;
import cz.craftmania.craftcore.spigot.internal.listener.NPCInteractEventListener;
import cz.craftmania.craftcore.spigot.internal.listener.PlayerCleanerListener;
import cz.craftmania.craftcore.spigot.internal.listener.ServerListener;
import cz.craftmania.craftcore.spigot.internal.registry.ProtocolLibsRegister;
import cz.craftmania.craftcore.spigot.internal.registry.WorldGuardRegister;
import cz.craftmania.craftcore.spigot.inventory.InventoryManager;
import cz.craftmania.craftcore.spigot.listener.basic.PlayerJoinListener;
import cz.craftmania.craftcore.spigot.listener.basic.PlayerLeaveListener;
import cz.craftmania.craftcore.spigot.listener.bungee.BungeeListener;
import cz.craftmania.craftcore.spigot.nms.NMSManager;
import cz.craftmania.craftcore.spigot.nms.NMSPackages;
import cz.craftmania.craftcore.spigot.tasks.CachedSkinTask;
import cz.craftmania.craftcore.spigot.tasks.TpsPollerTask;
import cz.craftmania.craftcore.spigot.utils.CoreLogger;
import cz.craftmania.craftcore.spigot.utils.effects.FireworkHandler;
import cz.craftmania.craftcore.spigot.utils.mojang.SkinAPI;
import cz.craftmania.craftcore.spigot.utils.time.TimeChecker;
import cz.craftmania.craftcore.core.annotations.AnnotationHandler;
import cz.craftmania.craftcore.spigot.bungee.BungeeAPI;
import cz.craftmania.craftcore.spigot.internal.listener.PacketListener;
import cz.craftmania.craftcore.core.files.DirectoryManager;
import cz.craftmania.craftcore.core.utils.ProxyUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
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
    protected NMSPackages nms;

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

        // Load NMS Packages
        getCoreLogger().info("Nacitani NMS Packages...");
        loadNMSPackages();

        // Timer + events
        timeHourOffSet = getConfig().getInt("timehouroffset", 0);
        loadBackgroundTimer(2);

        // Server data
        getCoreLogger().info("Nacitani internich API...");
        ServerData.getInstance().setup();
        ServerData.getInstance().setPluginVersion(this);
        new SkinAPI();
        new BungeeAPI();
        for (String proxy : getConfig().getStringList("proxies")) {
            String[] x = proxy.split(":");
            ProxyUtils.put(x[0], Integer.parseInt(x[1]));
        }

        // WorldGuard Addons
        if (getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
            WorldGuardRegister.registerWorldGuard();
            getCoreLogger().info("Detekce WorldGuard");
            getCoreLogger().info("Pridavne Eventy byly aktivovany!");
        } else {
            getCoreLogger().error(ChatColor.RED + "WorldGuard neni detekovan! Eventy nebudou aktivni!");
        }

        // Bungee ID z configu
        idServer = getConfig().getString("server");
        getCoreLogger().info("Server zaevidovany jako: " + idServer);

        //Detekce TPS
        if (getConfig().getBoolean("tps-detector", false)) {
            getCoreLogger().info("Detekce TPS byla zapnuta.");
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TpsPollerTask(), 100L, 1L);
        }

        // Bungee register
        getServer().getMessenger().registerOutgoingPluginChannel(this, CRAFTCORE_CHANNEL);
        getServer().getMessenger().registerIncomingPluginChannel(this, CRAFTCORE_CHANNEL, new BungeeListener());

        // Inventory Manager API
        invManager = new InventoryManager(this);
        invManager.init();

        if (getConfig().getBoolean("auto_renew_skin", false)) {
            new CachedSkinTask().runTaskTimerAsynchronously(this, 0, 1200);
        }

    }

    @Override
    public void onDisable() {

        AnnotationHandler.unregister(NPCInteractEventListener.class, null);

        for (Player player : getServer().getOnlinePlayers()) {
            PacketListener.remove(player);
        }

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
        pm.registerEvents(new PlayerCleanerListener(), this);

        if (getConfig().getBoolean("packet_handler", true)) {
            getCoreLogger().info("Registrace Packet listeneru!");
            getServer().getPluginManager().registerEvents(new PacketListener(), this);
        }

        AnnotationHandler.register(NPCInteractEventListener.class, null);

        if (pm.isPluginEnabled("ProtocolLib")) {
            getCoreLogger().info("Registrace Packet eventu s ProtocolLibs.");
            ProtocolLibsRegister.registerPacketListeners();
        } else {
            getCoreLogger().error("Registrace Packet eventu je deaktivovana! Chybi ProtocolLibs!");
        }
    }

    private void loadCommands() {
    }

    public String getIdServer() {
        return idServer;
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

    public NMSPackages getNMS() {
        return this.nms;
    }

    private void loadNMSPackages() {
        try {
            final Class<?> forName = Class.forName("cz.wake.craftcore.nms.packages." + NMSManager.getVersion());
            if (NMSPackages.class.isAssignableFrom(forName)) {
                getInstance().nms = (NMSPackages) forName.getConstructor((Class<?>[]) new Class[0]).newInstance(new Object[0]);
                getCoreLogger().info("Detekovana NMS verze: " + NMSManager.getVersion());
            } else {
                getCoreLogger().warn("Nepodarilo se detekovat verzi NMS! Nektere funkce budou vypnuty!");
                getInstance().nms = null;
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex5) {
            getCoreLogger().error("NMS (" + NMSManager.getVersion() + ") nejsou kompatibilni s touto verzi serveru! Zkontroluj update CraftCore nebo pockej na opravu.");
            getInstance().nms = null;
        }
    }

    public static InventoryManager getInvManager() {
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
