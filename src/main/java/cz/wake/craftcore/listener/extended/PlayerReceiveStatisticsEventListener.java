package cz.wake.craftcore.listener.extended;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedStatistic;
import cz.wake.craftcore.events.PlayerReceiveStatisticsEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class PlayerReceiveStatisticsEventListener extends PacketAdapter {

    public PlayerReceiveStatisticsEventListener(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.STATISTIC);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Map<WrappedStatistic, Integer> read = event.getPacket().getStatisticMaps().read(0);
        PlayerReceiveStatisticsEvent receiveStatisticsEvent = new PlayerReceiveStatisticsEvent(event.getPlayer(), read);
        Bukkit.getPluginManager().callEvent(receiveStatisticsEvent);
    }
}
