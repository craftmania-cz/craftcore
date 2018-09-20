package cz.wake.craftcore.internal.registry;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import cz.wake.craftcore.Main;
import cz.wake.craftcore.listener.worldguard.WGRegionEventsListener;
import org.bukkit.plugin.Plugin;

public class WorldGuardRegister {

    public static void registerWorldGuard(){
        WorldGuardPlugin wgPlugin = getWGPlugin();
        WGRegionEventsListener wgListener = new WGRegionEventsListener(Main.getInstance(), wgPlugin);
        Main.getInstance().getServer().getPluginManager().registerEvents(wgListener, wgPlugin);
    }

    private static WorldGuardPlugin getWGPlugin() {
        final Plugin plugin = Main.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if (!(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }
}
