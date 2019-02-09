package cz.craftmania.craftcore.spigot.npc.skin;

public class JsonSkinData {

    private SkinData skindata;
    private long updated;

    public JsonSkinData(SkinData skindata, long updated){
        this.skindata = skindata;
        this.updated = updated;
    }

    public SkinData getSkinData() {
        return skindata;
    }

    public long getTimeUpdated() {
        return updated;
    }
}
