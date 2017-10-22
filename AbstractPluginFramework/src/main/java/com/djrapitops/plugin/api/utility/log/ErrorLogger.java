/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.utility.log;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.utilities.FormattingUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    static void logThrowable(Throwable e, File logsFolder) throws IOException {
        File errorFile = new File(logsFolder, Log.ERROR_FILE_NAME);
        if (!errorFile.exists()) {
            errorFile.createNewFile();
        }
        List<String> stackTrace = getStackTrace(e);
        stackTrace.add("Error was logged: " + FormattingUtils.formatTimeStampSecond(Benchmark.getTime()));
        FileLogger.logToFile(logsFolder, stackTrace);
    }
}