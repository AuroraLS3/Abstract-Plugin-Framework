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
 * ErrorManager that all APF plugins use by default inf one is not specified.
 *
 * @author Rsl1122
 * @see ErrorManager
 */
@Deprecated
public class DefaultErrorManager implements ErrorManager {

    @Override
    public void toLog(String source, Throwable e, Class callingPlugin) {
        try {
            File logsFolder = Log.getLogsFolder(callingPlugin);
            Log.warn(source + " Caught: " + e, callingPlugin);
            ErrorLogger.logThrowable(e, logsFolder);
        } catch (Exception exception) {
            Logger.getGlobal().log(Level.SEVERE, "Failed to log error because of exception", exception);
            Logger.getGlobal().log(Level.SEVERE, "Unlogged Exception:", e);
        }
    }
}