package cz.craftmania.craftcore.spigot.events.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Synchronous event that is fired when VaultEventsPlugin wraps the original Economy to start firing events.
 *
 * After this event has fired the other events will start firing.
 */
public class EconomyEventsAvailableEvent extends Event {

    private final Economy economy;

    public EconomyEventsAvailableEvent(Economy economy) {
        super(!Bukkit.isPrimaryThread());
        this.economy = economy;
    }

    public Economy getEconomy() {
        return economy;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
