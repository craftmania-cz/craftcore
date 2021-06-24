package cz.craftmania.craftcore.inventory.builder.buttons;

import cz.craftmania.craftcore.builders.items.ItemBuilder;
import cz.craftmania.craftcore.inventory.builder.ClickableItem;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class ToggleButton extends ClickableItem {
    public static final ItemStack off;
    public static final ItemStack on;

    public ToggleButton(final ItemStack item, final Consumer<InventoryClickEvent> consumer) {
        super(item, consumer);
    }

    public ToggleButton(final String name, final List<String> desc, final boolean current, final Consumer<Boolean> mode) {
        this(new ItemBuilder(current ? ToggleButton.on : ToggleButton.off).setName(name).setLore(desc).build(), e -> mode.accept(!current));
    }

    public ToggleButton(final String name, final boolean current, final Consumer<Boolean> mode) {
        this(new ItemBuilder(current ? ToggleButton.on : ToggleButton.off).setName(name).build(), e -> mode.accept(!current));
    }

    static {
        off = new ItemStack(Material.INK_SAC, 1, (short) 14);
        on = new ItemStack(Material.INK_SAC, 1, (short) 5);
    }
}
