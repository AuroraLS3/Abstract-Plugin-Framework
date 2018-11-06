package com.djrapitops.plugin.logging.debug;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.console.PluginLogger;

/**
 * {@link DebugLogger} implementation that logs the debug messages with a {@link PluginLogger} on {@code L.DEBUG_INFO} channel.
 *
 * @author Rsl1122
 */
public class ConsoleDebugLogger implements DebugLogger {

    private final PluginLogger logger;

    /**
     * Create a new ConsoleDebugLogger.
     *
     * @param logger PluginLogger to use for logging.
     */
    public ConsoleDebugLogger(PluginLogger logger) {
        this.logger = logger;
    }

    @Override
    public void logOn(String channel, String... lines) {
        for (String line : lines) {
            logger.log(L.DEBUG_INFO, "[" + channel + "] " + line);
        }
    }
}
