package cz.wake.craftcore.spigot.events.spigot;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PlayerReceiveMessageEvent extends PlayerEvent implements Cancellable {

    private boolean cancelled;
    private static HandlerList handlers = new HandlerList();

    private EnumWrappers.ChatType type;
    private WrappedChatComponent message;

    /**
     * Fires when the server sends a message to a player
     *
     * @param who     The player receiving the message
     * @param type    The type of the message
     * @param message The contents of the message
     */
    public PlayerReceiveMessageEvent(Player who, EnumWrappers.ChatType type, WrappedChatComponent message) {
        super(who);
        this.type = type;
        this.message = message;
    }

    /**
     * Returns the chat type of the message, used by the client to specify
     * whether it should be showed with certain chat visibility settings.
     *
     * @return The type of the message being sent
     */
    public EnumWrappers.ChatType getType() {
        return type;
    }

    /**
     * Returns the chat message being sent to the player
     *
     * @return The chat message being sent to the player
     */
    public WrappedChatComponent getMessage() {
        return message;
    }

    /**
     * Converts the WrappedChatComponent to the text the client will see
     *
     * @return The text the client will actually see
     */
    public String toPlainText() {
        Object handle = message.getHandle();
        Class clazz = handle.getClass();
        try {
            Method m = clazz.getMethod("toPlainText");
            return String.valueOf(m.invoke(handle));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
