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

    void onEnable();

    void onDisable();

    void setDebugLoggers(DebugLogger... loggers);

    void onReload();

    void reloadPlugin(boolean full);

    boolean isReloading();

    File getDataFolder();

    String getVersion();

    void registerCommand(String name, CommandNode command);

    void setReloading(boolean value);

    RunnableFactory getRunnableFactory();

    PluginLogger getPluginLogger();

    DebugLogger getDebugLogger();

    ErrorHandler getErrorHandler();

    Timings getTimings();
}
