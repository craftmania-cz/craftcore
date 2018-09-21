package cz.craftmania.craftcore.spigot.bungee.responses;

public abstract class BungeePlayerAmountResponse extends BungeeResponse {
    public abstract void result(String server, int amount);
}
