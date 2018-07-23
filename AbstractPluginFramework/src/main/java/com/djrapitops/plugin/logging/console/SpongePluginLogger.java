package com.djrapitops.plugin.logging.console;

import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.logging.L;
import org.slf4j.Logger;


public class SpongePluginLogger implements PluginLogger {

    private final SpongePlugin plugin;

    public SpongePluginLogger(SpongePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void log(L level, String... message) {
        if (level == L.DEBUG) {
            // Log to debug log.
            return;
        } else {
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
