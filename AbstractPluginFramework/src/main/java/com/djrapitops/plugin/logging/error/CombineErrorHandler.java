package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.L;

public class CombineErrorHandler implements ErrorHandler {

    private final ErrorHandler[] handlers;

    public CombineErrorHandler(ErrorHandler... handlers) {
        this.handlers = handlers;
    }

    @Override
    public void log(L level, Class caughtBy, Throwable throwable) {
        for (ErrorHandler handler : handlers) {
            handler.log(level, caughtBy, throwable);
        }
    }
}
