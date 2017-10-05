package com.djrapitops.plugin.utilities;

import com.djrapitops.plugin.Plugin;

/**
 * Class for getting information about the current StackTrace.
 *
 * @author Rsl1122
 */
public class StackUtils {

    /**
     * Get the calling Plugin class from the StackTrace.
     *
     * @return Class that extends Plugin or Plugin.class if not found in the
     * StackTrace.
     */
    public static Class getCallingPlugin() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            Class c = stack[i].getClass();
            if (Plugin.class.isAssignableFrom(c)) {
                return c;
            }
        }
        // TODO check commands, tasks and listeners
        return Plugin.class;
    }

}
