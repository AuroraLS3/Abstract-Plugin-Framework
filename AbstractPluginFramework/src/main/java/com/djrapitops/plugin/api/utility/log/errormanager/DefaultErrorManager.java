/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.utility.log.errormanager;

import com.djrapitops.plugin.api.utility.log.ErrorLogger;
import com.djrapitops.plugin.api.utility.log.Log;

import java.io.File;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class DefaultErrorManager extends ErrorManager {

    @Override
    public void toLog(String source, Throwable e, Class callingPlugin) {
        try {
            File logsFolder = Log.getLogsFolder(callingPlugin);
            Log.warn(source + " Caught: " + e, callingPlugin);
            ErrorLogger.logThrowable(e, logsFolder);
        } catch (Exception exception) {
            System.out.println("Failed to log error to file because of " + exception);
            System.out.println("Error:");
            e.printStackTrace();
            System.out.println("Fail Reason:");
            exception.printStackTrace();
        }
    }
}