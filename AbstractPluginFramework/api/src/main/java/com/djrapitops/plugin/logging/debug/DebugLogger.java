package com.djrapitops.plugin.logging.debug;

/**
 * Interface for logging messages to a debug log.
 *
 * @author Rsl1122
 */
public interface DebugLogger {

    /**
     * Log messages on the default channel.
     *
     * @param lines to log.
     */
    default void log(String... lines) {
        logOn("", lines);
    }

    /**
     * Log messages on a specific debug channel.
     *
     * @param channel Channel to log the debug lines on.
     * @param lines   to log.
     */
    void logOn(String channel, String... lines);

}
