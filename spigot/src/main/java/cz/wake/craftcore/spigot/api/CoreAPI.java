package cz.wake.craftcore.api;

import cz.wake.craftcore.Main;

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
