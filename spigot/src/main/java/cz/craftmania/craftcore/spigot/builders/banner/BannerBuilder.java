package cz.craftmania.craftcore.spigot.builders.banner;

import cz.craftmania.craftcore.spigot.builders.items.ItemBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class BannerBuilder extends ItemBuilder {

    /**
     * Create new instance of banner
     */
    public BannerBuilder() {
        super(Material.WHITE_BANNER);
    }

    /**
     * Create new instance of banner
     * @param itemStack Convent exists banner
     */
    public BannerBuilder(final ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * Create new instance of banner
     * @param amount Amount of crated banners
     */
    public BannerBuilder(final int amount) {
        super(Material.WHITE_BANNER, amount);
    }

    /**
     * Add new pattern into banner
     * @param color Color of new pattern
     * @param pattern Selected pattern
     * @return {@BannerBuilder}
     */
    public BannerBuilder addPattern(final DyeColor color, final PatternType pattern) {
        BannerMeta bm;
        (bm = (BannerMeta)super.is.getItemMeta()).addPattern(new Pattern(color, pattern));
        super.is.setItemMeta(bm);
        return this;
    }
}
