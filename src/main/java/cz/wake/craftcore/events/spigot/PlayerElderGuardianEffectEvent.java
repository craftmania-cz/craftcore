package cz.wake.craftcore.events.spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerElderGuardianEffectEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    /**
     * Fired when a player is inflicted with mining fatigue from an elder guardian.
     * @param who The player receiving the elder guardian effect.
     */
    public PlayerElderGuardianEffectEvent(Player who) {
        super(who);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets whether to cancel the event. If the event is cancelled, the client will still be inflicted
     * with mining fatigue but they will not recieve the effect of the elder guardian traversing from
     * the top of their window to the bottom.
     * @param b Whether to cancel the event.
     */
    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
