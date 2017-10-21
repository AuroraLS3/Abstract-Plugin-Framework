/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.api;

import com.djrapitops.plugin.utilities.StackUtils;
import com.djrapitops.plugin.utilities.Verify;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rsl1122
 */
public class Log {

    private static final Map<Class, String> debugMode = new HashMap<>();

    public static void debug(String s) {
        debug(s, StackUtils.getCallingPlugin());
    }

    public static void debug(String s, Class c) {

    }

    private boolean logFile(Class c) {
        String debugMode = Log.debugMode.get(c);
        return Verify.equalsOne(debugMode.toLowerCase(), "true", "both", "file");
    }

    private boolean logConsole(Class c) {
        String debugMode = Log.debugMode.get(c);
        return Verify.equalsOne(debugMode.toLowerCase(), "true", "both", "console");
    }
}
