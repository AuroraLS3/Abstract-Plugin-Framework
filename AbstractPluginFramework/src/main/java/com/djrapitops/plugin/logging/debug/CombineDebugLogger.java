package com.djrapitops.plugin.logging.debug;

import java.util.Optional;

public class CombineDebugLogger implements DebugLogger {

    private final DebugLogger[] loggers;

    public CombineDebugLogger(DebugLogger... loggers) {
        this.loggers = loggers;
    }

    @Override
    public void logOn(String channel, String... message) {
        for (DebugLogger logger : loggers) {
            logger.logOn(channel, message);
        }
    }

    public <T extends DebugLogger> Optional<T> getDebugLogger(Class<T> ofType) {
        for (DebugLogger logger : loggers) {
            if (ofType.isAssignableFrom(logger.getClass())) {
                return Optional.of(ofType.cast(logger));
            }
        }
        return Optional.empty();
    }
}
