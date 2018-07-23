package com.djrapitops.plugin.logging.console;

import com.djrapitops.plugin.logging.L;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PluginLogger implementation for testing purposes.
 *
 * @author Rsl1122
 */
public class TestPluginLogger implements PluginLogger {

    @Override
    public void log(L level, String... message) {
        for (String line : message) {
            System.out.println(level.name() + ": " + line);
        }
    }

    @Override
    public void log(L level, String message, Throwable throwable) {
        Logger.getGlobal().log(Level.SEVERE, message, throwable);
    }
}
