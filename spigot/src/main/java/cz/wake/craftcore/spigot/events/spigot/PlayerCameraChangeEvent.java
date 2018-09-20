package cz.wake.craftcore.events.spigot;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerCameraChangeEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private Entity camera;
    private boolean cancelled = false;

    /**
     * Triggered when the player's camera changes, ie when they click an entity in spectator mode to enter their view.
     *
     * @param who    The player who's camera is changing to {@code camera}.
     * @param camera The new camera for the player {@code who}.
     */
    public PlayerCameraChangeEvent(Player who, Entity camera) {
        super(who);
        this.camera = camera;
    }

    /**
     * This will return the entity the player's camera has moved to
     *
     * @return The entity the player's camera has moved to
     */
    public Entity getCamera() {
        return camera;
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
     * Note that cancelling the event will trigger an extra event to move the camera back to the player
     *
     * @param b Whether the event should be cancelled
     */
    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
