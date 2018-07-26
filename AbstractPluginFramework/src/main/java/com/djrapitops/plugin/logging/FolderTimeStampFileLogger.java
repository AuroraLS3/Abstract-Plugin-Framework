package com.djrapitops.plugin.logging;

import com.djrapitops.plugin.logging.error.ErrorHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FolderTimeStampFileLogger {

    private final String fileNamePrefix;
    private final File logFolder;

    private final Supplier<ErrorHandler> errorHandler;

    public FolderTimeStampFileLogger(String fileNamePrefix, File logFolder, Supplier<ErrorHandler> errorHandler) {
        this.fileNamePrefix = fileNamePrefix;
        this.logFolder = logFolder;
        this.errorHandler = errorHandler;
    }

    public void log(String... message) {
        File logFile = new File(logFolder, getFileName());
        try {
            FileLogger.appendToFile(logFile, Arrays.stream(message)
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

}
