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