package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.utilities.status.ProcessStatus;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;
import java.io.IOException;

/**
 * This class manages the messages going to the Bukkit's Logger.
 *
 * @param <T>
 * @author Rsl1122
 */
public class BukkitLog<T extends BukkitPlugin> extends PluginLog {

    final private T instance;

    public BukkitLog(T instance, String debugMode, String prefix) throws IOException {
        super(debugMode, prefix, instance.getDataFolder());
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
        if (!message.contains("[DEBUG]") && !message.contains("[DebugLog]") && !message.contains("[Errors]")) {
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
}
