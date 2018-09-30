package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.console.PluginLogger;

/**
 * {@link ErrorHandler} implementation that logs errors on the console using {@link PluginLogger}.
 *
 * @author Rsl1122
 */
public class ConsoleErrorLogger implements ErrorHandler {

    private final PluginLogger logger;

    /**
     * Create a new ConsoleErrorLogger.
     *
     * @param logger PluginLogger to use for logging the errors.
     */
    public ConsoleErrorLogger(PluginLogger logger) {
        this.logger = logger;
    }

    @Override
    public void log(L level, Class caughtBy, Throwable throwable) {
        logger.log(level, "Error was caught by " + caughtBy.getName(), throwable);
    }
}
