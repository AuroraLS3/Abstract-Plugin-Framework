package com.djrapitops.plugin.logging.console;

import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.logging.L;
import com.djrapitops.plugin.logging.debug.DebugLogger;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class SpongePluginLogger implements PluginLogger {

    private final SpongePlugin plugin;
    private final Supplier<DebugLogger> debugLogger;

    public SpongePluginLogger(SpongePlugin plugin, Supplier<DebugLogger> debugLogger) {
        this.plugin = plugin;
        this.debugLogger = debugLogger;
    }

    @Override
    public void log(L level, String... message) {
        if (level == L.DEBUG) {
            debugLogger.get().logOn(message);
            return;
        } else if (level != L.DEBUG_INFO) {
            log(L.DEBUG, message);
        }
        Logger logger = plugin.getLogger();
        switch (level) {
            case CRITICAL:
            case ERROR:
                for (String line : message) {
                    logger.error(line);
                }
                break;
            case WARN:
                for (String line : message) {
                    logger.warn(line);
                }
                break;
            case DEBUG_INFO:
                for (String line : message) {
                    logger.info("[DEBUG] " + line);
                }
                break;
            case INFO_COLOR:
            case INFO:
            default:
                for (String line : message) {
                    logger.info(line);
                }
                break;
        }
    }

    @Override
    public void log(L level, String message, Throwable throwable) {
        Logger logger = plugin.getLogger();
        switch (level) {
            case CRITICAL:
            case ERROR:
                logger.error(message, throwable);
                break;
            case WARN:
            default:
                logger.warn(message, throwable);
                break;
        }
    }
}
