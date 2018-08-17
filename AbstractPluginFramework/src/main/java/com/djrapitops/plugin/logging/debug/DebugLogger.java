package com.djrapitops.plugin.logging.debug;

public interface DebugLogger {

    default void logOn(String... message) {
        log("", message);
    }

    void logOn(String channel, String... message);

}
