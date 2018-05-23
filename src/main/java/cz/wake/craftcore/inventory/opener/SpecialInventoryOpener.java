package cz.wake.craftcore.inventory.opener;

import cz.wake.craftcore.Main;
import cz.wake.craftcore.inventory.InventoryManager;
import cz.wake.craftcore.inventory.SmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

public class SpecialInventoryOpener implements InventoryOpener {

    private List<InventoryType> supported = Arrays.asList(
            InventoryType.FURNACE,
            InventoryType.WORKBENCH,
            InventoryType.DISPENSER,
            InventoryType.DROPPER,
            InventoryType.ENCHANTING,
            InventoryType.BREWING,
            InventoryType.ANVIL,
            InventoryType.BEACON,
            InventoryType.HOPPER
    );

    @Override
    public Inventory open(SmartInventory inv, Player player) {
        InventoryManager manager = Main.getInvManager();
        Inventory handle = Bukkit.createInventory(player, inv.getType(), inv.getTitle());

        fill(handle, manager.getContents(player).get());

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return supported.contains(type);
    }

}
