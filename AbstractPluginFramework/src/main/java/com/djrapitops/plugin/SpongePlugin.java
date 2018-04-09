/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.utility.log.DebugLog;
import org.slf4j.Logger;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public abstract class SpongePlugin implements IPlugin {

    protected boolean reloading;

    @Override
    public void onEnable() {
        StaticHolder.register(this);
    }

    @Override
    public void onDisable() {
        Class<? extends IPlugin> pluginClass = getClass();
        StaticHolder.unRegister(pluginClass);
        Benchmark.pluginDisabled(pluginClass);
        DebugLog.pluginDisabled(pluginClass);
    }

    @Override
    public void reloadPlugin(boolean full) {
        PluginCommon.reload(this, full);
    }

    @Override
    public void log(String level, String s) {
        Logger logger = getLogger();
        switch (level.toUpperCase()) {
            case "INFO":
            case "I":
            case "INFO_COLOR":
                logger.info(s);
                break;
            case "W":
            case "WARN":
            case "WARNING":
                logger.warn(s);
                break;
            case "E":
            case "ERR":
            case "ERROR":
            case "SEVERE":
                logger.error(s);
                break;
            default:
                logger.info(s);
                break;
        }
    }

    protected abstract Logger getLogger();

    void setReloading(boolean reloading) {
        this.reloading = reloading;
    }
}