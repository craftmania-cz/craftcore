package cz.craftmania.craftcore.bungee.annotations;

import java.lang.annotation.*;

@Inherited
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketHandler {
}
