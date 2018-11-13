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
package com.djrapitops.plugin.logging.console;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.debug.DebugLogger;

/**
 * Interface for logging messages to console on the plugin's channel.
 *
 * @author Rsl1122
 * @see L for level related information.
 */
public interface PluginLogger {

    /**
     * Log a message to console.
     *
     * @param level   Level of the message.
     * @param message to log.
     */
    void log(L level, String... message);

    /**
     * Log a INFO level message.
     *
     * @param message to log.
     */
    default void info(String... message) {
        log(L.INFO, message);
    }

    /**
     * Log a WARN level message.
     *
     * @param message to log.
     */
    default void warn(String... message) {
        log(L.WARN, message);
    }

    /**
     * Log a message on default debug channel using a {@link DebugLogger}.
     *
     * @param message to log.
     */
    default void debug(String... message) {
        log(L.DEBUG, message);
    }

    /**
     * Log a ERROR level message.
     *
     * @param message to log.
     */
    default void error(String... message) {
        log(L.ERROR, message);
    }

    /**
     * Log a stacktrace of a Throwable on the console.
     *
     * @param level     Level of severity.
     * @param message   Message accompanying the stacktrace.
     * @param throwable Throwable of which stack trace to log on the console.
     */
    void log(L level, String message, Throwable throwable);

    /**
     * Get the {@link DebugLogger} used by this PluginLogger.
     *
     * @return in use DebugLogger.
     */
    DebugLogger getDebugLogger();
}
