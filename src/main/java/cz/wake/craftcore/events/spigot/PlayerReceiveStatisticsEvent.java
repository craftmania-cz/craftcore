package cz.wake.craftcore.events.spigot;

import com.comphenix.protocol.wrappers.WrappedStatistic;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.util.Map;

public class PlayerReceiveStatisticsEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private Map<WrappedStatistic, Integer> statistics;

    /**
     * Fired when the server sends the client its statistics
     * @param who The player receiving their statistics
     * @param statistics The statistics {@code who} has. WrappedStatistic gives you access to both the name and the underlying NMS Statistic.
     * @see WrappedStatistic#getHandle()
     */
    public PlayerReceiveStatisticsEvent(Player who, Map<WrappedStatistic, Integer> statistics) {
        super(who);
        this.statistics = statistics;
    }

    /**
     * Returns the amount of {@code statistic} the client has
     * @param statistic The statistic
     * @return How much of the statistic the client has
     */
    public int getStatistic(WrappedStatistic statistic) {
        return statistics.get(statistic);
    }

    /**
     * Returns the amount of the statistic the client has
     * @param name The statistic's name
     * @return How much of the statistic the client has
     * @see PlayerReceiveStatisticsEvent#getStatistic(WrappedStatistic)
     */
    public int getStatistic(String name) {
        return getStatistic(WrappedStatistic.fromName(name));
    }

    /**
     * Returns a map of the client's statistics
     * @return A map of the client's statistics
     */
    public Map<WrappedStatistic, Integer> getStatistics() {
        return statistics;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
