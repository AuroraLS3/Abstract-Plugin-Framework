/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.api.utility.log;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.api.TimeAmount;
import com.djrapitops.plugin.api.utility.log.errormanager.ErrorManager;
import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.utilities.StackUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Rsl1122
 */
@Deprecated
public class Log extends DebugLog {

    private static final String ERROR_FILE_NAME = "ErrorLog-";

    private static final Map<Class, String> debugMode = new HashMap<>();

    private static final Map<Class, List<String>> debugLogs = new HashMap<>();

    private static final Map<Class, ErrorManager> errorManagers = new HashMap<>();

    public static void info(String s) {
        info(s, StackUtils.getCallingPlugin());
    }

    private static void info(String s, Class c) {
        IPlugin instance = StaticHolder.getInstance(c);
        if (instance == null) {
            return;
        }
        instance.getPluginLogger().info(s);
    }

    public static void infoColor(String s) {
        infoColor(s, StackUtils.getCallingPlugin());
    }

    private static void infoColor(String s, Class c) {
        IPlugin instance = StaticHolder.getInstance(c);
        if (instance == null) {
            return;
        }
        instance.getPluginLogger().log(L.INFO_COLOR, s);
    }

    public static void warn(String s) {
        warn(s, StackUtils.getCallingPlugin());
    }

    public static void warn(String s, Class c) {
        IPlugin instance = StaticHolder.getInstance(c);
        if (instance == null) {
            return;
        }
        instance.getPluginLogger().warn(s);
    }

    public static void error(String s) {
        error(s, StackUtils.getCallingPlugin());
    }

    private static void error(String s, Class c) {
        IPlugin instance = StaticHolder.getInstance(c);
        if (instance == null) {
            return;
        }
        instance.getPluginLogger().error(s);
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
        StaticHolder.getInstance(callingPlugin).getPluginLogger().debug(lines.toArray(new String[0]));
    }

    public static void toLog(Class clazz, Throwable e) {
        toLog(clazz.getName(), e);
    }

    public static void toLog(String source, Throwable e) {
        Class callingPlugin = StackUtils.getCallingPlugin();
        toLog(source, e, callingPlugin);
    }

    private static void toLog(String source, Throwable e, Class callingPlugin) {
        StaticHolder.getInstance(callingPlugin).getErrorHandler().log(L.ERROR, callingPlugin, e);
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
            File apfErrorLogs = new File("APF_error_logs");
            apfErrorLogs.mkdirs();
            return apfErrorLogs;
        }
        File dataFolder = instance.getDataFolder();
        File logsFolder = new File(dataFolder, "logs");
        logsFolder.mkdirs();
        return logsFolder;
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

    public static String getErrorFileName() {
        String day = new SimpleDateFormat("yyyy_MM_dd").format(TimeAmount.currentMs());
        return ERROR_FILE_NAME + day + ".txt";
    }
}
