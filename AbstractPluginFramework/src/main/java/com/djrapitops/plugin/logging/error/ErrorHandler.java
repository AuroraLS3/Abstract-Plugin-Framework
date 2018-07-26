package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.L;

public interface ErrorHandler {
    void log(L level, Class caughtBy, Throwable throwable);
}
