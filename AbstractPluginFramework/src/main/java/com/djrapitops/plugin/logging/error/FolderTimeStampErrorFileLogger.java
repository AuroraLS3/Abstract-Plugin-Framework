package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.logging.FolderTimeStampFileLogger;
import com.djrapitops.plugin.logging.L;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * {@link ErrorHandler} implementation that logs the errors to a per day file.
 *
 * @author Rsl1122
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

    /**
     * Parse a list of lines from a file logged by this logger into a TreeMap of different errors.
     *
     * @param lines Lines read from a file logged by this logger.
     * @return TreeMap with first line - rest of the lines structure.
     */
    public static TreeMap<String, List<String>> splitByError(List<String> lines) {
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
}
