package com.djrapitops.plugin.logging.console;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.debug.DebugLogger;

/**
 * Interface for logging messages to console on the plugin's channel.
 *
 * @author Rsl1122
 * @see L for level related information.
 */
public interface PluginLogger {

    /**
     * Log a message to console.
     *
     * @param level   Level of the message.
     * @param message to log.
     */
    void log(L level, String... message);

    /**
     * Log a INFO level message.
     *
     * @param message to log.
     */
    default void info(String... message) {
        log(L.INFO, message);
    }

    /**
     * Log a WARN level message.
     *
     * @param message to log.
     */
    default void warn(String... message) {
        log(L.WARN, message);
    }

    /**
     * Log a message on default debug channel using a {@link DebugLogger}.
     *
     * @param message to log.
     */
    default void debug(String... message) {
        log(L.DEBUG, message);
    }

    /**
     * Log a ERROR level message.
     *
     * @param message to log.
     */
    default void error(String... message) {
        log(L.ERROR, message);
    }

    /**
     * Log a stacktrace of a Throwable on the console.
     *
     * @param level     Level of severity.
     * @param message   Message accompanying the stacktrace.
     * @param throwable Throwable of which stack trace to log on the console.
     */
    void log(L level, String message, Throwable throwable);

    /**
     * Get the {@link DebugLogger} used by this PluginLogger.
     *
     * @return in use DebugLogger.
     */
    DebugLogger getDebugLogger();
}
