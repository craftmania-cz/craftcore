package cz.craftmania.craftcore.messages;

import cz.craftmania.craftcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

public class BossBar {

    /**
     * The boss bar.
     */
    public org.bukkit.boss.BossBar bossBar;

    /**
     * Instantiates a new boss bar.
     *
     * @param msg      the msg
     * @param barColor the bar color
     * @param barStyle the bar style
     * @param progress the progress
     */
    public BossBar(String msg, String barColor, String barStyle, double progress) {
        bossBar = Bukkit.createBossBar(msg, BarColor.valueOf(barColor), BarStyle.valueOf(barStyle), BarFlag.DARKEN_SKY);
        bossBar.setProgress(progress);
    }

    /**
     * Adds player to the boss bar (can see it)
     *
     * @param player selected player
     */
    public void addPlayer(Player player) {
        bossBar.addPlayer(player);
    }

    /**
     * Remove player from the boss (cannot see it)
     *
     * @param player selected player
     */
    public void removePlayer(Player player) {
        bossBar.removePlayer(player);
    }

    /**
     * Hide bossbar for all
     */
    public void hide() {
        bossBar.setVisible(false);
        bossBar.removeAll();
    }

    /**
     * Sends bossbar and after selected delay the bossbar will hide
     *
     * @param delay delay in seconds
     */
    public void send(int delay) {
        bossBar.setVisible(true);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), this::hide, delay);
    }

    /**
     * Send.
     *
     * @param player the player
     * @param delay  the delay
     */
    public void send(Player player, int delay) {
        bossBar.addPlayer(player);
        bossBar.setVisible(true);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), this::hide, delay);
    }

    /**
     * Sets color of the Bossbar
     *
     * @param barColor {@link BarColor}
     */
    public void setColor(String barColor) {
        bossBar.setColor(BarColor.valueOf(barColor));
    }

    /**
     * Sets progres of the Bossbar
     *
     * @param progress Double value
     */
    public void setProgress(double progress) {
        if (progress > 1) {
            progress = 1;
        }
        if (progress < 0) {
            progress = 0;
        }
        bossBar.setProgress(progress);
    }

    /**
     * Sets style of the bossbar
     *
     * @param barStyle {@link BarStyle}
     */
    public void setStyle(String barStyle) {
        bossBar.setStyle(BarStyle.valueOf(barStyle));
    }

    /**
     * Sets title of the bossbar
     *
     * @param title the title
     */
    public void setTitle(String title) {
        bossBar.setTitle(title);
    }

    /**
     * Sets visible state of the bossbar
     *
     * @param visible true if visible for players
     */
    public void setVisible(boolean visible) {
        bossBar.setVisible(visible);
    }
}
