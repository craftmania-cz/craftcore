package cz.wake.craftcore.events.spigot;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerListUpdateEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private BaseComponent[] header;
    private BaseComponent[] footer;

    //TODO: xxx
    public PlayerListUpdateEvent(Player who, BaseComponent[] header, BaseComponent[] footer) {
        super(who);
        this.header = header;
        this.footer = footer;
    }

    /**
     * Returns the player list header for this player
     * @return The player list header for this player
     */
    public BaseComponent[] getHeader() {
        return header;
    }

    /**
     * Returns the player list footer for this player
     * @return The player list footer for this player
     */
    public BaseComponent[] getFooter() {
        return footer;
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

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
