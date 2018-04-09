/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.utility.log.errormanager;

import com.djrapitops.plugin.api.utility.log.ErrorLogger;
import com.djrapitops.plugin.api.utility.log.Log;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.err.println("Failed to log error to file because of " + exception);
            Logger.getGlobal().log(Level.SEVERE, "Error:", e);
            System.err.println("Fail Reason:");
            exception.printStackTrace();
        }
    }
}