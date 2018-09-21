package cz.craftmania.craftcore.spigot.bungee.responses;

import java.util.List;

public abstract class BungeePlayerListResponse extends BungeeResponse {
    public abstract void result(String server, List<String> players);
}
