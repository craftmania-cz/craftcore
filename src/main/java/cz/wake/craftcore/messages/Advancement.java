package cz.wake.craftcore.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cz.wake.craftcore.Main;
import cz.wake.craftcore.messages.handler.AdvancementManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public class Advancement {

    private static final Gson gson;
    private NamespacedKey id;
    private String parent;
    private String icon;
    private String background;
    private String title;
    private String description;
    private AdvancementManager.FrameType frame;
    private boolean announce;
    private boolean toast;
    private boolean hidden;

    static {
        gson = new Gson();
    }

    private Advancement(final NamespacedKey id, final String parent, final String icon, final String background, final String title, final String description, final AdvancementManager.FrameType frame, final boolean announce, final boolean toast, final boolean hidden) {
        this.announce = true;
        this.toast = true;
        this.hidden = true;
        this.id = id;
        this.parent = parent;
        this.icon = icon;
        this.background = background;
        this.title = title;
        this.description = description;
        this.frame = frame != null ? frame : AdvancementManager.FrameType.TASK;
        this.announce = announce;
        this.toast = toast;
        this.hidden = hidden;
    }

    public static AdvancementBuilder builder(final NamespacedKey namespacedKey) {
        return new AdvancementBuilder().id(namespacedKey);
    }

    public String getJSON() {
        final JsonObject jsonObject = new JsonObject();
        final JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("item", this.getIcon());
        final JsonObject jsonObject3 = new JsonObject();
        jsonObject3.add("icon", jsonObject2);
        jsonObject3.add("title", getJsonFromComponent("\"" + this.getTitle() + "\""));
        jsonObject3.add("description", getJsonFromComponent("\"" + this.getDescription() + "\""));
        jsonObject3.addProperty("background", this.getBackground());
        jsonObject3.addProperty("frame", this.getFrame().toString());
        jsonObject3.addProperty("announce_to_chat", this.announce);
        jsonObject3.addProperty("show_toast", this.toast);
        jsonObject3.addProperty("hidden", this.hidden);
        jsonObject.addProperty("parent", this.getParent());
        final JsonObject jsonObject4 = new JsonObject();
        final JsonObject jsonObject5 = new JsonObject();
        jsonObject5.addProperty("trigger", "minecraft:impossible");
        jsonObject4.add("IMPOSSIBLE", jsonObject5);
        jsonObject.add("criteria", jsonObject4);
        jsonObject.add("display", jsonObject3);
        return new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject);
    }

    public String getIcon() {
        return this.icon;
    }

    public static JsonElement getJsonFromComponent(String s) {
        s = s.replace("\\n", "XlineBreakX");
        s = s.replace("\\", "\\\\");
        s = s.replace("XlineBreakX", "\\n");
        return (JsonElement) Advancement.gson.fromJson(s, (Class) JsonElement.class);
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getBackground() {
        return this.background;
    }

    public AdvancementManager.FrameType getFrame() {
        return this.frame;
    }

    public String getParent() {
        return this.parent;
    }

    public Advancement show(final JavaPlugin javaPlugin, final Player... array) {
        this.add();
        this.grant(array);
        Bukkit.getScheduler().runTaskLater(javaPlugin, () -> {
            Advancement.this.revoke(array);
            Main.getInstance().getNMS().removeAdvancement(Advancement.this);
        }, 20L);
        return this;
    }

    public Advancement add() {
        try {
            Main.getInstance().getNMS().loadAdvancement(this.id, this.getJSON());
        } catch (IllegalArgumentException ex) {
        }
        return this;
    }

    public Advancement grant(final Player... array) {
        final org.bukkit.advancement.Advancement advancement = this.getAdvancement();
        for (final Player player : array) {
            if (!player.getAdvancementProgress(advancement).isDone()) {
                final Iterator iterator = player.getAdvancementProgress(advancement).getRemainingCriteria().iterator();
                while (iterator.hasNext()) {
                    player.getAdvancementProgress(this.getAdvancement()).awardCriteria((String) iterator.next());
                }
            }
        }
        return this;
    }

    public Advancement revoke(final Player... array) {
        final org.bukkit.advancement.Advancement advancement = this.getAdvancement();
        if (advancement == null) {
            return this;
        }
        for (final Player player : array) {
            if (player.getAdvancementProgress(advancement) != null) {
                if (player.getAdvancementProgress(advancement).isDone()) {
                    final Iterator iterator = player.getAdvancementProgress(advancement).getAwardedCriteria().iterator();
                    while (iterator.hasNext()) {
                        player.getAdvancementProgress(this.getAdvancement()).revokeCriteria((String) iterator.next());
                    }
                }
            }
        }
        return this;
    }

    public org.bukkit.advancement.Advancement getAdvancement() {
        return Bukkit.getAdvancement(this.id);
    }

    public NamespacedKey getId() {
        return this.id;
    }

    public boolean isAnnounce() {
        return this.announce;
    }

    public boolean isToast() {
        return this.toast;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public static class AdvancementBuilder {
        private NamespacedKey id;
        private String parent;
        private String icon;
        private String background;
        private String title;
        private String description;
        private AdvancementManager.FrameType frame;
        private boolean announce;
        private boolean toast;
        private boolean hidden;

        public AdvancementBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public AdvancementBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public AdvancementBuilder id(final NamespacedKey id) {
            this.id = id;
            return this;
        }

        public AdvancementBuilder parent(final String parent) {
            this.parent = parent;
            return this;
        }

        public AdvancementBuilder icon(final String icon) {
            this.icon = icon;
            return this;
        }

        public AdvancementBuilder background(final String background) {
            this.background = background;
            return this;
        }

        public AdvancementBuilder frame(final AdvancementManager.FrameType frame) {
            this.frame = frame;
            return this;
        }

        public AdvancementBuilder announce(final boolean announce) {
            this.announce = announce;
            return this;
        }

        public AdvancementBuilder toast(final boolean toast) {
            this.toast = toast;
            return this;
        }

        public AdvancementBuilder hidden(final boolean hidden) {
            this.hidden = hidden;
            return this;
        }

        public Advancement build() {
            return new Advancement(this.id, this.parent, this.icon, this.background, this.title, this.description, this.frame, this.announce, this.toast, this.hidden);
        }
    }
}
