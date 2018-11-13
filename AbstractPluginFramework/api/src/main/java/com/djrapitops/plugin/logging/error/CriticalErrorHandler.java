package com.djrapitops.plugin.logging.error;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.logging.L;

/**
 * {@link ErrorHandler} implementation that performs plugin shutdown when {@code L.CRITICAL} is used.
 *
 * @author Rsl1122
 */
public class CriticalErrorHandler implements ErrorHandler {

    private final IPlugin plugin;

    /**
     * Create a CriticalErrorHandler.
     *
     * @param plugin Plugin to shutdown when {@code L.CRITICAL} is used.
     */
    public CriticalErrorHandler(IPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void log(L level, Class caughtBy, Throwable throwable) {
        if (L.CRITICAL == level) {
            plugin.getPluginLogger().error("CRITICAL error triggered a plugin shutdown.");
            plugin.onDisable();
        }
    }
}