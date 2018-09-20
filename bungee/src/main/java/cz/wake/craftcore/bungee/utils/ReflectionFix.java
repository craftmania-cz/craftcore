package cz.wake.craftcore.bungee.utils;

import java.lang.reflect.Field;

public class ReflectionFix {

    public static void setField(String field, Class<?> clazz, Object obj, Object value){
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, value);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object getField(String field, Class<?> clazz, Object obj){
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(obj);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
