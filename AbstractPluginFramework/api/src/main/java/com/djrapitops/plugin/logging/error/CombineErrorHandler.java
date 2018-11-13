package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.L;

import java.util.Optional;

/**
 * {@link ErrorHandler} implementation that accepts multiple {@link ErrorHandler} instances and calls every one.
 *
 * @author Rsl1122
 */
public class CombineErrorHandler implements ErrorHandler {

    private ErrorHandler[] handlers;

    /**
     * Create a new CombineErrorHandler.
     *
     * @param handlers ErrorHandlers to call in other methods.
     */
    public CombineErrorHandler(ErrorHandler... handlers) {
        this.handlers = handlers;
    }

    @Override
    public void log(L level, Class caughtBy, Throwable throwable) {
        for (ErrorHandler handler : handlers) {
            handler.log(level, caughtBy, throwable);
        }
    }

    /**
     * Change {@link ErrorHandler}s on the fly.
     *
     * @param handlers New ErrorHandlers to use.
     */
    public void setErrorHandlers(ErrorHandler... handlers) {
        this.handlers = handlers;
    }

    /**
     * Retrieve an {@link ErrorHandler} of a specific type if present in this CombineErrorHandler.
     *
     * @param ofType Class that defines what type of ErrorHandler is sought.
     * @param <T>    Type of the ErrorHandler.
     * @return Optional of the found ErrorHandler. Empty if not present.
     */
    public <T extends ErrorHandler> Optional<T> getErrorHandler(Class<T> ofType) {
        for (ErrorHandler handler : handlers) {
            if (ofType.isAssignableFrom(handler.getClass())) {
                return Optional.of(ofType.cast(handler));
            }
        }
        return Optional.empty();
    }
}
