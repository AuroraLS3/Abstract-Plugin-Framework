package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.utilities.status.ProcessStatus;
import java.io.File;
import org.bukkit.command.ConsoleCommandSender;

/**
 * This class manages the messages going to the Bukkit's Logger.
 *
 * @author Rsl1122
 * @param <T>
 */
public class BukkitLog<T extends BukkitPlugin> extends PluginLog {

    final private T instance;

    public BukkitLog(T instance, String debugMode, String prefix) {
        super(debugMode, prefix);
        this.instance = instance;
    }

    /**
     * Logs the message to the console as INFO.
     *
     * @param message "Message" will show up as [INFO][PLUGINNAME]: Message
     */
    @Override
    public void info(String message) {
        if (instance != null) {
            instance.getLogger().info(message);
        }
        if (!message.contains("[DEBUG]")) {
            debug(message);
        }
    }

    @Override
    public void infoColor(String message) {
        ConsoleCommandSender consoleSender = instance.getServer().getConsoleSender();
        consoleSender.sendMessage(prefix + " " + message);
        debug(message);
    }

    /**
     * Logs an error message to the console as ERROR.
     *
     * @param message "Message" will show up as [ERROR][Plan]: Message
     */
    @Override
    public void error(String message) {
        if (instance != null) {
            instance.getLogger().severe(message);
        }
    }

    /**
     * Logs a message to the a given file with a timestamp.
     *
     * @param message Message to log to Errors.txt [timestamp] Message
     * @param filename Name of the file to write to.
     */
    @Override
    public void toLog(String message, String filename) {
        if (instance == null) {
            return;
        }
        File folder = instance.getDataFolder();
        if (!folder.exists()) {
            folder.mkdir();
        }
        toLog(message, filename, folder);
    }

    @Override
    public void addToErrorStatus() {
        ProcessStatus process = instance.processStatus();
        String status = process.getStatus("Errors caught");
        if ("Process not running.".equals(status)) {
            process.setStatus("Errors caught", "1");
        } else {
            process.setStatus("Errors caught", "" + (Integer.parseInt(status) + 1));
        }
    }
}
