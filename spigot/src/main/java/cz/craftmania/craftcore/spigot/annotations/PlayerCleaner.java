package cz.craftmania.craftcore.spigot.annotations;

import java.lang.annotation.*;

@Inherited
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlayerCleaner {
}
