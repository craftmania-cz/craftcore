package cz.craftmania.craftcore.spigot.api;

import cz.craftmania.craftcore.spigot.Main;

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
