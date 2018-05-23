package cz.wake.craftcore.utils.border;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

import java.util.ArrayList;

public class BorderAPI {

    private ArrayList<CustomBorder> borders = new ArrayList<CustomBorder>();
    private static BorderAPI instance;

    public BorderAPI(){
        instance = this;
    }

    /**
     * Get BorderAPI
     * @return BorderAPI class
     */
    public static BorderAPI getAPI(){
        return instance;
    }

    /**
     * Create a world border in selected world with custom radius
     * @param world World where will be the border
     * @param center Center of the border
     * @param radius Radius of the border
     * @return CustomBorder class
     */
    public CustomBorder createBorder(World world, Location center, double radius){
        CustomBorder border = new CustomBorder(world, center, radius);
        borders.add(border);
        return border;
    }

    /**
     * Create a world border in selected world with custom WorldBorder
     * @param world World where will be the border
     * @param worldBorder Border in world (Vanilla)
     * @return CustomBorder class
     */
    public CustomBorder createBorder(World world, WorldBorder worldBorder){
        CustomBorder border = new CustomBorder(world, worldBorder);
        borders.add(border);
        return border;
    }

    /**
     * Create a wordborder with custom center and radius
     * @param world Selected World
     * @param centerX X of the border
     * @param centerZ Z of the border
     * @param radius Radius from X-Z
     * @return CustomBorder class
     */
    public CustomBorder createBorder(World world, double centerX, double centerZ, double radius){
        CustomBorder border = new CustomBorder(world, centerX, centerZ, radius);
        borders.add(border);
        return border;
    }

    /**
     * Get border by the selected world
     * @param world Selected world
     * @return CustomBorder class
     */
    public CustomBorder getBorder(World world){
        if(borders.isEmpty())return null;
        for(CustomBorder b : borders){
            if(b.getWorld().getName().equals(world.getName()))return b;
        }
        return null;
    }
}
