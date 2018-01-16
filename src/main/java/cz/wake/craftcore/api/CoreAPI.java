package cz.wake.craftcore.api;

import cz.wake.craftcore.Main;
import cz.wake.craftcore.annotations.NeedTesting;
import cz.wake.craftcore.sql.SQLManager;

public class CoreAPI {

    /**
     * Metoda k ziskani instance pluginu CraftCore
     *
     * @return Main.getInstance()
     */
    public static Main getCoreInstance() {
        return Main.getInstance();
    }

    /**
     * Metoda k ziskani instance SQLManageru
     *
     * @return SQLManager
     */
    @NeedTesting
    public SQLManager getSQL() {
        return Main.getInstance().getSQL();
    } //TODO: Fix NPE

    /**
     * Metoda k ziskani IDcka serveru (podle configu)
     *
     * @return ID serveru
     */
    public static String getIdServer() {
        return Main.getInstance().getIdServer();
    }
}
