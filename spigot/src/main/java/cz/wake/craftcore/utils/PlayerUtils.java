package cz.wake.craftcore.utils;

import com.google.common.collect.Iterables;
import cz.wake.craftcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class PlayerUtils {

    /**
     * The instance.
     */
    static PlayerUtils instance = new PlayerUtils();

    public static PlayerUtils getInstance() {
        return instance;
    }

    private PlayerUtils() {
    }

    /**
     * Gets the player meta.
     *
     * @param player the player
     * @param str    the string
     * @return the player meta
     */
    public Object getPlayerMeta(Player player, String str) {
        for (MetadataValue meta : player.getMetadata(str)) {
            if (meta.getOwningPlugin().equals(Main.getInstance())) {
                return meta.value();
            }
        }
        return null;
    }

    /**
     * Gets random online player from server
     *
     * @return randomly selected player
     */
    public Player getRandomPlayer() {
        return Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
    }

    /**
     * Checks if is player online.
     *
     * @param playerName the player name
     * @return true, if is player online
     */
    public boolean isPlayerOnline(String playerName) {
        if (playerName == null) {
            return false;
        }
        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            return true;
        }
        return false;
    }

    /**
     * Sets the player meta.
     *
     * @param player the player
     * @param str    the str
     * @param value  the value
     */
    public void setPlayerMeta(Player player, String str, Object value) {
        player.removeMetadata(str, Main.getInstance());
        player.setMetadata(str, new MetadataValue() {

            @Override
            public boolean asBoolean() {

                return false;
            }

            @Override
            public byte asByte() {

                return 0;
            }

            @Override
            public double asDouble() {

                return 0;
            }

            @Override
            public float asFloat() {

                return 0;
            }

            @Override
            public int asInt() {

                return 0;
            }

            @Override
            public long asLong() {

                return 0;
            }

            @Override
            public short asShort() {

                return 0;
            }

            @Override
            public String asString() {

                return null;
            }

            @Override
            public Main getOwningPlugin() {
                return Main.getInstance();
            }

            @Override
            public void invalidate() {
            }

            @Override
            public Object value() {
                return value;
            }

        });
    }
}
