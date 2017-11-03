package com.djrapitops.plugin;

import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.command.bungee.BungeeCommand;
import net.md_5.bungee.api.plugin.Listener;

import java.util.logging.Logger;

/**
 * @author Rsl1122
 */
public abstract class BungeePlugin extends net.md_5.bungee.api.plugin.Plugin implements IPlugin {

    private final Plugin plugin;

    public BungeePlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        plugin.enable(this);
    }

    @Override
    public void onDisable() {
        plugin.disable();
    }

    @Override
    public void reloadPlugin(boolean full) {
        plugin.reloadPlugin(full);
    }

    @Override
    public void log(String level, String s) {
        Logger logger = getLogger();
        switch (level.toUpperCase()) {
            case "INFO":
            case "I":
                logger.info(s);
            case "W":
            case "WARN":
            case "WARNING":
                logger.warning(s);
            case "E":
            case "ERR":
            case "ERROR":
            case "SEVERE":
                logger.severe(s);
            default:
                logger.info(s);
        }
    }

    public void registerListener(Listener listener) {
        getProxy().getPluginManager().registerListener(this, listener);
        StaticHolder.saveInstance(listener.getClass(), plugin.getClass());
    }

    public void registerCommand(String name, SubCommand command) {
        getProxy().getPluginManager().registerCommand(this, new BungeeCommand(command));
        StaticHolder.saveInstance(command.getClass(), plugin.getClass());
    }
}
