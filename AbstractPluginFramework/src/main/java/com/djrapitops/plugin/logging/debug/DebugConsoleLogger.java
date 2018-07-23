package com.djrapitops.plugin.logging.debug;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.console.PluginLogger;

public class DebugConsoleLogger implements DebugLogger {

    private final PluginLogger logger;

    public DebugConsoleLogger(PluginLogger logger) {
        this.logger = logger;
    }

    @Override
    public void log(String... message) {
        logger.log(L.DEBUG_INFO, message);
    }
}
