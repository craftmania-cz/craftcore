package cz.craftmania.craftcore.utils.effects;

import cz.craftmania.craftcore.Main;
import cz.craftmania.craftcore.utils.effects.versions.ParticleDisplay_12;
import cz.craftmania.craftcore.utils.effects.versions.ParticleDisplay_13;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class ParticleDisplay {
    private Main manager;

    public abstract void display(Particle particle, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount, float size, Color color, Material material, byte materialData, double range, List<Player> targetPlayers);

    protected void display(Particle particle, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount, Object data, double range, List<Player> targetPlayers) {
        try {
            if (targetPlayers == null) {
                String worldName = center.getWorld().getName();
                double squared = range * range;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.getWorld().getName().equals(worldName) || player.getLocation().distanceSquared(center) > squared) {
                        continue;
                    }
                    player.spawnParticle(particle, center, amount, offsetX, offsetY, offsetZ, speed, data);
                }
            } else {
                for (Player player : targetPlayers) {
                    player.spawnParticle(particle, center, amount, offsetX, offsetY, offsetZ, speed, data);
                }
            }
        } catch (Exception ex) {
            if (manager != null) {
                ex.printStackTrace();
            }
        }
    }

    protected void displayItem(Particle particle, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount, Material material, byte materialData, double range, List<Player> targetPlayers) {
        if (material == null || material == Material.AIR) {
            return;
        }

        ItemStack item = new ItemStack(material);
        item.setDurability(materialData);
        display(particle, center, offsetX, offsetY, offsetZ, speed, amount, item, range, targetPlayers);
    }

    protected void displayLegacyColored(Particle particle, Location center, float speed, Color color, double range, List<Player> targetPlayers) {
        int amount = 0;
        // Colored particles can't have a speed of 0.
        if (speed == 0) {
            speed = 1;
        }
        float offsetX = (float) color.getRed() / 255;
        float offsetY = (float) color.getGreen() / 255;
        float offsetZ = (float) color.getBlue() / 255;

        // The redstone particle reverts to red if R is 0!
        if (offsetX < Float.MIN_NORMAL) {
            offsetX = Float.MIN_NORMAL;
        }

        display(particle, center, offsetX, offsetY, offsetZ, speed, amount, null, range, targetPlayers);
    }

    public void setManager(Main manager) {
        this.manager = manager;
    }

    public static ParticleDisplay newInstance() {
        ParticleDisplay display;
        try {
            Particle.valueOf("SQUID_INK");
            display = new ParticleDisplay_13();
        } catch (Throwable not13) {
            display = new ParticleDisplay_12();
        }
        return display;
    }
}