package cz.craftmania.craftcore.spigot.bungee.responses;

import java.util.UUID;

public abstract class BungeeOtherPlayerUUIDResponse extends BungeeResponse {
    public abstract void result(String player, UUID uuid);
}
