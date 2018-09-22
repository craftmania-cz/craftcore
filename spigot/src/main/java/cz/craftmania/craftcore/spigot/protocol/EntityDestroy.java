package cz.craftmania.craftcore.spigot.protocol;

import cz.craftmania.craftcore.core.utils.Group;
import cz.craftmania.craftcore.spigot.utils.GameVersion;
import cz.craftmania.craftcore.spigot.utils.reflections.ReflectionUtils;

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
