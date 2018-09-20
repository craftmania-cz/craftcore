package cz.wake.craftcore.spigot.bungee.responses;

public abstract class BungeePlayerIPResponse extends BungeeResponse {
    public abstract void result(String ip, int port);
}
