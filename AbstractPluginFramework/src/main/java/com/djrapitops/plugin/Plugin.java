package com.djrapitops.plugin;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.systems.NotificationCenter;
import com.djrapitops.plugin.api.utility.log.DebugLog;

import java.io.File;
import java.net.URL;

/**
 * @author Rsl1122
 */
public abstract class Plugin implements IPlugin {

    protected boolean reloading;

    private IPlugin provider;

    void enable(IPlugin provider) {
        this.provider = provider;
        StaticHolder.register(getClass(), this);
        onEnable();
    }

    @Override
    public abstract void onEnable();

    void disable() {
        Class<? extends Plugin> pluginClass = getClass();
        StaticHolder.unRegister(pluginClass);
        Benchmark.pluginDisabled(pluginClass);
        DebugLog.pluginDisabled(pluginClass);
        provider = null;
    }

    @Override
    public abstract void onDisable();

    @Override
    public void reloadPlugin(boolean full) {
        reloading = true;
        if (full) {
            IPlugin safe = provider;
            disable();
            onReload();
            enable(safe);
        } else {
            onReload();
        }
        reloading = false;
    }

    public abstract void onReload();

    public NotificationCenter getNotificationCenter() {
        return StaticHolder.getNotificationCenter();
    }

    public void log(String level, String s) {
        if (provider != null) {
            provider.log(level, s);
        }
    }

    public void setProvider(IPlugin provider) {
        this.provider = provider;
    }

    @Override
    public File getDataFolder() {
        if (provider == null) {
            throw new IllegalStateException("Plugin provider can not be null after plugin is enabled.");
        }
        return provider.getDataFolder();
    }

    @Override
    public String getVersion() {
        return provider.getVersion();
    }

    protected boolean isNewVersionAvailable(String versionStringUrl) {
        boolean gitHubAddress = versionStringUrl.contains("github.com");

    }
}
