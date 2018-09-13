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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rsl1122
 */
@Deprecated
public class Log extends DebugLog {

    @Deprecated
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

    @Deprecated
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

    @Deprecated
    public static void warn(String s) {
        warn(s, StackUtils.getCallingPlugin());
    }

    @Deprecated
    public static void warn(String s, Class c) {
        IPlugin instance = StaticHolder.getInstance(c);
        if (instance == null) {
            return;
        }
        instance.getPluginLogger().warn(s);
    }

    @Deprecated
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

    @Deprecated
    public static void debug(Class callingPlugin, String... lines) {
        debug(Arrays.asList(lines), callingPlugin);
    }

    @Deprecated
    public static void debug(String... lines) {
        debug(Arrays.asList(lines));
    }

    @Deprecated
    public static void debug(List<String> lines) {
        debug(lines, StackUtils.getCallingPlugin());
    }

    private static void debug(List<String> lines, Class callingPlugin) {
        StaticHolder.getInstance(callingPlugin).getPluginLogger().debug(lines.toArray(new String[0]));
    }

    @Deprecated
    public static void toLog(Class clazz, Throwable e) {
        toLog(clazz.getName(), e);
    }

    @Deprecated
    public static void toLog(String source, Throwable e) {
        Class callingPlugin = StackUtils.getCallingPlugin();
        toLog(source, e, callingPlugin);
    }

    private static void toLog(String source, Throwable e, Class callingPlugin) {
        StaticHolder.getInstance(callingPlugin).getErrorHandler().log(L.ERROR, callingPlugin, e);
    }

    @Deprecated
    public static void setErrorManager(ErrorManager errorManager) {
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

    @Deprecated
    public static void setDebugMode(String mode) {
    }

    @Deprecated
    public static List<String> getDebugLogInMemory() {
        return new ArrayList<>();
    }

    @Deprecated
    public static String getErrorFileName() {
        String day = new SimpleDateFormat("yyyy_MM_dd").format(TimeAmount.currentMs());
        return "ErrorLog-" + day + ".txt";
    }
}
