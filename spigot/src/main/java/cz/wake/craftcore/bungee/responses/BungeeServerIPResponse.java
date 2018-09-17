package cz.wake.craftcore.bungee.responses;

public abstract class BungeeServerIPResponse extends BungeeResponse {
    public abstract void result(String server, String ip, int port);
}
