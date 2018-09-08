package cz.wake.craftcore.utils;

import cz.wake.craftcore.internal.thread.FileThread;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public class FilesManager {

    /**
     * The instance.
     */
    static FilesManager instance = new FilesManager();

    /**
     * Gets the single instance of FilesManager.
     *
     * @return single instance of FilesManager
     */
    public static FilesManager getInstance() {
        return instance;
    }

    /**
     * Instantiates a new files manager.
     */
    private FilesManager() {
    }

    /**
     * Edits the file.
     *
     * @param file the file
     * @param data the data
     */
    public void editFile(File file, FileConfiguration data) {
        FileThread.getInstance().run(() -> {
            try {
                data.save(file);
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save " + file.getName());
            }

        });
    }
}
