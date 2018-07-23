package com.djrapitops.plugin.logging.console;

import com.djrapitops.plugin.logging.L;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaUtilPluginLogger implements PluginLogger {

    protected final Consumer<String> console;
    protected final Logger logger;

    public JavaUtilPluginLogger(Consumer<String> console, Logger logger) {
        this.console = console;
        this.logger = logger;
    }

    @Override
    public void log(L level, String... message) {
        if (level == L.DEBUG) {
            // Log to debug log.
            return;
        } else {
            log(L.DEBUG, message);
        }
        switch (level) {
            case CRITICAL:
            case ERROR:
                for (String line : message) {
                    logger.log(Level.SEVERE, line);
                }
                break;
            case WARN:
                for (String line : message) {
                    logger.log(Level.WARNING, line);
                }
                break;
            case INFO_COLOR:
                for (String line : message) {
                    console.accept(line);
                }
                break;
            case INFO:
            default:
                for (String line : message) {
                    logger.log(Level.INFO, line);
                }
                break;
        }
    }

    @Override
    public void log(L level, String message, Throwable throwable) {
        switch (level) {
            case CRITICAL:
            case ERROR:
                logger.log(Level.SEVERE, message, throwable);
                break;
            case WARN:
            default:
                logger.log(Level.WARNING, message, throwable);
                break;
        }
    }
}
