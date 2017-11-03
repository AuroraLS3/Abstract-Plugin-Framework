package com.djrapitops.plugin;

import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.command.bukkit.BukkitCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.event.Level;

import java.util.logging.Logger;

/**
 * @author Rsl1122
 */
public abstract class BukkitPlugin extends JavaPlugin implements IPlugin {

    private final Plugin plugin;

    public BukkitPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        plugin.enable(this);
    }

    @Override
    public void onDisable() {
        plugin.onDisable();
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
        getServer().getPluginManager().registerEvents(listener, this);
        StaticHolder.saveInstance(listener.getClass(), plugin.getClass());
    }

    public void registerCommand(String name, SubCommand command) {
        getCommand(name).setExecutor(new BukkitCommand(command));
        StaticHolder.saveInstance(command.getClass(), plugin.getClass());
    }
}