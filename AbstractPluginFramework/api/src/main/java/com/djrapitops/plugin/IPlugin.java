/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
