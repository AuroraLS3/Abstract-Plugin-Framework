package com.djrapitops.plugin.logging.debug;

public interface DebugLogger {

    default void log(String... message) {
        logOn("", message);
    }

    void logOn(String channel, String... message);

}
