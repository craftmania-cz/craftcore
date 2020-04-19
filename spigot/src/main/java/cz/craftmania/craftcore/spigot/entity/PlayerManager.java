package cz.craftmania.craftcore.spigot.entity;

import cz.craftmania.craftcore.spigot.protocol.EntityDestroy;
import cz.craftmania.craftcore.spigot.protocol.NamedEntitySpawn;
import cz.craftmania.craftcore.spigot.protocol.PlayerInfo;
import cz.craftmania.craftcore.spigot.utils.GameVersion;
import cz.craftmania.craftcore.spigot.utils.reflections.ReflectionUtils;
import cz.craftmania.craftcore.core.utils.Group;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager extends EntityManager {

    /**
     * Creates a new PlayerManager instance
     *
     * @param player the player
     */
    public PlayerManager(Player player) {
        super(player);
    }

    private Player getPlayer() {
        return (Player) this.entity;
    }

    /**
     * Gets the ping number of that player
     *
     * @return the player ping
     */
    public int getPing() {
        String v = GameVersion.getVersion().toString();
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer");
            Class<?> nmsEntityPlayerClass = Class.forName("net.minecraft.server." + v + ".EntityPlayer");
            Object craftPlayer = ReflectionUtils.cast(craftPlayerClass, getPlayer());
            Object nmsEntity = ReflectionUtils.getMethod("getHandle", craftPlayerClass, craftPlayer);
            return (int) ReflectionUtils.getField("ping", nmsEntityPlayerClass, nmsEntity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void respawn() {
        String v = GameVersion.getVersion().toString();
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer");
            Class<?> nmsEntityPlayerClass = Class.forName("net.minecraft.server." + v + ".EntityPlayer");
            Class<?> playerConnClass = Class.forName("net.minecraft.server." + v + ".PlayerConnection");
            Class<?> packetClass = Class.forName("net.minecraft.server." + v + ".PacketPlayInClientCommand");
            Class<?> enumClass = Class.forName("net.minecraft.server." + v + "." + (v.equals(GameVersion.v1_8_R1.toString()) ? "" : "PacketPlayInClientCommand$") + "EnumClientCommand");
            Object craftPlayer = ReflectionUtils.cast(craftPlayerClass, getPlayer());
            Object nmsEntityPlayer = ReflectionUtils.getMethod("getHandle", craftPlayerClass, craftPlayer);
            Object enum_ = ReflectionUtils.getEnum("PERFORM_RESPAWN", enumClass);
            Object packet = ReflectionUtils.getConstructor(packetClass, new Group<>(
                    new Class<?>[]{enumClass},
                    new Object[]{enum_}
            ));
            Object playerConn = ReflectionUtils.getField("playerConnection", nmsEntityPlayerClass, nmsEntityPlayer);
            ReflectionUtils.getMethod("a", playerConnClass, playerConn, new Group<>(
                    new Class<?>[]{packetClass},
                    new Object[]{packet}
            ));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
