package com.djrapitops.plugin;

import com.djrapitops.plugin.api.systems.NotificationCenter;

/**
 * @author Rsl1122
 */
public abstract class Plugin implements IPlugin {

    protected boolean reloading;

    void enable() {
        StaticHolder.register(getClass(), this);
        onEnable();
    }

    @Override
    public abstract void onEnable();

    void disable() {
        StaticHolder.unRegister(getClass());
        onDisable();
    }

    @Override
    public abstract void onDisable();

    @Override
    public void reloadPlugin(boolean full) {
        reloading = true;
        if (full) {
            disable();
            onReload();
            enable();
        } else {
            onReload();
        }
        reloading = false;
    }

    public abstract void onReload();

    public NotificationCenter getNotificationCenter() {
        return StaticHolder.getNotificationCenter();
    }
}
