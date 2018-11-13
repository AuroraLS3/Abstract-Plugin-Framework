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
import com.djrapitops.plugin.logging.debug.ConsoleDebugLogger;
import com.djrapitops.plugin.logging.debug.DebugLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link PluginLogger} implementation for testing purposes.
 *
 * @author Rsl1122
 */
public class TestPluginLogger implements PluginLogger {

    @Override
    public void log(L level, String... message) {
        for (String line : message) {
            Logger.getAnonymousLogger().log(Level.INFO, () -> level.name() + ": " + line);
        }
    }

    @Override
    public void log(L level, String message, Throwable throwable) {
        Logger.getAnonymousLogger().log(Level.SEVERE, message, throwable);
    }

    @Override
    public DebugLogger getDebugLogger() {
        return new ConsoleDebugLogger(this);
    }
}
