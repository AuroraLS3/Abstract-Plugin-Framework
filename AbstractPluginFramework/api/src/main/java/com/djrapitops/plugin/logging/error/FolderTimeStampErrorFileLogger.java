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
package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.FolderTimeStampFileLogger;
import com.djrapitops.plugin.logging.L;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;

/**
 * {@link ErrorHandler} implementation that logs the errors to a per day file.
 *
 * @author AuroraLS3
 */
public class FolderTimeStampErrorFileLogger extends FolderTimeStampFileLogger implements ErrorHandler {

    /**
     * Create a new FolderTimeStampErrorFileLogger.
     *
     * @param logFolder    Folder to store the logs in.
     * @param errorHandler ErrorHandler to use in case logging the file goes wrong.
     */
    public FolderTimeStampErrorFileLogger(File logFolder, ErrorHandler errorHandler) {
        super("Errors", logFolder, () -> errorHandler);
    }

    /**
     * Create a new FolderTimeStampErrorFileLogger.
     *
     * @param logFolder    Supplier for the Folder to store the logs in.
     * @param errorHandler ErrorHandler to use in case logging the file goes wrong.
     */
    public FolderTimeStampErrorFileLogger(Supplier<File> logFolder, ErrorHandler errorHandler) {
        super("Errors", logFolder, () -> errorHandler);
    }

    /**
     * Parse a list of lines from a file logged by this logger into a TreeMap of different errors.
     *
     * @param lines Lines read from a file logged by this logger.
     * @return TreeMap with first line - rest of the lines structure.
     */
    public static SortedMap<String, List<String>> splitByError(List<String> lines) {
        if (lines.isEmpty()) {
            return new TreeMap<>();
        }
        String splittingLine = " caught ";

        TreeMap<String, List<String>> errors = new TreeMap<>();
        List<String> errorLines = null;
        String currentError = null;
        for (String line : lines) {
            if (line.contains(splittingLine)) {
                if (errorLines != null) {
                    errors.put(currentError, errorLines);
                }
                currentError = line;
                errorLines = new ArrayList<>();
            } else {
                if (errorLines != null) {
                    errorLines.add(line);
                }
            }
        }
        return errors;
    }

    @Override
    public void log(L level, Class caughtBy, Throwable throwable) {
        log(caughtBy.getName() + " caught " + throwable.getClass().getSimpleName());

        List<String> stackTrace = getStackTrace(throwable);
        for (String line : stackTrace) {
            log(line);
        }
    }

    private List<String> getStackTrace(Throwable e) {
        List<String> trace = new ArrayList<>();
        trace.add(e.toString());
        for (StackTraceElement element : e.getStackTrace()) {
            trace.add("   " + element);
        }
        Throwable cause = e.getCause();
        if (cause != null) {
            trace.add("Caused by:");
            trace.addAll(getStackTrace(cause));
        }
        return trace;
    }
}
