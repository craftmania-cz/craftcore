package cz.craftmania.craftcore.builders.anvil.versions;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerAnvil;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class AnvilContainer1_16_R1 extends ContainerAnvil {

    public AnvilContainer1_16_R1(Player player, int containerId) {
        super(containerId, ((CraftPlayer) player).getHandle().getInventory(),
                ContainerAccess.at(((CraftWorld) player.getWorld()).getHandle(), new BlockPosition(0, 0, 0)));
        this.checkReachable = false;
        setTitle(new ChatMessage("Repair & Name"));
    }

    @Override
    public void e() {
        super.e();
    }

    //TODO: Fix
    //public int getContainerId() {
    //    return windowId;
    //}

}

