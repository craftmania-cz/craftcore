package cz.wake.craftcore.spigot.internal.registry;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import cz.wake.craftcore.spigot.Main;
import cz.wake.craftcore.spigot.listener.extended.*;

public class ProtocolLibsRegister {

    /**
     * Register ProtocolLibs events into CraftCore.
     */
    public static void registerPacketListeners() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PlayerCameraChangeEventListener(Main.getInstance()));
        protocolManager.addPacketListener(new PlayerListUpdateEventListener(Main.getInstance()));
        protocolManager.addPacketListener(new PlayerOpenSignEditorEventListener(Main.getInstance()));
        protocolManager.addPacketListener(new PlayerChangeGameStateListener(Main.getInstance()));
        protocolManager.addPacketListener(new PlayerReceiveStatisticsEventListener(Main.getInstance()));
        protocolManager.addPacketListener(new PlayerReceiveMessageEventListener(Main.getInstance()));
    }
}
