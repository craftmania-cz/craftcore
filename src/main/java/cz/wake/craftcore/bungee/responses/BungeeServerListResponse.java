package cz.wake.craftcore.bungee.responses;

import java.util.List;

public abstract class BungeeServerListResponse extends BungeeResponse {
    public abstract void result(List<String> servers);
}
