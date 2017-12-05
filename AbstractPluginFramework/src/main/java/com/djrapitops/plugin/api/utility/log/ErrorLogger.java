/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.utility.log;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.utilities.FormatUtils;
import com.djrapitops.plugin.utilities.Verify;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class ErrorLogger {

    private static List<String> getStackTrace(Throwable e) {
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

    public static void logThrowable(Throwable e, File logsFolder) throws IOException {
        File errorFile = new File(logsFolder, Log.ERROR_FILE_NAME);
        if (!errorFile.exists()) {
            errorFile.createNewFile();
        }
        List<String> stackTrace = getStackTrace(e);
        stackTrace.add("Error was logged: " + FormatUtils.formatTimeStampSecond(Benchmark.getTime()));
        FileLogger.appendToFile(errorFile, stackTrace);
    }

    /**
     * Used to get all lines for different errors in a TreeMap.
     *
     * @param plugin Plugin that uses APF.
     * @return TreeMap: "Error was logged: timestamp", List of error stacktrace lines.
     * @throws IOException If file can not be read.
     */
    public static TreeMap<String, List<String>> getLoggedErrors(IPlugin plugin) throws IOException {
        File logsFolder = new File(plugin.getDataFolder(), "logs");
        File errorFile = new File(logsFolder, Log.ERROR_FILE_NAME);
        if (!logsFolder.exists() || !errorFile.exists()) {
            return new TreeMap<>();
        }

        try (Stream<String> s = Files.lines(errorFile.toPath(), Charset.forName("UTF-8"))) {
            List<String> lines = s.collect(Collectors.toList());
            TreeMap<String, List<String>> errors = new TreeMap<>();
            List<String> errorLines = new ArrayList<>();
            for (String line : lines) {
                if (line.startsWith("Error was logged: ")) {
                    errors.put(line, errorLines);
                    errorLines = new ArrayList<>();
                } else {
                    errorLines.add(line);
                }
            }
            return errors;
        }
    }
}