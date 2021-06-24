package cz.craftmania.craftcore.utils.server;

import cz.craftmania.craftcore.nms.NMSManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ServerPropertiesAPI {

    private static Class<?> minecraftServerClass;
    private static Class<?> propertyManagerClass;
    private static Method getServer;
    private static Method getPropertyManager;
    private static boolean invoked;

    /*
        Constructor
     */
    public ServerPropertiesAPI() {
        if (!ServerPropertiesAPI.invoked) {
            try {
                ServerPropertiesAPI.minecraftServerClass = NMSManager.getNMSClass("MinecraftServer");
                ServerPropertiesAPI.propertyManagerClass = NMSManager.getNMSClass("PropertyManager");
                ServerPropertiesAPI.getServer = ServerPropertiesAPI.minecraftServerClass.getDeclaredMethod("getServer", (Class<?>[]) new Class[0]);
                ServerPropertiesAPI.getPropertyManager = ServerPropertiesAPI.minecraftServerClass.getDeclaredMethod("getPropertyManager", (Class<?>[]) new Class[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ServerPropertiesAPI.invoked = true;
        }
    }

    /**
     * Sets requested property in server.properties
     *
     * @param path  Path to property
     * @param value Value to set
     * @return This class {@link #ServerPropertiesAPI()}
     */
    public ServerPropertiesAPI setProperty(final String path, final Object value) {
        try {
            final Method setProperty = ServerPropertiesAPI.propertyManagerClass.getDeclaredMethod("setProperty", String.class, Object.class);
            setProperty.invoke(ServerPropertiesAPI.getPropertyManager.invoke(ServerPropertiesAPI.getServer.invoke(null, new Object[0]), new Object[0]), path, value);
            final Method savePropertiesFile = ServerPropertiesAPI.propertyManagerClass.getDeclaredMethod("savePropertiesFile", (Class<?>[]) new Class[0]);
            savePropertiesFile.invoke(ServerPropertiesAPI.getPropertyManager.invoke(ServerPropertiesAPI.getServer.invoke(null, new Object[0]), new Object[0]), new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Returns value from selected path
     * ex. getProperty("server-port");
     *
     * @param path Path to requested value
     * @return Object like value
     */
    public Object getProperty(final String path) {
        Object object = null;
        try {
            final Field propertiesField = ServerPropertiesAPI.propertyManagerClass.getDeclaredField("properties");
            final Properties properties = (Properties) propertiesField.get(ServerPropertiesAPI.getPropertyManager.invoke(ServerPropertiesAPI.getServer.invoke(null, new Object[0]), new Object[0]));
            object = properties.getProperty(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * Gets all entries in server.properties (whole file)
     *
     * @return Entries as {@link Map.Entry}
     */
    public Set<Map.Entry<Object, Object>> getEntries() {
        Set<Map.Entry<Object, Object>> entries = null;
        try {
            final Field propertiesField = ServerPropertiesAPI.propertyManagerClass.getDeclaredField("properties");
            final Properties properties = (Properties) propertiesField.get(ServerPropertiesAPI.getPropertyManager.invoke(ServerPropertiesAPI.getServer.invoke(null, new Object[0]), new Object[0]));
            entries = properties.entrySet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }
}
