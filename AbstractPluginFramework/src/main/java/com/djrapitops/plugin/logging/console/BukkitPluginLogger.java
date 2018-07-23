package com.djrapitops.plugin.logging.console;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class BukkitPluginLogger extends JavaUtilPluginLogger {

    public BukkitPluginLogger(Consumer<String> console, Logger logger) {
        super(console, logger);
    }
}
