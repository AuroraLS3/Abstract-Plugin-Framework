package com.djrapitops.plugin;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.utility.Version;
import com.djrapitops.plugin.api.utility.log.DebugLog;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.bungee.BungeeCommand;
import com.djrapitops.plugin.logging.console.BungeePluginLogger;
import com.djrapitops.plugin.logging.console.PluginLogger;
import com.djrapitops.plugin.task.RunnableFactory;
import com.djrapitops.plugin.task.bungee.BungeeRunnableFactory;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Rsl1122
 */
public abstract class BungeePlugin extends net.md_5.bungee.api.plugin.Plugin implements IPlugin {

    protected PluginLogger logger;
    protected final RunnableFactory runnableFactory;
    
    protected boolean reloading;

    public BungeePlugin() {
        runnableFactory = new BungeeRunnableFactory(this);
        logger = new BungeePluginLogger(
                message -> getProxy().getConsole().sendMessage(new TextComponent(message)),
                getLogger()
        );
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
        runnableFactory.cancelAllKnownTasks();
    }

    @Override
    public void reloadPlugin(boolean full) {
        PluginCommon.reload(this, full);
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
            StaticHolder.saveInstance(listener.getClass(), getClass());
        }
    }

    @Override
    public void registerCommand(String name, CommandNode command) {
        getProxy().getPluginManager().registerCommand(this, new BungeeCommand(name, command));
        PluginCommon.saveCommandInstances(command, this.getClass());
    }

    protected boolean isNewVersionAvailable(String versionStringUrl) throws IOException {
        return Version.checkVersion(getVersion(), versionStringUrl);
    }

    @Override
    public boolean isReloading() {
        return reloading;
    }

    @Override
    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    @Override
    public RunnableFactory getRunnableFactory() {
        return runnableFactory;
    }

    @Override
    public PluginLogger getPluginLogger() {
        return logger;
    }
}
