/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.api.utility.log;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.api.TimeAmount;
import com.djrapitops.plugin.api.utility.log.errormanager.DefaultErrorManager;
import com.djrapitops.plugin.api.utility.log.errormanager.ErrorManager;
import com.djrapitops.plugin.utilities.FormatUtils;
import com.djrapitops.plugin.utilities.StackUtils;
import com.djrapitops.plugin.utilities.Verify;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rsl1122
 */
public class Log extends DebugLog {

    public static final String DEBUG_FILE_NAME = "DebugLog-.txt";
    public static final String ERROR_FILE_NAME = "ErrorLog.txt";

    private static final Map<Class, String> debugMode = new HashMap<>();

    private static final Map<Class, List<String>> debugLogs = new HashMap<>();

    private static final Map<Class, ErrorManager> errorManagers = new HashMap<>();
    private static DefaultErrorManager defaultErrorManager = new DefaultErrorManager();

    public static void info(String s) {
        info(s, StackUtils.getCallingPlugin());
    }

    private static void info(String s, Class c) {
        IPlugin instance = StaticHolder.getInstance(c);
        if (instance == null) {
            return;
        }
        instance.log("INFO", s);
        if (!s.startsWith("[DEBUG]")) {
            debug(Collections.singletonList(s), c);
        }
    }

    public static void infoColor(String s) {
        infoColor(s, StackUtils.getCallingPlugin());
    }

    private static void infoColor(String s, Class c) {
        IPlugin instance = StaticHolder.getInstance(c);
        if (instance == null) {
            return;
        }
        instance.log("INFO_COLOR", s);
    }

    public static void warn(String s) {
        warn(s, StackUtils.getCallingPlugin());
    }

    public static void warn(String s, Class c) {
        IPlugin instance = StaticHolder.getInstance(c);
        if (instance == null) {
            return;
        }
        instance.log("WARN", s);
    }

    public static void error(String s) {
        error(s, StackUtils.getCallingPlugin());
    }

    private static void error(String s, Class c) {
        IPlugin instance = StaticHolder.getInstance(c);
        if (instance == null) {
            return;
        }
        instance.log("ERROR", s);
    }

    public static void debug(Class callingPlugin, String... lines) {
        debug(Arrays.asList(lines), callingPlugin);
    }

    public static void debug(String... lines) {
        debug(Arrays.asList(lines));
    }

    public static void debug(List<String> lines) {
        debug(lines, StackUtils.getCallingPlugin());
    }

    static void debug(List<String> lines, Class callingPlugin) {
        List<String> log = debugLogs.getOrDefault(callingPlugin, new ArrayList<>());
        boolean debugToConsole = debugToConsole(callingPlugin);

        for (String line : lines) {
            if (log.size() >= 750) {
                log.remove(0);
            }
            log.add(line);
            if (debugToConsole) {
                if (line.startsWith("|")) {
                    info("[DEBUG] " + line.substring(19));
                } else {
                    info("[DEBUG] " + line);
                }
            }
        }

        if (callingPlugin != null) {
            debugLogs.put(callingPlugin, log);
        }

        if (debugToFile(callingPlugin)) {
            toDebugLog(lines, callingPlugin);
        }
    }

    public static void toLog(Class clazz, Throwable e) {
        toLog(clazz.getName(), e);
    }

    public static void toLog(String source, Throwable e) {
        Class callingPlugin = StackUtils.getCallingPlugin();
        toLog(source, e, callingPlugin);
    }

    private static void toLog(String source, Throwable e, Class callingPlugin) {
        getErrorManager(callingPlugin).toLog(source, e, callingPlugin);
    }

    private static ErrorManager getErrorManager(Class callingPlugin) {
        return errorManagers.getOrDefault(callingPlugin, defaultErrorManager);
    }

    public static void setErrorManager(ErrorManager errorManager) {
        errorManagers.put(StackUtils.getCallingPlugin(), errorManager);
    }

    public static File getLogsFolder() {
        return getLogsFolder(StackUtils.getCallingPlugin());
    }

    public static File getLogsFolder(Class callingPlugin) {
        IPlugin instance = StaticHolder.getInstance(callingPlugin);
        if (instance == null) {
            File apf_plugin_errorlogs = new File("APF_plugin_errorlogs");
            apf_plugin_errorlogs.mkdirs();
            return apf_plugin_errorlogs;
        }
        File dataFolder = instance.getDataFolder();
        File logsFolder = new File(dataFolder, "logs");
        logsFolder.mkdirs();
        return logsFolder;
    }


    private static void toDebugLog(List<String> lines, Class callingPlugin) {
        File logsFolder = getLogsFolder(callingPlugin);

        String[] split = DEBUG_FILE_NAME.split("-");
        String day = FormatUtils.formatTimeStampYear(TimeAmount.currentMs()).split(",")[0].replace(" ", "-");
        String debugLogFileName = split[0] + "-" + day + ".txt";

        String timeStamp = FormatUtils.formatTimeStampSecond(TimeAmount.currentMs());
        List<String> timeStamped = lines.stream().map(line -> "| " + timeStamp + " | " + line)
                .collect(Collectors.toList());

        try {
            FileLogger.appendToFile(new File(logsFolder, debugLogFileName), timeStamped);
        } catch (IOException e) {
            Log.toLog("com.djrapitops.plugin.api.utility.log.Log", e);
        }
    }


    private static boolean debugToFile(Class c) {
        String debugMode = Log.debugMode.get(c);
        if (debugMode == null) {
            return false;
        }
        return Verify.equalsOne(debugMode.toLowerCase(), "true", "both", "file");
    }

    private static boolean debugToConsole(Class c) {
        String debugMode = Log.debugMode.get(c);
        if (debugMode == null) {
            return false;
        }
        return Verify.equalsOne(debugMode.toLowerCase(), "true", "both", "console");
    }

    public static void setDebugMode(String mode) {
        Class callingPlugin = StackUtils.getCallingPlugin();
        debugMode.put(callingPlugin, mode);
    }

    public static List<String> getDebugLogInMemory() {
        return getDebugLogInMemory(StackUtils.getCallingPlugin());
    }

    private static List<String> getDebugLogInMemory(Class callingPlugin) {
        return debugLogs.getOrDefault(callingPlugin, new ArrayList<>());
    }
}
