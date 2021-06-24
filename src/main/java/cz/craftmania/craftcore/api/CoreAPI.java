package cz.craftmania.craftcore.api;

import cz.craftmania.craftcore.Main;

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
