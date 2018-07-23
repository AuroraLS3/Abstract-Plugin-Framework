package com.djrapitops.plugin.logging.console;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class BungeePluginLogger extends JavaUtilPluginLogger {

    public BungeePluginLogger(Consumer<String> console, Logger logger) {
        super(console, logger);
    }
}
