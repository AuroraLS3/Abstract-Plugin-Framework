package com.djrapitops.plugin.logging.debug;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.console.PluginLogger;

/**
 * {@link DebugLogger} implementation that logs the debug messages with a {@link PluginLogger} on {@code L.DEBUG_INFO} channel.
 *
 * @author Rsl1122
 */
public class DebugConsoleLogger implements DebugLogger {

    private final PluginLogger logger;

    /**
     * Create a new DebugConsoleLogger.
     *
     * @param logger PluginLogger to use for logging.
     */
    public DebugConsoleLogger(PluginLogger logger) {
        this.logger = logger;
    }

    @Override
    public void logOn(String channel, String... lines) {
        for (String line : lines) {
            logger.log(L.DEBUG_INFO, "[" + channel + "] " + line);
        }
    }
}
