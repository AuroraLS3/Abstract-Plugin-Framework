package com.djrapitops.plugin;

/**
 *
 * @author Rsl1122
 */
public abstract class Plugin {
    
    protected boolean reloading;
    
    public abstract void onEnable();
    
    public abstract void onDisable();
    
    public void reloadPlugin(boolean full) {
        reloading = true;
        if (full) {
            onDisable();
            reload();
            onEnable();
        } else {
            reload();
        }
        reloading = false;
    }
    
    public abstract void reload();
    
}
