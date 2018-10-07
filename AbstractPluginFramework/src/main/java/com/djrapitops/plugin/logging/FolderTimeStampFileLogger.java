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
 * @author Rsl1122
 */
public class FolderTimeStampFileLogger {

    private final String fileNamePrefix;
    private final File logFolder;

    private final Supplier<ErrorHandler> errorHandler;

    /**
     * Create a new FolderTimeStampFileLogger.
     *
     * @param fileNamePrefix Prefix of the file, usage: fileNamePrefix-day_timestamp.txt
     * @param logFolder      Folder the log files should be created in.
     * @param errorHandler   Supplier for a {@link ErrorHandler} in case logging the information fails.
     */
    public FolderTimeStampFileLogger(String fileNamePrefix, File logFolder, Supplier<ErrorHandler> errorHandler) {
        this.fileNamePrefix = fileNamePrefix;
        this.logFolder = logFolder;
        this.errorHandler = errorHandler;
    }

    public void log(String... lines) {
        File logFile = new File(logFolder, getFileName());
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
        File file = new File(logFolder, getFileName());
        if (file.exists()) {
            return Optional.of(file);
        }
        return Optional.empty();
    }

}
