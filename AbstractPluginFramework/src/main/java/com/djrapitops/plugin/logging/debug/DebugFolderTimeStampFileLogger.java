package com.djrapitops.plugin.logging.debug;

import com.djrapitops.plugin.logging.FolderTimeStampFileLogger;
import com.djrapitops.plugin.logging.error.ErrorHandler;

import java.io.File;
import java.util.function.Supplier;

public class DebugFolderTimeStampFileLogger extends FolderTimeStampFileLogger implements DebugLogger {

    public DebugFolderTimeStampFileLogger(File logFolder, Supplier<ErrorHandler> errorHandler) {
        super("DebugLog", logFolder, errorHandler);
    }

    @Override
    public void logOn(String channel, String... message) {
        for (String line : message) {
            log("[" + channel + "] " + line);
        }
    }
}
