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
public class DebugFolderTimeStampFileLogger extends FolderTimeStampFileLogger implements DebugLogger {

    /**
     * Create a new DebugFolderTimeStampFileLogger.
     *
     * @param logFolder    Folder to store the log files in.
     * @param errorHandler Supplier for the {@link ErrorHandler} if writing to file fails.
     */
    public DebugFolderTimeStampFileLogger(File logFolder, Supplier<ErrorHandler> errorHandler) {
        super("DebugLog", logFolder, errorHandler);
    }

    @Override
    public void logOn(String channel, String... lines) {
        for (String line : lines) {
            log("[" + channel + "] " + line);
        }
    }
}
