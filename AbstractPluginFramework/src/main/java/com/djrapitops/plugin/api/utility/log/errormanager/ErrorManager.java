/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.utility.log.errormanager;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public abstract class ErrorManager {

    public abstract void toLog(String source, Throwable e, Class callingPlugin);

}