/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.utility.log.DebugLog;

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
        reloading = true;
        if (full) {
            onDisable();
            onReload();
            onEnable();
        } else {
            onReload();
        }
        reloading = false;
    }

}