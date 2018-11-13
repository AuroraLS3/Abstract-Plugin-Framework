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

import com.djrapitops.plugin.logging.FolderTimeStampFileLogger;
import com.djrapitops.plugin.logging.error.ErrorHandler;

import java.io.File;
import java.util.function.Supplier;

/**
 * {@link DebugLogger} implementation that logs the messages to a timestamped file.
 *
 * @author Rsl1122
 */
public class FolderTimeStampFileDebugLogger extends FolderTimeStampFileLogger implements DebugLogger {

    /**
     * Create a new FolderTimeStampFileDebugLogger.
     *
     * @param logFolder    Folder to store the log files in.
     * @param errorHandler Supplier for the {@link ErrorHandler} if writing to file fails.
     */
    public FolderTimeStampFileDebugLogger(File logFolder, Supplier<ErrorHandler> errorHandler) {
        super("DebugLog", logFolder, errorHandler);
    }

    @Override
    public void logOn(String channel, String... lines) {
        for (String line : lines) {
            log("[" + channel + "] " + line);
        }
    }
}
