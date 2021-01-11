package cz.craftmania.craftcore.spigot.nbt;

import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import net.minecraft.server.v1_16_R3.NBTTagString;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NBTEdit {

    private static void setNBTBoolean(@NotNull NBTTagCompound nbt, @NotNull String key, boolean value) {
        int index = key.indexOf('.');
        if (index == -1) {
            nbt.setBoolean(key, value);
            return;
        }

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        NBTTagCompound child = nbt.getCompound(k);
        if (child == null)
            child = new NBTTagCompound();

        setNBTBoolean(child, subKey, value);
        nbt.set(k, child);
    }

    private static void setNBTInteger(@NotNull NBTTagCompound nbt, @NotNull String key, int value) {
        int index = key.indexOf('.');
        if (index == -1) {
            nbt.setInt(key, value);
            return;
        }

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        NBTTagCompound child = nbt.getCompound(k);
        if (child == null)
            child = new NBTTagCompound();

        setNBTInteger(child, subKey, value);
        nbt.set(k, child);
    }

    private static void setNBTLong(@NotNull NBTTagCompound nbt, @NotNull String key, long value) {
        int index = key.indexOf('.');
        if (index == -1) {
            nbt.setLong(key, value);
            return;
        }

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        NBTTagCompound child = nbt.getCompound(k);
        if (child == null)
            child = new NBTTagCompound();

        setNBTLong(child, subKey, value);
        nbt.set(k, child);
    }

    private static void setNBTString(@NotNull NBTTagCompound nbt, @NotNull String key, String value) {
        int index = key.indexOf('.');
        if (index == -1) {
            nbt.setString(key, value);
            return;
        }

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        NBTTagCompound child = nbt.getCompound(k);
        if (child == null)
            child = new NBTTagCompound();

        setNBTString(child, subKey, value);
        nbt.set(k, child);
    }

    private static void setNBTIntArray(@NotNull NBTTagCompound nbt, @NotNull String key, @NotNull int[] value) {
        int index = key.indexOf('.');
        if (index == -1) {
            nbt.setIntArray(key, value);
            return;
        }

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        NBTTagCompound child = nbt.getCompound(k);
        if (child == null)
            child = new NBTTagCompound();

        setNBTIntArray(child, subKey, value);
        nbt.set(k, child);
    }

    private static void setNBTStringArray(@NotNull NBTTagCompound nbt, @NotNull String key, @NotNull String[] value) {
        int index = key.indexOf('.');
        if (index == -1) {
            NBTTagList idsTag = new NBTTagList();
            for (String v : value)
                idsTag.add(NBTTagString.a(v));

            nbt.set(key, idsTag);
            return;
        }

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        NBTTagCompound child = nbt.getCompound(k);
        if (child == null)
            child = new NBTTagCompound();

        setNBTStringArray(child, subKey, value);
        nbt.set(k, child);
    }

    public static ItemStack setNBTBoolean(@NotNull ItemStack is, @NotNull String key, boolean value) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound == null)
                compound = new NBTTagCompound();
            {
                setNBTBoolean(compound, key, value);
            }
            nms.setTag(compound);
        }
        return CraftItemStack.asBukkitCopy(nms);
    }

    public static ItemStack setNBTInteger(@NotNull ItemStack is, @NotNull String key, int value) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound == null)
                compound = new NBTTagCompound();
            {
                setNBTInteger(compound, key, value);
            }
            nms.setTag(compound);
        }
        return CraftItemStack.asBukkitCopy(nms);
    }

    public static ItemStack setNBTLong(@NotNull ItemStack is, @NotNull String key, long value) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound == null)
                compound = new NBTTagCompound();
            {
                setNBTLong(compound, key, value);
            }
            nms.setTag(compound);
        }
        return CraftItemStack.asBukkitCopy(nms);
    }

    public static ItemStack setNBTString(@NotNull ItemStack is, @NotNull String key, String value) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound == null)
                compound = new NBTTagCompound();
            {
                setNBTString(compound, key, value);
            }
            nms.setTag(compound);
        }
        return CraftItemStack.asBukkitCopy(nms);
    }

    public static ItemStack setNBTIntArray(@NotNull ItemStack is, @NotNull String key, @NotNull int[] value) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound == null)
                compound = new NBTTagCompound();
            {
                setNBTIntArray(compound, key, value);
            }
            nms.setTag(compound);
        }
        return CraftItemStack.asBukkitCopy(nms);
    }

    public static ItemStack setNBTStringArray(@NotNull ItemStack is, @NotNull String key, @NotNull String[] value) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound == null)
                compound = new NBTTagCompound();
            {
                setNBTStringArray(compound, key, value);
            }
            nms.setTag(compound);
        }
        return CraftItemStack.asBukkitCopy(nms);
    }

    public static ItemStack setNBTStringArray(@NotNull ItemStack is, @NotNull String key, List<String> value) {
        return setNBTStringArray(is, key, value.toArray(new String[0]));
    }

    private static boolean getNBTBoolean(@NotNull NBTTagCompound nbt, @NotNull String key, boolean default_) {
        int index = key.indexOf('.');
        if (index == -1)
            return nbt.getBoolean(key);

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        if (nbt.hasKey(k))
            return getNBTBoolean(nbt.getCompound(k), subKey, default_);
        else
            return default_;
    }

    private static int getNBTInteger(@NotNull NBTTagCompound nbt, @NotNull String key, int default_) {
        int index = key.indexOf('.');
        if (index == -1)
            return nbt.getInt(key);

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        if (nbt.hasKey(k))
            return getNBTInteger(nbt.getCompound(k), subKey, default_);
        else
            return default_;
    }

    private static long getNBTLong(@NotNull NBTTagCompound nbt, @NotNull String key, long default_) {
        int index = key.indexOf('.');
        if (index == -1)
            return nbt.getLong(key);

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        if (nbt.hasKey(k))
            return getNBTLong(nbt.getCompound(k), subKey, default_);
        else
            return default_;
    }

    private static String getNBTString(@NotNull NBTTagCompound nbt, @NotNull String key, String default_) {
        int index = key.indexOf('.');
        if (index == -1)
            return nbt.getString(key);

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        if (nbt.hasKey(k))
            return getNBTString(nbt.getCompound(k), subKey, default_);
        else
            return default_;
    }

    private static int[] getNBTIntArray(@NotNull NBTTagCompound nbt, @NotNull String key, int[] default_) {
        int index = key.indexOf('.');
        if (index == -1)
            return nbt.getIntArray(key);

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        if (nbt.hasKey(k))
            return getNBTIntArray(nbt.getCompound(k), subKey, default_);
        else
            return default_;
    }

    private static String[] getNBTStringArray(@NotNull NBTTagCompound nbt, @NotNull String key, String[] default_) {
        int index = key.indexOf('.');
        if (index == -1) {
            NBTBase tagListBase = nbt.get(key);
            if (tagListBase instanceof NBTTagList) {
                NBTTagList tagList = (NBTTagList) tagListBase;
                List<String> list = new ArrayList<>();
                {
                    for (NBTBase nbtVal : tagList) {
                        if (nbtVal == null)
                            list.add(null);
                        else
                            list.add(nbtVal.asString());
                    }
                }
                return list.toArray(new String[0]);
            } else
                return default_;
        }

        String k = key.substring(0, index);
        String subKey = key.substring(index + 1);

        if (nbt.hasKey(k))
            return getNBTStringArray(nbt.getCompound(k), subKey, default_);
        else
            return default_;
    }

    public static boolean getNBTBoolean(@NotNull ItemStack is, @NotNull String key, boolean default_) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound != null)
                return getNBTBoolean(compound, key, default_);
        }

        return default_;
    }

    public static int getNBTInt(@NotNull ItemStack is, @NotNull String key, int default_) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound != null)
                return getNBTInteger(compound, key, default_);
        }

        return default_;
    }

    public static long getNBTLong(@NotNull ItemStack is, @NotNull String key, long default_) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound != null)
                return getNBTLong(compound, key, default_);
        }

        return default_;
    }

    public static int getNBTInt(@NotNull ItemStack is, @NotNull String key) {
        return getNBTInt(is, key, 0);
    }

    public static String getNBTString(@NotNull ItemStack is, @NotNull String key, String default_) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound != null)
                return getNBTString(compound, key, default_);
        }

        return default_;
    }

    public static String getNBTString(@NotNull ItemStack is, @NotNull String key) {
        return getNBTString(is, key, null);
    }

    public static int[] getNBTIntArray(@NotNull ItemStack is, @NotNull String key, int[] default_) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound != null)
                return getNBTIntArray(compound, key, default_);
        }

        return default_;
    }

    public static int[] getNBTIntArray(@NotNull ItemStack is, @NotNull String key) {
        return getNBTIntArray(is, key, null);
    }

    public static String[] getNBTStringArray(@NotNull ItemStack is, @NotNull String key, String[] default_) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(is);
        {
            NBTTagCompound compound = nms.getTag();
            if (compound != null)
                return getNBTStringArray(compound, key, default_);
        }

        return default_;
    }

    public static String[] getNBTStringArray(@NotNull ItemStack is, @NotNull String key) {
        return getNBTStringArray(is, key, null);
    }
}
