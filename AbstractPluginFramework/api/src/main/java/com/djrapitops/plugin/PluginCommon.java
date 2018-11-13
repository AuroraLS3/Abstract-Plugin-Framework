package com.djrapitops.plugin;

/**
 * Common code snippets for all plugins.
 *
 * @author Rsl1122
 */
class PluginCommon {

    private PluginCommon() {
        /* Hides constructor */
    }

    static void reload(APFPlugin plugin, boolean full) {
        try {
            plugin.setReloading(true);
            if (full) {
                plugin.onDisable();
                plugin.onReload();
                plugin.onEnable();
            } else {
                plugin.onReload();
            }
        } finally {
            plugin.setReloading(false);
        }
    }
}