package cz.wake.craftcore.spigot.api;

import cz.wake.craftcore.spigot.Main;

public class CoreAPI {

    /**
     * Metoda k ziskani instance pluginu CraftCore
     *
     * @return Main.getInstance()
     */
    public static Main getCoreInstance() {
        return Main.getInstance();
    }
}
