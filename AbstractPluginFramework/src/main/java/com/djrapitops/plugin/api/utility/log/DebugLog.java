/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.utility.log;

import com.djrapitops.plugin.api.TimeAmount;
import com.djrapitops.plugin.utilities.StackUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for logging larger "boxes" to logs after a task is complete.
 *
 * @author Rsl1122
 */
@Deprecated
public class DebugLog {

    private static final Map<Class, Map<String, DebugInfo>> debugInfo = new HashMap<>();

    /**
     * Used for logging larger debug complexes.
     *
     * @param task    complex this debug message is a part of.
     * @param message Single message to add to the debug log.
     * @return full debug complex so far.
     */
    public static DebugInfo logDebug(String task, String message) {
        return getDebug(task).addLine(message, TimeAmount.currentMs());
    }

    /**
     * Used for logging larger debug complexes.
     *
     * @param task     complex this debug message is a part of.
     * @param messages All messages to add to the debug log.
     * @return full debug complex so far.
     */
    public static DebugInfo logDebug(String task, String... messages) {
        DebugInfo debug = getDebug(task);
        long time = TimeAmount.currentMs();
        for (String message : messages) {
            debug.addLine(message, time);
        }
        return debug;
    }

    /**
     * Used for logging larger debug complexes.
     *
     * @param task complex to get
     * @return full debug complex so far.
     */
    public static DebugInfo getDebug(String task) {
        Class callingPlugin = StackUtils.getCallingPlugin();

        Map<String, DebugInfo> debugInfos = debugInfo.getOrDefault(callingPlugin, new HashMap<>());
        DebugInfo info = debugInfos.getOrDefault(task, new DebugInfo(callingPlugin, TimeAmount.currentMs(), task));

        debugInfos.put(task, info);
        debugInfo.put(callingPlugin, debugInfos);
        return info;
    }

    public static Map<String, DebugInfo> getAllDebugInfo() {
        return getAllDebugInfo(StackUtils.getCallingPlugin());
    }

    public static Map<String, DebugInfo> getAllDebugInfo(Class callingPlugin) {
        return debugInfo.getOrDefault(callingPlugin, new HashMap<>());
    }

    /**
     * Logs the full debug complex to the debug log.
     *
     * @param task complex to log.
     */
    public static void logDebug(String task) {
        getDebug(task).toLog();
    }

    /**
     * Logs the full debug complex to the debug log with an execution time.
     *
     * @param task complex to log.
     * @param time execution time.
     */
    public static void logDebug(String task, long time) {
        getDebug(task).toLog(time);
    }

    public static void clearDebug(String task) {
        Class callingPlugin = StackUtils.getCallingPlugin();

        Map<String, DebugInfo> debugInfos = debugInfo.getOrDefault(callingPlugin, new HashMap<>());
        debugInfos.remove(task);
    }

    public static void pluginDisabled(Class plugin) {
        debugInfo.remove(plugin);
    }
}