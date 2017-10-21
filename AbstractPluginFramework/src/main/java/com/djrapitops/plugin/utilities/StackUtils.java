package com.djrapitops.plugin.utilities;

import com.djrapitops.plugin.Plugin;
import com.djrapitops.plugin.StaticHolder;

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
        for (int i = 0; i < stack.length; i++) {
            Class c = stack[i].getClass();
            Class provider = StaticHolder.getProvidingPlugin(c);
            if (provider != null) {
                return provider;
            }
        }
        return Plugin.class;
    }

}
