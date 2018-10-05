package com.djrapitops.plugin.logging.console;

import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.debug.ConsoleDebugLogger;
import com.djrapitops.plugin.logging.debug.DebugLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link PluginLogger} implementation for testing purposes.
 *
 * @author Rsl1122
 */
public class TestPluginLogger implements PluginLogger {

    @Override
    public void log(L level, String... message) {
        for (String line : message) {
            Logger.getAnonymousLogger().log(Level.INFO, () -> level.name() + ": " + line);
        }
    }

    @Override
    public void log(L level, String message, Throwable throwable) {
        Logger.getAnonymousLogger().log(Level.SEVERE, message, throwable);
    }

    @Override
    public DebugLogger getDebugLogger() {
        return new ConsoleDebugLogger(this);
    }
}
