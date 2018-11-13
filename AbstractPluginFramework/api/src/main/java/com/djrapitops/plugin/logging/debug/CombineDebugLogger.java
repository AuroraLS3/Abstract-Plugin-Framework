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
