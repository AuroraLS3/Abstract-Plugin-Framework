/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.api.utility.log;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.utilities.FormatUtils;
import com.djrapitops.plugin.utilities.StackUtils;
import com.djrapitops.plugin.utilities.Verify;
import com.google.common.base.Strings;

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

    public static void info(String s) {
        info(s, StackUtils.getCallingPlugin());
    }

    private static void info(String s, Class c) {
        IPlugin instance = StaticHolder.getInstance(c);
        if (instance == null) {
            return;
        }
        instance.log("INFO", s);
        if (debugToFile(c)) {
            toDebugLog(Collections.singletonList(s), c);
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

    private static void warn(String s, Class c) {
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
        if (debugToConsole(callingPlugin)) {
            for (String line : lines) {
                if (line.startsWith("|")) {
                    info("[DEBUG] "+line.substring(19));
                } else {
                    info("[DEBUG] " + line);
                }
            }
        }
        if (debugToFile(callingPlugin)) {
            toDebugLog(lines, callingPlugin);
        }
    }

    public static void toLog(String source, Throwable e) {
        Class callingPlugin = StackUtils.getCallingPlugin();
        toLog(source, e, callingPlugin);
    }

    private static void toLog(String source, Throwable e, Class callingPlugin) {
        File logsFolder = getLogsFolder(callingPlugin);

        try {
            warn(source + " Caught: " + e, callingPlugin);
            ErrorLogger.logThrowable(e, logsFolder);
        } catch (IOException ioException) {
            System.out.println("Failed to log error to file because of " + ioException);
            System.out.println("Error:");
            e.printStackTrace();
            System.out.println("Fail Reason:");
            ioException.printStackTrace();
        }
    }

    public static File getLogsFolder() {
        return getLogsFolder(StackUtils.getCallingPlugin());
    }

    private static File getLogsFolder(Class callingPlugin) {
        IPlugin instance = StaticHolder.getInstance(callingPlugin);
        if (instance == null) {
            return new File("APF_plugin_errorlogs");
        }
        File dataFolder = instance.getDataFolder();
        File logsFolder = new File(dataFolder, "logs");
        logsFolder.mkdirs();
        return logsFolder;
    }


    private static void toDebugLog(List<String> lines, Class callingPlugin) {
        File logsFolder = getLogsFolder(callingPlugin);

        String[] split = DEBUG_FILE_NAME.split("-");
        String day = FormatUtils.formatTimeStampYear(Benchmark.getTime()).split(",")[0].replace(" ", "-");
        String debugLogFileName = split[0] + "-" + day + ".txt";

        String timeStamp = FormatUtils.formatTimeStampSecond(Benchmark.getTime());
        List<String> timeStamped = lines.stream().map(l -> "| " + timeStamp + " | " + l)
                .collect(Collectors.toList());

        try {
            FileLogger.appendToFile(new File(logsFolder, debugLogFileName), timeStamped);
        } catch (IOException e) {
            Log.toLog("com.djrapitops.plugin.api.utility.log.Log", e);
        }
    }


    private static boolean debugToFile(Class c) {
        String debugMode = Log.debugMode.get(c);
        return Verify.equalsOne(debugMode.toLowerCase(), "true", "both", "file");
    }

    private static boolean debugToConsole(Class c) {
        String debugMode = Log.debugMode.get(c);
        return Verify.equalsOne(debugMode.toLowerCase(), "true", "both", "console");
    }

    public static void setDebugMode(String mode) {
        Class callingPlugin = StackUtils.getCallingPlugin();
        debugMode.put(callingPlugin, mode);
    }
}
