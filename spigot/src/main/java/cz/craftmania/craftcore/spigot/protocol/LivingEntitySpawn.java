package cz.craftmania.craftcore.spigot.protocol;

import cz.craftmania.craftcore.core.utils.Group;
import cz.craftmania.craftcore.spigot.utils.GameVersion;
import cz.craftmania.craftcore.spigot.utils.reflections.ReflectionUtils;
import org.bukkit.entity.LivingEntity;

public class LivingEntitySpawn {

    public static PacketSender create(Object nmsEntityLiving) {
        String v = GameVersion.getVersion().toString();
        try {
            Class<?> nmsEntityLivingClass = Class.forName("net.minecraft.server." + v + ".EntityLiving");
            Class<?> packetPlayOutSpawnEntityLivingClass = Class.forName("net.minecraft.server." + v + ".PacketPlayOutSpawnEntityLiving");
            return new PacketSender(ReflectionUtils.getConstructor(packetPlayOutSpawnEntityLivingClass, new Group<>(
                    new Class<?>[]{nmsEntityLivingClass},
                    new Object[]{nmsEntityLiving}
            )));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PacketSender create(LivingEntity livingEntity) {
        String v = GameVersion.getVersion().toString();
        try {
            Class<?> craftLivingEntityClass = Class.forName("org.bukkit.craftbukkit." + v + ".entity.CraftLivingEntity");
            Class<?> nmsEntityLivingClass = Class.forName("net.minecraft.server." + v + ".EntityLiving");
            Class<?> packetPlayOutSpawnEntityLivingClass = Class.forName("net.minecraft.server." + v + ".PacketPlayOutSpawnEntityLiving");
            Object craftEntityLiving = ReflectionUtils.cast(craftLivingEntityClass, livingEntity);
            Object nmsEntityLiving = ReflectionUtils.getMethod("getHandle", craftLivingEntityClass, craftEntityLiving);
            return new PacketSender(ReflectionUtils.getConstructor(packetPlayOutSpawnEntityLivingClass, new Group<>(
                    new Class<?>[]{nmsEntityLivingClass},
                    new Object[]{nmsEntityLiving}
            )));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
