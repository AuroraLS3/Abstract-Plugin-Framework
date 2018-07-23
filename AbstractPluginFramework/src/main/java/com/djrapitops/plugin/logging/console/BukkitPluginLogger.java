package com.djrapitops.plugin.logging.console;

import com.djrapitops.plugin.logging.debug.DebugLogger;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class BukkitPluginLogger extends JavaUtilPluginLogger {

    public BukkitPluginLogger(Consumer<String> console,
                              Supplier<DebugLogger> debugLogger,
                              Logger logger) {
        super(console, debugLogger, logger);
    }
}
