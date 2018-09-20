package cz.wake.craftcore.spigot.internal;

import cz.wake.craftcore.spigot.Main;
import cz.wake.craftcore.spigot.utils.files.YMLFile;

import java.io.File;

public class ServerData extends YMLFile {

    /** The instance. */
    static ServerData instance = new ServerData();

    /**
     * Gets the single instance of ServerData.
     *
     * @return single instance of ServerData
     */
    public static ServerData getInstance() {
        return instance;
    }

    /**
     * Instantiates a new server data.
     */
    public ServerData() {
        super(new File(Main.getInstance().getDataFolder(), "serverdata.yml"));
    }

    /**
     * Gets the plugin version.
     *
     * @param plugin the plugin
     * @return the plugin version
     */
    public String getPluginVersion(Main plugin) {
        return getData().getString("PluginVersions." + plugin.getName(), "");
    }

    /**
     * Gets the prev day.
     *
     * @return the prev day
     */
    public int getPrevDay() {
        return getData().getInt("PrevDay", -1);
    }

    /**
     * Gets the prev month.
     *
     * @return the prev month
     */
    public String getPrevMonth() {
        return getData().getString("Month", "");
    }

    /**
     * Gets the prev week day.
     *
     * @return the prev week day
     */
    public int getPrevWeekDay() {
        return getData().getInt("PrevWeek", -1);
    }

    @Override
    public void onFileCreation() {
    }

    public void setData(String path, Object value) {
        getData().set(path, value);
        saveData();
    }

    /**
     * Sets the plugin version.
     *
     * @param plugin the new plugin version
     */
    public void setPluginVersion(Main plugin) {
        getData().set("PluginVersions." + plugin.getName(), plugin.getDescription().getVersion());
        saveData();
    }

    /**
     * Sets the prev day.
     *
     * @param day the new prev day
     */
    public void setPrevDay(int day) {
        getData().set("PrevDay", day);
        saveData();
    }

    /**
     * Sets the prev month.
     *
     * @param month the new prev month
     */
    public void setPrevMonth(String month) {
        getData().set("Month", month);
        saveData();
    }

    /**
     * Sets the prev week day.
     *
     * @param week the new prev week day
     */
    public void setPrevWeekDay(int week) {
        getData().set("PrevWeek", week);
        saveData();
    }
}
