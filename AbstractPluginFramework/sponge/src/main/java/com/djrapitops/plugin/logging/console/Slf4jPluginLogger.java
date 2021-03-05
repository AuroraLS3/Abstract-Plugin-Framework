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
import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * {@link PluginLogger} implementation for slf4j Logger platforms.
 *
 * @author AuroraLS3
 */
public class Slf4jPluginLogger implements PluginLogger {

    private final Supplier<DebugLogger> debugLogger;
    private final Supplier<Logger> loggerSupplier;

    /**
     * Create a new Slf4jPluginLogger.
     *
     * @param loggerSupplier slf4j logger for console logging.
     * @param debugLogger    Supplier for the {@link DebugLogger} to use.
     */
    public Slf4jPluginLogger(Logger loggerSupplier, Supplier<DebugLogger> debugLogger) {
        this(() -> loggerSupplier, debugLogger);
    }

    /**
     * Create a new Slf4jPluginLogger
     *
     * @param loggerSupplier slf4j logger for console logging.
     * @param debugLogger    Supplier for the {@link DebugLogger} to use.
     */
    public Slf4jPluginLogger(Supplier<Logger> loggerSupplier, Supplier<DebugLogger> debugLogger) {
        this.debugLogger = debugLogger;
        this.loggerSupplier = loggerSupplier;
    }

    @Override
    public void log(L level, String... message) {
        if (level == L.DEBUG) {
            debugLogger.get().log(message);
            return;
        } else if (level != L.DEBUG_INFO) {
            log(L.DEBUG, message);
        }
        Logger logger = loggerSupplier.get();
        switch (level) {
            case CRITICAL:
            case ERROR:
                for (String line : message) {
                    logger.error(line);
                }
                break;
            case WARN:
                for (String line : message) {
                    logger.warn(line);
                }
                break;
            case DEBUG_INFO:
                for (String line : message) {
                    logger.info("[DEBUG] {}", line);
                }
                break;
            case INFO_COLOR:
            case INFO:
            default:
                for (String line : message) {
                    logger.info(line);
                }
                break;
        }
    }

    @Override
    public void log(L level, String message, Throwable throwable) {
        Logger logger = loggerSupplier.get();
        switch (level) {
            case CRITICAL:
            case ERROR:
                logger.error(message, throwable);
                break;
            case WARN:
            default:
                logger.warn(message, throwable);
                break;
        }
    }

    @Override
    public DebugLogger getDebugLogger() {
        return debugLogger.get();
    }
}
