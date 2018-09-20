package cz.wake.craftcore.bungee.tasks;

import cz.wake.craftcore.bungee.mojang.CachedSkin;
import cz.wake.craftcore.bungee.mojang.SkinAPI;
import cz.wake.craftcore.core.files.FileManager;
import cz.wake.craftcore.core.utils.GZipUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CachedSkinTask implements Runnable {
    @Override
    public void run() {
        try {
            // clones...
            List<CachedSkin> a = new ArrayList<>(SkinAPI.getSkins().values());
            for(CachedSkin skin : a){
                boolean save = false;
                if(skin.isExpired()){
                    SkinAPI.renewSkin(skin.getOwner());
                    save = true;
                }
                File file = SkinAPI.getSkinFile(skin);
                if(save || !file.exists()) {
                    new FileManager(file).delete().initFile(GZipUtils.compress(SkinAPI.toJSON(skin)
                            .getBytes(StandardCharsets.UTF_8)));
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
