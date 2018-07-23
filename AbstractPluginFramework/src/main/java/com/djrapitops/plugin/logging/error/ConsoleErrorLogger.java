package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.console.PluginLogger;

public class ConsoleErrorLogger implements ErrorHandler {

    private final PluginLogger logger;

    public ConsoleErrorLogger(PluginLogger logger) {
        this.logger = logger;
    }

    @Override
    public void logError(L level, Class caughtBy, Throwable throwable) {
        logger.log(level, "Error was caught by " + caughtBy.getName(), throwable);
    }
}
