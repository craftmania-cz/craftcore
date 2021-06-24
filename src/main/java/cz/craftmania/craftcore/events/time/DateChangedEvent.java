package cz.craftmania.craftcore.events.time;

import cz.craftmania.craftcore.utils.time.TimeType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DateChangedEvent extends Event {

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

    private TimeType timeType;

    public DateChangedEvent(TimeType time) {
        super(true);
        this.timeType = time;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * @return the timeType
     */
    public TimeType getTimeType() {
        return timeType;
    }

    public boolean isFake() {
        return fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }

    /**
     * @param timeType the timeType to set
     */
    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

}
