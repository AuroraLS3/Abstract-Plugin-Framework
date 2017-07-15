/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.utilities.BenchUtil;
import com.djrapitops.plugin.utilities.FormattingUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 *
 * @author Rsl1122
 */
public abstract class PluginLog {

    private String debugMode;
    protected String prefix;
    final private String DEBUG = "DebugLog.txt";
    final private String ERRORS = "Errors.txt";

    public PluginLog(String debugMode, String prefix) {
        this.prefix = prefix;
        this.debugMode = debugMode;
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

    /**
     * Logs trace of caught Exception to Errors.txt and notifies on console.
     *
     * @param source Class name the exception was caught in.
     * @param e Throwable, eg NullPointerException
     */
    public void toLog(String source, Throwable e) {
        error("Caught " + e.toString() + ". It has been logged to " + ERRORS);
        toLog(source + " Caught " + e, ERRORS);
        for (StackTraceElement x : e.getStackTrace()) {
            toLog("  " + x, ERRORS);
        }
        toLog("", ERRORS);
    }

    public abstract void addToErrorStatus();

    /**
     * Logs multiple caught Errors to Errors.txt.
     *
     * @param source Class name the exception was caught in.
     * @param e Collection of Throwables, eg NullPointerException
     */
    public void toLog(String source, Collection<Throwable> e) {
        for (Throwable ex : e) {
            toLog(source, ex);
        }
    }

    /**
     * Logs a message to the a given file with a timestamp.
     *
     * @param message Message to log to Errors.txt [timestamp] Message
     * @param filename Name of the file to write to.
     */
    public abstract void toLog(String message, String filename);

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

    public String getErrorsFilename() {
        return ERRORS;
    }

    public void setDebugMode(String debugMode) {
        this.debugMode = debugMode;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
