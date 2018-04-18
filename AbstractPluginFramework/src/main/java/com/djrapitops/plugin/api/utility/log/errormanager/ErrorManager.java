/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.utility.log.errormanager;

/**
 * Interface for acting on different errors.
 * <p>
 * Used by Log class for logging all exceptions.
 *
 * @author Rsl1122
 * @see com.djrapitops.plugin.api.utility.log.Log
 */
public interface ErrorManager {

    void toLog(String source, Throwable e, Class callingPlugin);

}