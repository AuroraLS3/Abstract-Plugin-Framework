package com.djrapitops.plugin;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.systems.NotificationCenter;
import com.djrapitops.plugin.api.systems.TaskCenter;
import com.djrapitops.plugin.api.utility.Version;
import com.djrapitops.plugin.api.utility.log.DebugLog;
import com.djrapitops.plugin.command.SubCommand;
import com.djrapitops.plugin.command.bungee.BungeeCommand;
import com.djrapitops.plugin.task.RunnableFactory;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Rsl1122
 */
public abstract class BungeePlugin<T extends BungeePlugin> extends net.md_5.bungee.api.plugin.Plugin implements IPlugin {

    protected boolean reloading;

    protected final T plugin;

    public BungeePlugin(T plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        StaticHolder.register(this);
    }

    @Override
    public void onDisable() {
        Class<? extends IPlugin> pluginClass = getClass();
        StaticHolder.unRegister(pluginClass);
        Benchmark.pluginDisabled(pluginClass);
        DebugLog.pluginDisabled(pluginClass);
        TaskCenter.cancelAllKnownTasks(pluginClass);
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
    public void log(String level, String s) {
        Logger logger = getLogger();
        switch (level.toUpperCase()) {
            case "INFO":
            case "I":
                logger.info(s);
                break;
            case "INFO_COLOR":
                getProxy().getConsole().sendMessage(new TextComponent(s));
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
            getProxy().getPluginManager().registerListener(this, listener);
            StaticHolder.saveInstance(listener.getClass(), plugin.getClass());
        }
    }

    public void registerCommand(String name, SubCommand command) {
        getProxy().getPluginManager().registerCommand(this, new BungeeCommand(command));
        StaticHolder.saveInstance(command.getClass(), plugin.getClass());
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

    public abstract void onReload();
}
