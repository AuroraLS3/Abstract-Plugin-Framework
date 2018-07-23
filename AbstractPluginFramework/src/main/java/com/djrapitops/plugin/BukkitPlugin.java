package com.djrapitops.plugin;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.utility.Version;
import com.djrapitops.plugin.api.utility.log.DebugLog;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.bukkit.BukkitCommand;
import com.djrapitops.plugin.logging.console.BukkitPluginLogger;
import com.djrapitops.plugin.logging.console.PluginLogger;
import com.djrapitops.plugin.logging.debug.DebugLogger;
import com.djrapitops.plugin.logging.debug.MemoryDebugLogger;
import com.djrapitops.plugin.logging.error.DefaultErrorHandler;
import com.djrapitops.plugin.logging.error.ErrorHandler;
import com.djrapitops.plugin.task.RunnableFactory;
import com.djrapitops.plugin.task.bukkit.BukkitRunnableFactory;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Rsl1122
 */
public abstract class BukkitPlugin extends JavaPlugin implements IPlugin {

    protected PluginLogger logger;
    protected DebugLogger debugLogger;
    protected ErrorHandler errorHandler;
    protected final RunnableFactory runnableFactory;

    public BukkitPlugin() {
        this(new MemoryDebugLogger());
    }

    public BukkitPlugin(DebugLogger debugLogger) {
        this.debugLogger = debugLogger;
        runnableFactory = new BukkitRunnableFactory(this);
        logger = new BukkitPluginLogger(
                message -> getServer().getConsoleSender().sendMessage(message),
                this::getDebugLogger,
                getLogger()
        );
        errorHandler = new DefaultErrorHandler(logger, new File(getDataFolder(), "logs"));
    }

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
        StaticHolder.unRegister(pluginClass);
        runnableFactory.cancelAllKnownTasks();
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

    @Override
    public void registerCommand(String name, CommandNode command) {
        getCommand(name).setExecutor(new BukkitCommand(command));
        PluginCommon.saveCommandInstances(command, this.getClass());
    }

    protected boolean isNewVersionAvailable(String versionStringUrl) throws IOException {
        return Version.checkVersion(getVersion(), versionStringUrl);
    }

    @Override
    public void reloadPlugin(boolean full) {
        PluginCommon.reload(this, full);
    }

    @Override
    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    @Override
    public boolean isReloading() {
        return reloading;
    }

    public RunnableFactory getRunnableFactory() {
        return runnableFactory;
    }

    @Override
    public PluginLogger getPluginLogger() {
        return logger;
    }

    private DebugLogger getDebugLogger() {
        return debugLogger;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }
}