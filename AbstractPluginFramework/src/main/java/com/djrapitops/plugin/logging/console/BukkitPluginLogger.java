package com.djrapitops.plugin.logging.console;

import com.djrapitops.plugin.logging.debug.DebugLogger;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * {@link PluginLogger} implementation for Bukkit.
 *
 * @author Rsl1122
 * @see JavaUtilPluginLogger
 */
public class BukkitPluginLogger extends JavaUtilPluginLogger {

    /**
     * Create a new BukkitPluginLogger.
     *
     * @param console     Consumer of the logging method for colored messages on the console.
     * @param debugLogger {@link DebugLogger} to log all channels on.
     * @param logger      Bukkit plugin logger for logging messages.
     */
    public BukkitPluginLogger(Consumer<String> console,
                              Supplier<DebugLogger> debugLogger,
                              Logger logger) {
        super(console, debugLogger, logger);
    }
}
