/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin;

import com.djrapitops.plugin.benchmarking.Timings;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.logging.console.PluginLogger;
import com.djrapitops.plugin.logging.debug.DebugLogger;
import com.djrapitops.plugin.logging.error.ErrorHandler;
import com.djrapitops.plugin.task.RunnableFactory;

import java.io.File;

/**
 * Main interface all APF plugins implement.
 *
 * @author Rsl1122
 */
public interface IPlugin {

    /**
     * Method to implement for plugin enabling.
     * <p>
     * Implementations should call super.onEnable() on the first line.
     */
    void onEnable();

    /**
     * Method to implement for plugin disabling.
     * <p>
     * Implementations should call super.onDisable() on the last line.
     */
    void onDisable();

    /**
     * Replace active debug loggers with some other {@link DebugLogger} implementations.
     *
     * @param loggers DebugLoggers to change to.
     */
    void setDebugLoggers(DebugLogger... loggers);

    /**
     * Replace active error handlers with some other {@link ErrorHandler} implementations.
     *
     * @param errorHandlers ErrorHandlers to change to.
     */
    void setErrorHandlers(ErrorHandler... errorHandlers);

    /**
     * Method that is called during a plugin reload.
     */
    void onReload();

    /**
     * Method that should be called when you want to reload the plugin.
     *
     * @param full Should onDisable and onEnable be called?
     */
    void reloadPlugin(boolean full);

    /**
     * Method for checking if the plugin is being reloaded using {@link IPlugin#reloadPlugin(boolean)} method.
     *
     * @return true/false
     */
    boolean isReloading();

    /**
     * Internal method for setting the plugin as reloading.
     *
     * @param value is the plugin reloading.
     */
    void setReloading(boolean value);

    /**
     * Retrieve the folder that contains data and configuration for the plugin.
     *
     * @return a folder that is plugin specific for storing things.
     */
    File getDataFolder();

    /**
     * Retrieve the version of the plugin.
     *
     * @return Platform specific developer defined plugin version.
     */
    String getVersion();

    /**
     * Register a {@link CommandNode} as a platform specific command.
     *
     * @param name    Name of the command.
     * @param command CommandNode to use for executing commands.
     */
    void registerCommand(String name, CommandNode command);

    /**
     * Retrieve the instance of {@link RunnableFactory} specific to this plugin.
     *
     * @return a factory for creating abstracted tasks.
     */
    RunnableFactory getRunnableFactory();

    /**
     * Retrieve the instance of {@link PluginLogger} specific to this plugin.
     *
     * @return an abstraction wrapper over logger implementations.
     */
    PluginLogger getPluginLogger();

    /**
     * Retrieve the instance of {@link DebugLogger} specific to this plugin.
     *
     * @return an utility to log debug information for bug issues.
     */
    DebugLogger getDebugLogger();

    /**
     * Retrieve the instance of {@link ErrorHandler} specific to this plugin.
     *
     * @return an utility to log exceptions.
     */
    ErrorHandler getErrorHandler();

    /**
     * Retrieve the instance of {@link Timings} specific to this plugin.
     *
     * @return an utility to benchmark execution.
     */
    Timings getTimings();
}
