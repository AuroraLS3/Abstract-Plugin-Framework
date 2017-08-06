/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.utilities.BenchUtil;
import com.djrapitops.plugin.utilities.FormattingUtils;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rsl1122
 */
public abstract class PluginLog {

    private String debugMode;
    protected String prefix;
    final private String DEBUG = "DebugLog.txt";
    final private String ERRORS = "Errors.txt";

    private final Map<String, DebugInfo> debugInfoMap;

    public PluginLog(String debugMode, String prefix) {
        this.prefix = prefix;
        this.debugMode = debugMode;
        debugInfoMap = new HashMap<>();
    }

    /**
     * Logs the message to the console as INFO.
     *
     * @param message "Message" will show up as [INFO][PLUGINNAME]: Message
     */
    public abstract void info(String message);

    public abstract void infoColor(String message);

    /**
     * Logs an error message to the console as ERROR.
     *
     * @param message "Message" will show up as [ERROR][Plan]: Message
     */
    public abstract void error(String message);

    /**
     * Logs a debug message to the console as INFO if Settings.Debug is true.
     *
     * @param message "Message" will show up as [INFO][Plan]: [DEBUG] Message
     */
    public void debug(String message) {
        boolean both = debugMode.equals("true") || debugMode.equals("both");
        boolean logConsole = both || debugMode.equals("console");
        boolean logFile = debugMode.equals("file") || both;
        if (logConsole) {
            info("[DEBUG] " + message);
        }
        if (logFile) {
            toLog(message, DEBUG);
        }
    }

    public boolean logConsole() {
        return debugMode.equals("true") || debugMode.equals("both") || debugMode.equals("console");
    }

    public DebugInfo getDebug(String task) {
        DebugInfo debug = debugInfoMap.get(task);
        if (debug == null) {
            debug = new DebugInfo(this, BenchUtil.getTime(), task);
            debugInfoMap.put(task, debug);
        }
        return debug;
    }

    public void endDebug(String task) {
        DebugInfo debug = debugInfoMap.get(task);
        if (debug != null) {
            debug.toLog();
        }
    }

    public void endDebug(String task, long duration) {
        DebugInfo debug = debugInfoMap.get(task);
        if (debug != null) {
            debug.toLog(duration);
        }
    }

    /**
     * Logs trace of caught Exception to Errors.txt and notifies on console.
     *
     * @param source Class name the exception was caught in.
     * @param e      Throwable, eg NullPointerException
     */
    public void toLog(String source, Throwable e) {
        error("Caught " + e.toString() + ". It has been logged to " + ERRORS);
        toLog(source + " Caught " + e, ERRORS);
        for (StackTraceElement x : e.getStackTrace()) {
            toLog("  " + x, ERRORS);
        }
        toLog("", ERRORS);
    }

    @Deprecated
    public abstract void addToErrorStatus();

    /**
     * Logs multiple caught Errors to Errors.txt.
     *
     * @param source Class name the exception was caught in.
     * @param e      Collection of Throwables, eg NullPointerException
     */
    public void toLog(String source, Collection<Throwable> e) {
        for (Throwable ex : e) {
            toLog(source, ex);
        }
    }

    public abstract File getFolder() throws IllegalStateException;

    /**
     * Logs a message to the a given file with a timestamp.
     *
     * @param message  Message to log to Errors.txt [timestamp] Message
     * @param filename Name of the file to write to.
     */
    public void toLog(String message, String filename) {
        try {
            File folder = getFolder();
            toLog(message, filename, folder);
        } catch (IllegalStateException ignore) {
        }
    }

    public void toLog(List<String> message, String filename) {
        try {
            File folder = getFolder();
            toLog(message, filename, folder);
        } catch (IllegalStateException ignore) {
        }
    }

    public void toLog(List<String> message, String filename, File folder) {
        File log = new File(folder, filename);
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            if (!log.exists()) {
                log.createNewFile();
            }
            fw = new FileWriter(log, true);
            pw = new PrintWriter(fw);
            for (String msg : message) {
                pw.println(msg);
            }
            pw.flush();
        } catch (IOException e) {
            error("Failed to create" + filename + "file");
        } finally {
            close(pw, fw);
        }
    }

    public void toLog(String message, String filename, File folder) {
        File log = new File(folder, filename);
        if (filename.equals(ERRORS)) {
            debug(message);
        }
        try {
            if (!log.exists()) {
                log.createNewFile();
            }
            FileWriter fw = new FileWriter(log, true);
            try (PrintWriter pw = new PrintWriter(fw)) {
                String timestamp = FormattingUtils.formatTimeStampSecond(BenchUtil.getTime());
                pw.println("[" + timestamp + "] " + message);
                pw.flush();
            }
        } catch (IOException e) {
            error("Failed to create" + filename + "file");
        }
    }

    private void close(Closeable... close) {
        for (Closeable c : close) {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    public String getErrorsFilename() {
        return ERRORS;
    }

    public String getDebugFilename() {
        return DEBUG;
    }

    public void setDebugMode(String debugMode) {
        this.debugMode = debugMode;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
