package cz.wake.craftcore.protocol;

import cz.wake.craftcore.utils.Group;
import cz.wake.craftcore.utils.GameVersion;
import cz.wake.craftcore.utils.reflections.ReflectionUtils;

public class EntityDestroy {

    public static PacketSender create(int entityID) {
        String v = GameVersion.getVersion().toString();
        try {
            Class<?> packetPlayOutEntityDestroyClass = Class.forName("net.minecraft.server." + v + ".PacketPlayOutEntityDestroy");
            return new PacketSender(ReflectionUtils.getConstructor(packetPlayOutEntityDestroyClass, new Group<>(
                    new Class<?>[]{int[].class},
                    new Object[]{new int[]{entityID}}
            )));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
