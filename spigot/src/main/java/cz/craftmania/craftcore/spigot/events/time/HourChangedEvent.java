package cz.craftmania.craftcore.spigot.events.time;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HourChangedEvent extends Event {

    /**
     * The Constant handlers.
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Gets the handler list.
     *
     * @return the handler list
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private boolean fake = false;
    private int hour;

    public HourChangedEvent(int hour) {
        super(true);
        this.hour = hour;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.bukkit.event.Event#getHandlers()
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean isFake() {
        return fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }

    public int getHour() {
        return hour;
    }
}
