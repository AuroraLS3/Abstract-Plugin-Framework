package net.playeranalytics.plugin.server;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaUtilLoggerShim implements PluginLogger {

    private final Logger logger;

    public JavaUtilLoggerShim(Logger logger) {
        this.logger = logger;
    }

    @Override
    public PluginLogger info(String message) {
        logger.info(message);
        return this;
    }

    @Override
    public PluginLogger warn(String message) {
        logger.warning(message);
        return this;
    }

    @Override
    public PluginLogger error(String message) {
        logger.severe(message);
        return this;
    }

    @Override
    public PluginLogger warn(String message, Throwable throwable) {
        logger.log(Level.WARNING, message, throwable);
        return this;
    }

    @Override
    public PluginLogger error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
        return this;
    }
}
