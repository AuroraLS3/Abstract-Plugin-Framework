package com.djrapitops.plugin;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.utility.Version;
import com.djrapitops.plugin.api.utility.log.DebugLog;
import com.djrapitops.plugin.benchmarking.Timings;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.bukkit.BukkitCommand;
import com.djrapitops.plugin.logging.console.BukkitPluginLogger;
import com.djrapitops.plugin.logging.console.PluginLogger;
import com.djrapitops.plugin.logging.debug.CombineDebugLogger;
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

/**
 * @author Rsl1122
 */
public abstract class BukkitPlugin extends JavaPlugin implements IPlugin {

    protected PluginLogger logger;
    protected CombineDebugLogger debugLogger;
    protected ErrorHandler errorHandler;
    protected final Timings timings;
    protected final RunnableFactory runnableFactory;

    public BukkitPlugin() {
        this(new CombineDebugLogger(new MemoryDebugLogger()));
    }

    public BukkitPlugin(CombineDebugLogger debugLogger) {
        this.debugLogger = debugLogger;
        runnableFactory = new BukkitRunnableFactory(this);
        timings = new Timings(debugLogger);
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

    @Override
    public DebugLogger getDebugLogger() {
        return debugLogger;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    @Override
    public Timings getTimings() {
        return timings;
    }

    @Override
    public void setDebugLoggers(DebugLogger... loggers) {
        debugLogger.setDebugLoggers(loggers);
    }

    @Override
    public void onReload() {
        // No implementation, override to be called on reload.
    }
}