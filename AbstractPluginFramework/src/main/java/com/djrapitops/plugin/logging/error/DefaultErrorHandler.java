package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.console.PluginLogger;

public class DefaultErrorHandler implements ErrorHandler {

    private final PluginLogger logger;
    private final ErrorFileLogger errorFileLogger;

    public DefaultErrorHandler(PluginLogger logger, ErrorFileLogger errorFileLogger) {
        this.logger = logger;
        this.errorFileLogger = errorFileLogger;
    }

    @Override
    public void logError(L level, Class caughtBy, Throwable throwable) {
        logger.log(level, "Error was caught by " + caughtBy.getName(), throwable);
        errorFileLogger.logError(level, caughtBy, throwable);
    }
}
