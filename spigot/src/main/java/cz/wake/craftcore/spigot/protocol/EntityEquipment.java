package cz.wake.craftcore.spigot.protocol;

import cz.wake.craftcore.spigot.inventory.EquipSlot;
import cz.wake.craftcore.core.utils.Group;
import cz.wake.craftcore.spigot.utils.GameVersion;
import cz.wake.craftcore.spigot.utils.reflections.ReflectionUtils;
import org.bukkit.inventory.ItemStack;

public class EntityEquipment {
    public static PacketSender create(int id, EquipSlot slot, ItemStack item) {
        String v = GameVersion.getVersion().toString();
        try {
            Class<?> itemClass = Class.forName("net.minecraft.server." + v + ".ItemStack");
            Class<?> craftItemStackClass = Class.forName("org.bukkit.craftbukkit." + v + ".inventory.CraftItemStack");
            Class<?> packetPlayOutEntityEquipmentClass = Class.forName("net.minecraft.server." + v + ".PacketPlayOutEntityEquipment");
            if (GameVersion.is1_9Above()) {
                Class<?> enumItemSlotClass = Class.forName("net.minecraft.server." + v + ".EnumItemSlot");
                return new PacketSender(ReflectionUtils.getConstructor(packetPlayOutEntityEquipmentClass, new Group<>(
                        new Class<?>[]{int.class, enumItemSlotClass, itemClass},
                        new Object[]{id, ReflectionUtils.getEnum(slot.toString(), enumItemSlotClass),
                                ReflectionUtils.getStaticMethod("asNMSCopy", craftItemStackClass,
                                        new Group<>(new Class<?>[]{ItemStack.class}, new Object[]{item}))}
                )));
            } else {
                int s;
                switch (slot) {
                    case MAINHAND:
                        s = 0;
                        break;
                    case HEAD:
                        s = 1;
                        break;
                    case CHEST:
                        s = 2;
                        break;
                    case LEGS:
                        s = 3;
                        break;
                    case FEET:
                        s = 4;
                        break;
                    default:
                        return null;
                }
                return new PacketSender(ReflectionUtils.getConstructor(packetPlayOutEntityEquipmentClass, new Group<>(
                        new Class<?>[]{int.class, int.class, itemClass},
                        new Object[]{id, s,
                                ReflectionUtils.getStaticMethod("asNMSCopy", craftItemStackClass,
                                        new Group<>(new Class<?>[]{ItemStack.class}, new Object[]{item}))}
                )));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
