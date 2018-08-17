package com.djrapitops.plugin.logging.debug;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.console.PluginLogger;

public class DebugConsoleLogger implements DebugLogger {

    private final PluginLogger logger;

    public DebugConsoleLogger(PluginLogger logger) {
        this.logger = logger;
    }

    @Override
    public void logOn(String channel, String... message) {
        for (String line : message) {
            logger.log(L.DEBUG_INFO, "[" + channel + "] " + line);
        }
    }
}
