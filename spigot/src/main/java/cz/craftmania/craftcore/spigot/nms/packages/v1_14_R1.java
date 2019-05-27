package cz.craftmania.craftcore.spigot.nms.packages;

import cz.craftmania.craftcore.spigot.nms.NMSPackages;
import net.minecraft.server.v1_14_R1.Advancement;
import net.minecraft.server.v1_14_R1.AdvancementDataWorld;
import net.minecraft.server.v1_14_R1.ChatDeserializer;
import net.minecraft.server.v1_14_R1.MinecraftKey;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_14_R1.util.CraftNamespacedKey;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class v1_14_R1 implements NMSPackages {

    public void loadAdvancement(final NamespacedKey namespacedKey, final String s) {
        if (Bukkit.getAdvancement(namespacedKey) != null) {
            return;
        }
        final Advancement.SerializedAdvancement serializedAdvancement = ChatDeserializer.a(AdvancementDataWorld.DESERIALIZER, s, Advancement.SerializedAdvancement.class);
        if (serializedAdvancement != null) {
            AdvancementDataWorld.REGISTRY.a((Map<MinecraftKey, Advancement.SerializedAdvancement>) new HashMap(Collections.singletonMap(CraftNamespacedKey.toMinecraft(namespacedKey), serializedAdvancement)));
        }
    }

    public void removeAdvancement(final cz.craftmania.craftcore.spigot.messages.Advancement advancement) {
        Bukkit.getUnsafe().removeAdvancement(advancement.getId());
        for (final Map.Entry<MinecraftKey, Advancement> entry : AdvancementDataWorld.REGISTRY.advancements.entrySet()) {
            if (entry.getValue().getName().getKey().equals(advancement.getId().getKey().toLowerCase())) {
                AdvancementDataWorld.REGISTRY.advancements.remove(entry.getKey());
                break;
            }
        }
    }
}
