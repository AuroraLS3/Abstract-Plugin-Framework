package com.djrapitops.plugin.logging.debug;

public class CombineDebugLogger implements DebugLogger {

    private final DebugLogger[] loggers;

    public CombineDebugLogger(DebugLogger... loggers) {
        this.loggers = loggers;
    }

    @Override
    public void log(String... message) {
        for (DebugLogger logger : loggers) {
            logger.log(message);
        }
    }
}
