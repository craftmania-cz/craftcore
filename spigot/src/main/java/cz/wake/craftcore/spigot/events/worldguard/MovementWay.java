package cz.wake.craftcore.spigot.events.worldguard;

public enum MovementWay {

    MOVE("MOVE", 0),
    TELEPORT("TELEPORT", 1),
    SPAWN("SPAWN", 2),
    DISCONNECT("DISCONNECT", 3);

    private MovementWay(final String s, final int n) {
    }
}
