package com.djrapitops.plugin;

import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.TreeCmdNode;

/**
 * Common code snippets for all plugins.
 *
 * @author Rsl1122
 */
public class PluginCommon {

    static void reload(IPlugin plugin, boolean full) {
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

    static void setReloading(IPlugin plugin, boolean value) {
        if (plugin instanceof BukkitPlugin) {
            ((BukkitPlugin) plugin).setReloading(value);
        } else if (plugin instanceof BungeePlugin) {
            ((BungeePlugin) plugin).setReloading(value);
        } else if (plugin instanceof SpongePlugin) {
            ((SpongePlugin) plugin).setReloading(value);
        }
    }

    public static void saveCommandInstances(CommandNode command, Class<? extends IPlugin> clazz) {
        StaticHolder.saveInstance(command.getClass(), clazz);
        if (command instanceof TreeCmdNode) {
            for (CommandNode[] nodeGroup : ((TreeCmdNode) command).getNodeGroups()) {
                for (CommandNode node : nodeGroup) {
                    if (node == null) {
                        continue;
                    }
                    StaticHolder.saveInstance(node.getClass(), clazz);
                }
            }
        }
    }

}