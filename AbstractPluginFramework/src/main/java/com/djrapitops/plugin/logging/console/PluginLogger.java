package com.djrapitops.plugin.logging.console;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.debug.DebugLogger;

public interface PluginLogger {

    void log(L level, String... message);

    default void info(String... message) {
        log(L.INFO, message);
    }

    default void warn(String... message) {
        log(L.WARN, message);
    }

    default void debug(String... message) {
        log(L.DEBUG, message);
    }

    default void error(String... message) {
        log(L.ERROR, message);
    }

    void log(L level, String message, Throwable throwable);

    DebugLogger getDebugLogger();
}
