package cz.craftmania.craftcore.bungee;

import cz.craftmania.craftcore.bungee.listeners.PacketListener;
import cz.craftmania.craftcore.bungee.listeners.PlayerCleanerListener;
import cz.craftmania.craftcore.bungee.listeners.SpigotListener;
import cz.craftmania.craftcore.bungee.mojang.SkinAPI;
import cz.craftmania.craftcore.bungee.tasks.CachedSkinTask;
import cz.craftmania.craftcore.core.files.DirectoryManager;
import cz.craftmania.craftcore.core.files.FileManager;
import cz.craftmania.craftcore.core.utils.IOUtils;
import cz.craftmania.craftcore.core.utils.ProxyUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class Main extends Plugin {

    public static Main instance;
    public final static String CRAFTCORE_CHANNEL = "craftcore:plugin";
    public final static File ROOT_FOLDER = new File("plugins/CraftCore/");
    public final static File SKINS_FOLDER = new File(ROOT_FOLDER, "skins/");
    public final static File CONFIG_FILE = new File(ROOT_FOLDER, "src/main/resources/config.yml");
    public static Configuration config;

    @Override
    public void onEnable() {
        instance = this;

        try {
            new DirectoryManager(ROOT_FOLDER).mkdirs();
            new DirectoryManager(SKINS_FOLDER).mkdirs();
            new FileManager(CONFIG_FILE).initFile(IOUtils.toByteArray(getClass().getResourceAsStream("/config.yml")));
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(CONFIG_FILE);
        } catch(IOException e) {
            e.printStackTrace();
        }

        new SkinAPI();
        for(String proxy : config.getStringList("proxies")){
            String[] x = proxy.split(":");
            ProxyUtils.put(x[0], Integer.parseInt(x[1]));
        }

        if(config.getBoolean("auto_renew_skin", false)) {
            getProxy().getScheduler().schedule(this, new CachedSkinTask(), 0, 60, TimeUnit.SECONDS);
        }

        getProxy().getPluginManager().registerListener(this, new SpigotListener());
        getProxy().getPluginManager().registerListener(this, new PlayerCleanerListener());
        if(config.getBoolean("packet_handler", false)){
            getProxy().getPluginManager().registerListener(this, new PacketListener());
        }

        getProxy().registerChannel(CRAFTCORE_CHANNEL);

    }

    @Override
    public void onDisable() {
        for(ProxiedPlayer player : getProxy().getPlayers()){
            PacketListener.remove(player);
        }
    }





}
