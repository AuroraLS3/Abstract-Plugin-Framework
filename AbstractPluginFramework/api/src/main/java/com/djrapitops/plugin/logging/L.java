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
package com.djrapitops.plugin.logging;

import com.djrapitops.plugin.logging.debug.ConsoleDebugLogger;

/**
 * L - Level, determines the logging type of
 * {@link com.djrapitops.plugin.logging.console.PluginLogger} and {@link com.djrapitops.plugin.logging.error.ErrorHandler}.
 * <p>
 * INFO: INFO level logging, any and all messages.
 * INFO_COLOR: INFO level logging with colored messages.
 * DEBUG: messages going into the {@link com.djrapitops.plugin.logging.debug.DebugLogger} default channel.
 * DEBUG_INFO: INFO level message logging of DEBUG channel messages if {@link ConsoleDebugLogger} is used.
 * WARN: WARN level messages, warnings to the user.
 * ERROR: ERROR level messages, warnings to the user.
 * CRITICAL: ERROR level messages, implementations should disable the plugin if logging an exception.
 *
 * @author Rsl1122
 */
public enum L {

    INFO,
    INFO_COLOR,
    DEBUG,
    DEBUG_INFO,
    WARN,
    ERROR,
    CRITICAL

}
