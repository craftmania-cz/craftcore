package cz.wake.craftcore.spigot.events.worldguard;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public class RegionEnteredEvent extends RegionEvent {

    public RegionEnteredEvent(final ProtectedRegion region, final Player player, final MovementWay movement, final PlayerEvent parent) {
        super(region, player, movement, parent);
    }
}
