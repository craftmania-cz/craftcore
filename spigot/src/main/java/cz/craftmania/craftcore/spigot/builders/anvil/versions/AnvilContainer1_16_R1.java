package cz.craftmania.craftcore.spigot.builders.anvil.versions;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.ContainerAccess;
import net.minecraft.server.v1_16_R3.ContainerAnvil;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class AnvilContainer1_16_R1 extends ContainerAnvil {

    public AnvilContainer1_16_R1(Player player, int containerId) {
        super(containerId, ((CraftPlayer) player).getHandle().inventory,
                ContainerAccess.at(((CraftWorld) player.getWorld()).getHandle(), new BlockPosition(0, 0, 0)));
        this.checkReachable = false;
        setTitle(new ChatMessage("Repair & Name"));
    }

    @Override
    public void e() {
        super.e();
    }

    public int getContainerId() {
        return windowId;
    }

}

