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
package com.djrapitops.plugin.logging;

import com.djrapitops.plugin.logging.error.ErrorHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Logging utility class that is used to log messages into files with timestamps.
 * <p>
 * This class is used by other logger classes and creating instances is not required.
 *
 * @author AuroraLS3
 */
public class FolderTimeStampFileLogger {

    private final String fileNamePrefix;
    private final Supplier<File> logFolder;

    private final Supplier<ErrorHandler> errorHandler;

    /**
     * Create a new FolderTimeStampFileLogger.
     *
     * @param fileNamePrefix Prefix of the file, usage: fileNamePrefix-day_timestamp.txt
     * @param logFolder      Folder the log files should be created in.
     * @param errorHandler   Supplier for a {@link ErrorHandler} in case logging the information fails.
     */
    public FolderTimeStampFileLogger(String fileNamePrefix, File logFolder, Supplier<ErrorHandler> errorHandler) {
        this(fileNamePrefix, () -> logFolder, errorHandler);
    }

    /**
     * Create a new folderTimeStampFileLogger.
     *
     * @param fileNamePrefix Prefix of the file, usage: fileNamePrefix-day_timestamp.txt
     * @param logFolder      Supplier for the Folder the log files should be created in.
     * @param errorHandler   Supplier for a {@link ErrorHandler} in case logging the information fails.
     */
    public FolderTimeStampFileLogger(String fileNamePrefix, Supplier<File> logFolder, Supplier<ErrorHandler> errorHandler) {
        this.fileNamePrefix = fileNamePrefix;
        this.logFolder = logFolder;
        this.errorHandler = errorHandler;
    }

    public void log(String... lines) {
        File logFile = new File(logFolder.get(), getFileName());
        try {
            FileLogger.appendToFile(logFile, Arrays.stream(lines)
                    .map(line -> "| " + getTimeStamp() + " | " + line)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            errorHandler.get().log(L.CRITICAL, this.getClass(), e);
        }
    }

    public String getTimeStamp() {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }

    private String getFileName() {
        String day = new SimpleDateFormat("yyyy_MM_dd").format(System.currentTimeMillis());
        return fileNamePrefix + "-" + day + ".txt";
    }

    /**
     * Get the currently used text file if it exists.
     *
     * @return Optional of file or empty optional if the file does not exist.
     */
    public Optional<File> getCurrentFile() {
        File file = new File(logFolder.get(), getFileName());
        if (file.exists()) {
            return Optional.of(file);
        }
        return Optional.empty();
    }

}
