/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
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

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic {@link PluginLogger} implementation for platforms using {@link Logger} as logging solution.
 *
 * @author Rsl1222
 */
public class JavaUtilPluginLogger implements PluginLogger {

    protected final Consumer<String> console;
    protected final Supplier<DebugLogger> debugLogger;
    protected final Supplier<Logger> logger;

    /**
     * Create a new JavaUtilPluginLogger.
     *
     * @param console     Consumer of the logging method for colored messages on the console.
     * @param debugLogger {@link DebugLogger} to log all channels on.
     * @param logger      plugin logger for logging messages.
     */
    public JavaUtilPluginLogger(Consumer<String> console, Supplier<DebugLogger> debugLogger, Logger logger) {
        this(console, debugLogger, () -> logger);
    }

    /**
     * Create a new JavaUtilPluginLogger.
     *
     * @param console     Consumer of the logging method for colored messages on the console.
     * @param debugLogger {@link DebugLogger} to log all channels on.
     * @param logger      Supplier for plugin logger for logging messages.
     */
    public JavaUtilPluginLogger(Consumer<String> console, Supplier<DebugLogger> debugLogger, Supplier<Logger> logger) {
        this.console = console;
        this.debugLogger = debugLogger;
        this.logger = logger;
    }

    @Override
    public void log(L level, String... message) {
        if (level == L.DEBUG) {
            debugLogger.get().log(message);
            return;
        } else if (level != L.DEBUG_INFO) {
            log(L.DEBUG, message);
        }
        switch (level) {
            case CRITICAL:
            case ERROR:
                for (String line : message) {
                    logger.get().log(Level.SEVERE, line);
                }
                break;
            case WARN:
                for (String line : message) {
                    logger.get().log(Level.WARNING, line);
                }
                break;
            case INFO_COLOR:
                for (String line : message) {
                    console.accept(line);
                }
                break;
            case DEBUG_INFO:
                for (String line : message) {
                    logger.get().log(Level.INFO, "[DEBUG] {0}", line);
                }
                break;
            case INFO:
            default:
                for (String line : message) {
                    logger.get().log(Level.INFO, line);
                }
                break;
        }
    }

    @Override
    public void log(L level, String message, Throwable throwable) {
        switch (level) {
            case CRITICAL:
            case ERROR:
                logger.get().log(Level.SEVERE, message, throwable);
                break;
            case WARN:
            default:
                logger.get().log(Level.WARNING, message, throwable);
                break;
        }
    }

    @Override
    public DebugLogger getDebugLogger() {
        return debugLogger.get();
    }
}
