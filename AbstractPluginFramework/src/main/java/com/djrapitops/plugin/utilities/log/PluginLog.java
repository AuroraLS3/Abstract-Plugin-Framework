/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.utilities.FormattingUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rsl1122
 */
public abstract class PluginLog {

    private boolean debugToConsole;
    private boolean debugToFile;

    protected String prefix;
    final private String DEBUG = "DebugLog.txt";

    final private File folder;

    private final Map<String, DebugInfo> debugInfoMap;
    private final ErrorLogManager errorLogManager;

    public PluginLog(String debugMode, String prefix, File folder) throws IOException {
        this.folder = folder;
        this.prefix = prefix;
        setDebugMode(debugMode);
        debugInfoMap = new ConcurrentHashMap<>();
        errorLogManager = new ErrorLogManager(this);
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
        if (debugToConsole) {
            info("[DEBUG] " + message);
        }
        if (debugToFile) {
            toLog(message, DEBUG);
        }
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

    public void endAllDebugs() {
        for (DebugInfo i : debugInfoMap.values()) {
            i.addLine("Ended due to onDisable").toLog();
        }
    }

    protected void clearDebug(String task) {
        debugInfoMap.remove(task);
    }

    /**
     * Logs trace of caught Exception to Errors.txt and notifies on console.
     *
     * @param source Class name the exception was caught in.
     * @param e      Throwable, eg NullPointerException
     */
    public void toLog(String source, Throwable e) {
        String caught = source + " Caught " + e;
        error(caught + ". It has been logged to " + getErrorsFilename());
        List<String> stack = new ArrayList<>();
        stack.add(caught);
        for (StackTraceElement x : e.getStackTrace()) {
            stack.add("  " + x);
        }
        errorLogManager.addError(stack);
    }

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

    public File getFolder() {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

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
        boolean isDebugLog = filename.equals("DebugLog.txt");

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
                if (debugToConsole) {
                    if (isDebugLog) {
                        info("[" + filename.replace(".txt", "") + "] " + msg.substring(19));
                    } else {
                        info("[" + filename.replace(".txt", "") + "] " + msg);
                    }
                }
                if (!isDebugLog || (isDebugLog && debugToFile)) {
                    pw.println(msg);
                }
            }
            pw.flush();
        } catch (IOException e) {
            error("Failed to create " + filename + " file");
        } finally {
            close(pw, fw);
        }
    }

    public void toLog(String message, String filename, File folder) {
        File log = new File(folder, filename);
        if (filename.equals(getErrorsFilename())) {
            return;
        }
        try {
            if (!log.exists()) {
                log.createNewFile();
            }
            FileWriter fw = new FileWriter(log, true);
            try (PrintWriter pw = new PrintWriter(fw)) {
                String timestamp = FormattingUtils.formatTimeStampSecond(BenchUtil.getTime());
                pw.println("| " + timestamp + " | " + message);
                pw.flush();
            }
        } catch (IOException e) {
            error("Failed to create " + filename + " file");
        }
    }

    public void close(Closeable... close) {
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
        return errorLogManager.getErrorFileName();
    }

    public String getDebugFilename() {
        return DEBUG;
    }

    public void setDebugMode(String debugMode) {
        boolean both = debugMode.equals("true") || debugMode.equals("both");
        debugToConsole = both || debugMode.equals("console");
        debugToFile = both || debugMode.equals("file");
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public ErrorLogManager getErrorLogManager() {
        return errorLogManager;
    }

    public Map<String, DebugInfo> getAllDebugs() {
        return debugInfoMap;
    }
}
