package com.djrapitops.plugin;

/**
 * Common code snippets for all plugins.
 *
 * @author Rsl1122
 */
public class PluginCommon {

    public static void reload(IPlugin plugin, boolean full) {
        try {
            setReloading(plugin, true);
            if (full) {
                plugin.onDisable();
                plugin.onReload();
                plugin.onEnable();
            } else {
                plugin.onReload();
            }
        } finally {
            setReloading(plugin, false);
        }
    }

    private static void setReloading(IPlugin plugin, boolean value) {
        if (plugin instanceof BukkitPlugin) {
            ((BukkitPlugin) plugin).setReloading(value);
        } else if (plugin instanceof BungeePlugin) {
            ((BungeePlugin) plugin).setReloading(value);
        } else if (plugin instanceof SpongePlugin) {
            ((SpongePlugin) plugin).setReloading(value);
        }
    }

}