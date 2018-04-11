package com.djrapitops.plugin;

import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.CommandType;
import com.djrapitops.plugin.command.ISender;
import com.djrapitops.plugin.command.TreeCmdNode;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PluginCommonTest {

    private IPlugin plugin;

    @Before
    public void setUp() {

        plugin = new IPlugin() {
            boolean enabled = false;
            boolean reloading = false;

            @Override
            public void onEnable() {
                enabled = true;
            }

            @Override
            public void onDisable() {
                enabled = false;
            }

            @Override
            public void onReload() {

            }

            @Override
            public void reloadPlugin(boolean full) {
                PluginCommon.reload(this, full);
            }

            @Override
            public boolean isReloading() {
                return reloading;
            }

            @Override
            public void log(String level, String s) {
            }

            @Override
            public File getDataFolder() {
                return null;
            }

            @Override
            public String getVersion() {
                return null;
            }

            @Override
            public void registerCommand(String name, CommandNode command) {

            }
        };
    }

    @Test
    public void reload() {
        plugin.reloadPlugin(true);
    }

    @Test
    public void saveCommandInstances() {
        TreeCmdNode command = new TreeCmdNode("", "", CommandType.ALL, null);
        CommandNode commandNode = new CommandNode("t", "", CommandType.ALL) {
            @Override
            public void onCommand(ISender sender, String commandLabel, String[] args) {

            }
        };
        command.setNodeGroups(new CommandNode[]{
                commandNode
        });
        PluginCommon.saveCommandInstances(command, BukkitPlugin.class);
        assertEquals(BukkitPlugin.class, StaticHolder.getProvidingPlugin(command.getClass()));
        assertEquals(BukkitPlugin.class, StaticHolder.getProvidingPlugin(commandNode.getClass()));
    }
}