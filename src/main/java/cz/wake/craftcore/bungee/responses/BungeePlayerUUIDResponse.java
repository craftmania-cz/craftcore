package cz.wake.craftcore.bungee.responses;

import java.util.UUID;

public abstract class BungeePlayerUUIDResponse extends BungeeResponse {
    public abstract void result(UUID uuid);
}