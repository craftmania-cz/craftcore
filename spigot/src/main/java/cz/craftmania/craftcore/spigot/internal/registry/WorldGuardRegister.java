package cz.craftmania.craftcore.spigot.internal.registry;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import cz.craftmania.craftcore.spigot.listener.worldguard.WGRegionEventsListener;
import cz.craftmania.craftcore.spigot.Main;
import org.bukkit.plugin.Plugin;

public class WorldGuardRegister {

    public static void registerWorldGuard(){
        WorldGuardPlugin wgPlugin = getWGPlugin();
        WGRegionEventsListener wgListener = new WGRegionEventsListener(Main.getInstance(), wgPlugin);
        Main.getInstance().getServer().getPluginManager().registerEvents(wgListener, wgPlugin);
    }

    private static WorldGuardPlugin getWGPlugin() {
        final Plugin plugin = Main.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin.getDescription().getVersion().contains("7.0.0")) {
            Main.getCoreLogger().warn("WorldGuard 7.x neni podporovany!");
            return null;
        }
        if (!(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }
}
