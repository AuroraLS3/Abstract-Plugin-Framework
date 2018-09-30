package com.djrapitops.plugin.logging.debug;

import java.util.Optional;

/**
 * {@link DebugLogger} implementation that accepts multiple {@link DebugLogger} instances and calls every one.
 *
 * @author Rsl1122
 */
public class CombineDebugLogger implements DebugLogger {

    private DebugLogger[] loggers;

    /**
     * Create a new CombineDebugLogger.
     *
     * @param loggers debug loggers to call in other methods.
     */
    public CombineDebugLogger(DebugLogger... loggers) {
        this.loggers = loggers;
    }

    @Override
    public void logOn(String channel, String... lines) {
        for (DebugLogger logger : loggers) {
            logger.logOn(channel, lines);
        }
    }

    /**
     * Swap used debug loggers on the fly.
     *
     * @param loggers debug loggers to use.
     */
    public void setDebugLoggers(DebugLogger... loggers) {
        this.loggers = loggers;
    }

    /**
     * Retrieve a {@link DebugLogger} of a specific type if present in this CombineDebugLogger.
     *
     * @param ofType Class that defines what type of DebugLogger is sought.
     * @param <T>    Type of the DebugLogger.
     * @return Optional of the found DebugLogger. Empty if not present.
     */
    public <T extends DebugLogger> Optional<T> getDebugLogger(Class<T> ofType) {
        for (DebugLogger logger : loggers) {
            if (ofType.isAssignableFrom(logger.getClass())) {
                return Optional.of(ofType.cast(logger));
            }
        }
        return Optional.empty();
    }
}
