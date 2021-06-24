package cz.craftmania.craftcore.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoreLogger {

    /**
     * public LoggerController info(@Nullable String message)
     *
     * @param message String {@link String}
     * @return LoggerController
     */
    public CoreLogger info(@Nullable String message) {
        log(CoreLogger.Level.INFO, message);
        return this;
    }

    /**
     * public LoggerController warn(@Nullable String message)
     *
     * @param message String {@link String}
     * @return LoggerController
     */

    public CoreLogger warn(@Nullable String message) {
        log(CoreLogger.Level.WARNING, message);
        return this;
    }

    /**
     * public LoggerController error(@Nullable String message)
     *
     * @param message String {@link String}
     * @return LoggerController
     */

    public CoreLogger error(@Nullable String message) {
        log(CoreLogger.Level.ERROR, message);
        return this;
    }

    /**
     * public LoggerController severe(@Nullable String message)
     *
     * @param message String {@link String}
     * @return LoggerController
     */
    public CoreLogger severe(@Nullable String message) {
        log(CoreLogger.Level.SEVERE, message);
        return this;
    }

    /**
     * public LoggerController debug(Object instance, @Nullable String message)
     *
     * @param instance Object {@link Object}
     * @param message  String {@link String}
     * @return LoggerController
     */
    public CoreLogger debug(Object instance, @Nullable String message) {
        log(CoreLogger.Level.DEBUG, instance.getClass().getName().split("\\.")[instance.getClass().getName().split("\\.").length - 1] + " " + message);
        return this;
    }

    /**
     * private LoggerController log(@NotNull LoggerController.Level level, @Nullable String message)
     *
     * @param level   Level {@link CoreLogger.Level}
     * @param message String {@link String}
     * @return LoggerController
     */
    private CoreLogger log(@NotNull CoreLogger.Level level, @Nullable String message) {
        Bukkit.getConsoleSender().sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', level.getPrefix() + (message != null ? message : "null")));
        return this;
    }

    public enum Level {

        INFO(org.bukkit.ChatColor.YELLOW + "[INFO]"),
        WARNING(org.bukkit.ChatColor.GOLD + "[WARN]"),
        ERROR(org.bukkit.ChatColor.RED + "[ERROR]"),
        SEVERE(org.bukkit.ChatColor.DARK_RED + "[SEVERE]"),
        DEBUG(org.bukkit.ChatColor.WHITE + "[DEBUG]");

        private @NotNull
        final String prefix;

        Level(@NotNull String prefix) {
            this.prefix = prefix;
        }

        @org.jetbrains.annotations.Contract(pure = true)
        public final @NotNull String getPrefix() {
            return org.bukkit.ChatColor.GOLD + "[CraftCore] " + this.prefix + " ";
        }

        @Override
        public String toString() {
            return name();
        }
    }

}
