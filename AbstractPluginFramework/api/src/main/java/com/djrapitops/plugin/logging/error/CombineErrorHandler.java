/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
