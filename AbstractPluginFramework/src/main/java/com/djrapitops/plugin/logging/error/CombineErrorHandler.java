package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.L;

import java.util.Optional;

public class CombineErrorHandler implements ErrorHandler {

    private ErrorHandler[] handlers;

    public CombineErrorHandler(ErrorHandler... handlers) {
        this.handlers = handlers;
    }

    @Override
    public void log(L level, Class caughtBy, Throwable throwable) {
        for (ErrorHandler handler : handlers) {
            handler.log(level, caughtBy, throwable);
        }
    }

    public void setErrorHandlers(ErrorHandler... handlers) {
        this.handlers = handlers;
    }

    public <T extends ErrorHandler> Optional<T> getErrorHandler(Class<T> ofType) {
        for (ErrorHandler handler : handlers) {
            if (ofType.isAssignableFrom(handler.getClass())) {
                return Optional.of(ofType.cast(handler));
            }
        }
        return Optional.empty();
    }
}
