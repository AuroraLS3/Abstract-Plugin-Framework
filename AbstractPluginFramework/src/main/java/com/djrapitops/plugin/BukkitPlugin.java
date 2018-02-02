package com.djrapitops.plugin;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.systems.NotificationCenter;
import com.djrapitops.plugin.api.systems.TaskCenter;
import com.djrapitops.plugin.api.utility.Version;
import com.djrapitops.plugin.api.utility.log.DebugLog;
import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.command.bukkit.BukkitCommand;
import com.djrapitops.plugin.task.RunnableFactory;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Rsl1122
 */
public abstract class BukkitPlugin extends JavaPlugin implements IPlugin {

    protected boolean reloading;

    @Override
    public void onEnable() {
        StaticHolder.register(this);
    }

    @Override
    public void onDisable() {
        Class<? extends IPlugin> pluginClass = getClass();
        Benchmark.pluginDisabled(pluginClass);
        DebugLog.pluginDisabled(pluginClass);
        TaskCenter.cancelAllKnownTasks(pluginClass);
        StaticHolder.unRegister(pluginClass);
    }

    @Override
    public void log(String level, String s) {
        Logger logger = getLogger();
        switch (level.toUpperCase()) {
            case "INFO":
            case "I":
                logger.info(s);
                break;
            case "INFO_COLOR":
                getServer().getConsoleSender().sendMessage(s);
                break;
            case "W":
            case "WARN":
            case "WARNING":
                logger.warning(s);
                break;
            case "E":
            case "ERR":
            case "ERROR":
            case "SEVERE":
                logger.severe(s);
                break;
            default:
                logger.info(s);
                break;
        }
    }

    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
            StaticHolder.saveInstance(listener.getClass(), getClass());
        }
    }

    public void registerCommand(String name, SubCommand command) {
        getCommand(name).setExecutor(new BukkitCommand(command));
        StaticHolder.saveInstance(command.getClass(), getClass());
    }

    protected boolean isNewVersionAvailable(String versionStringUrl) throws IOException {
        return Version.checkVersion(getVersion(), versionStringUrl);
    }

    public NotificationCenter getNotificationCenter() {
        return StaticHolder.getNotificationCenter();
    }

    public RunnableFactory getRunnableFactory() {
        return StaticHolder.getRunnableFactory();
    }

    @Override
    public void reloadPlugin(boolean full) {
        reloading = true;
        if (full) {
            onDisable();
            onReload();
            onEnable();
        } else {
            onReload();
        }
        reloading = false;
    }

    @Override
    public boolean isReloading() {
        return reloading;
    }
}