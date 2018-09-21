package cz.craftmania.craftcore.spigot.listener.worldguard;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import cz.craftmania.craftcore.spigot.Main;
import cz.craftmania.craftcore.spigot.events.worldguard.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class WGRegionEventsListener implements Listener {

    private WorldGuardPlugin wgPlugin;
    private Main plugin;
    private Map<Player, Set<ProtectedRegion>> playerRegions;

    public WGRegionEventsListener(final Main plugin, final WorldGuardPlugin wgPlugin) {
        this.plugin = plugin;
        this.wgPlugin = wgPlugin;
        this.playerRegions = new HashMap<Player, Set<ProtectedRegion>>();
    }

    @EventHandler
    public void onPlayerKick(final PlayerKickEvent e) {
        final Set<ProtectedRegion> regions = this.playerRegions.remove(e.getPlayer());
        if (regions != null) {
            for (final ProtectedRegion region : regions) {
                final RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, e.getPlayer(), MovementWay.DISCONNECT, (PlayerEvent) e);
                final RegionLeftEvent leftEvent = new RegionLeftEvent(region, e.getPlayer(), MovementWay.DISCONNECT, (PlayerEvent) e);
                this.plugin.getServer().getPluginManager().callEvent((Event) leaveEvent);
                this.plugin.getServer().getPluginManager().callEvent((Event) leftEvent);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        final Set<ProtectedRegion> regions = this.playerRegions.remove(e.getPlayer());
        if (regions != null) {
            for (final ProtectedRegion region : regions) {
                final RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, e.getPlayer(), MovementWay.DISCONNECT, (PlayerEvent) e);
                final RegionLeftEvent leftEvent = new RegionLeftEvent(region, e.getPlayer(), MovementWay.DISCONNECT, (PlayerEvent) e);
                this.plugin.getServer().getPluginManager().callEvent((Event) leaveEvent);
                this.plugin.getServer().getPluginManager().callEvent((Event) leftEvent);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent e) {
        e.setCancelled(this.updateRegions(e.getPlayer(), MovementWay.MOVE, e.getTo(), (PlayerEvent) e));
    }

    @EventHandler
    public void onPlayerTeleport(final PlayerTeleportEvent e) {
        e.setCancelled(this.updateRegions(e.getPlayer(), MovementWay.TELEPORT, e.getTo(), (PlayerEvent) e));
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        this.updateRegions(e.getPlayer(), MovementWay.SPAWN, e.getPlayer().getLocation(), (PlayerEvent) e);
    }

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent e) {
        this.updateRegions(e.getPlayer(), MovementWay.SPAWN, e.getRespawnLocation(), (PlayerEvent) e);
    }

    private synchronized boolean updateRegions(final Player player, final MovementWay movement, final Location to, final PlayerEvent event) {
        Set<ProtectedRegion> regions;
        if (this.playerRegions.get(player) == null) {
            regions = new HashSet<ProtectedRegion>();
        } else {
            regions = new HashSet<ProtectedRegion>(this.playerRegions.get(player));
        }
        final Set<ProtectedRegion> oldRegions = new HashSet<ProtectedRegion>(regions);
        final RegionManager rm = this.wgPlugin.getRegionManager(to.getWorld());
        if (rm == null) {
            return false;
        }
        final ApplicableRegionSet appRegions = rm.getApplicableRegions(to);
        for (final ProtectedRegion region : appRegions) {
            if (!regions.contains(region)) {
                final RegionEnterEvent e = new RegionEnterEvent(region, player, movement, event);
                this.plugin.getServer().getPluginManager().callEvent((Event) e);
                if (e.isCancelled()) {
                    regions.clear();
                    regions.addAll(oldRegions);
                    return true;
                }
                Bukkit.getScheduler().runTaskLater((Plugin) this.plugin, (Runnable) new Runnable() {
                    @Override
                    public void run() {
                        final RegionEnteredEvent e = new RegionEnteredEvent(region, player, movement, event);
                        WGRegionEventsListener.this.plugin.getServer().getPluginManager().callEvent((Event) e);
                    }
                }, 1L);
                regions.add(region);
            }
        }
        final Collection<ProtectedRegion> app = (Collection<ProtectedRegion>) appRegions.getRegions();
        final Iterator<ProtectedRegion> itr = regions.iterator();
        while (itr.hasNext()) {
            final ProtectedRegion region2 = itr.next();
            if (!app.contains(region2)) {
                if (rm.getRegion(region2.getId()) != region2) {
                    itr.remove();
                } else {
                    final RegionLeaveEvent e2 = new RegionLeaveEvent(region2, player, movement, event);
                    this.plugin.getServer().getPluginManager().callEvent((Event) e2);
                    if (e2.isCancelled()) {
                        regions.clear();
                        regions.addAll(oldRegions);
                        return true;
                    }
                    Bukkit.getScheduler().runTaskLater((Plugin) this.plugin, (Runnable) new Runnable() {
                        @Override
                        public void run() {
                            final RegionLeftEvent e = new RegionLeftEvent(region2, player, movement, event);
                            WGRegionEventsListener.this.plugin.getServer().getPluginManager().callEvent((Event) e);
                        }
                    }, 1L);
                    itr.remove();
                }
            }
        }
        this.playerRegions.put(player, regions);
        return false;
    }
}
