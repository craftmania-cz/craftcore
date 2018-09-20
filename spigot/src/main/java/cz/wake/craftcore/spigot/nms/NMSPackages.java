package cz.wake.craftcore.spigot.nms;

import cz.wake.craftcore.spigot.messages.Advancement;
import org.bukkit.NamespacedKey;

public interface NMSPackages {

    void loadAdvancement(final NamespacedKey p0, final String p1);

    void removeAdvancement(final Advancement p0);
}
