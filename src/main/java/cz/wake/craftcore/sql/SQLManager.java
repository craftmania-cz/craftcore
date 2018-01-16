package cz.wake.craftcore.sql;

import com.zaxxer.hikari.HikariDataSource;
import cz.wake.craftcore.Main;

public class SQLManager {

    private final Main plugin;
    private final ConnectionPoolManager pool;
    private HikariDataSource dataSource;

    public SQLManager(Main plugin) {
        this.plugin = plugin;
        pool = new ConnectionPoolManager(plugin);
    }

    public void onDisable() {
        pool.closePool();
    }

    /**
     * Getting HikariCP pool
     *
     * @return Main MySQL pool of CraftCore (HikariCP)
     */
    public ConnectionPoolManager getPool() {
        return pool;
    }
}
