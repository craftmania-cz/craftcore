package cz.craftmania.craftcore.spigot.bungee.responses;

import java.util.UUID;

public abstract class BungeePlayerUUIDResponse extends BungeeResponse {
    public abstract void result(UUID uuid);
}
