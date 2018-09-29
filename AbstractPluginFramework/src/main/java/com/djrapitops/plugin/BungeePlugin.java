package com.djrapitops.plugin;

import com.djrapitops.plugin.api.utility.Version;
import com.djrapitops.plugin.benchmarking.Timings;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.bungee.BungeeCommand;
import com.djrapitops.plugin.logging.console.BungeePluginLogger;
import com.djrapitops.plugin.logging.console.PluginLogger;
import com.djrapitops.plugin.logging.debug.CombineDebugLogger;
import com.djrapitops.plugin.logging.debug.DebugLogger;
import com.djrapitops.plugin.logging.debug.MemoryDebugLogger;
import com.djrapitops.plugin.logging.error.DefaultErrorHandler;
import com.djrapitops.plugin.logging.error.ErrorHandler;
import com.djrapitops.plugin.task.RunnableFactory;
import com.djrapitops.plugin.task.bungee.BungeeRunnableFactory;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;

import java.io.File;
import java.io.IOException;

/**
 * @author Rsl1122
 */
public abstract class BungeePlugin extends net.md_5.bungee.api.plugin.Plugin implements IPlugin {

    protected PluginLogger logger;
    protected final CombineDebugLogger debugLogger;
    protected final DefaultErrorHandler errorHandler;
    protected final Timings timings;
    protected final RunnableFactory runnableFactory;

    protected boolean reloading;

    public BungeePlugin() {
        this(new CombineDebugLogger(new MemoryDebugLogger()));
    }

    public BungeePlugin(CombineDebugLogger debugLogger) {
        this.debugLogger = debugLogger;
        runnableFactory = new BungeeRunnableFactory(this);
        timings = new Timings(debugLogger);
        logger = new BungeePluginLogger(
                message -> getProxy().getConsole().sendMessage(new TextComponent(message)),
                this::getDebugLogger,
                getLogger()
        );
        errorHandler = new DefaultErrorHandler(logger, new File(getDataFolder(), "logs"));
    }

    @Override
    public void onDisable() {
        Class<? extends IPlugin> pluginClass = getClass();
        runnableFactory.cancelAllKnownTasks();
    }

    @Override
    public void reloadPlugin(boolean full) {
        PluginCommon.reload(this, full);
    }

    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            if (listener == null) {
                logger.warn("Attempted to register a null listener!");
                continue;
            }
            getProxy().getPluginManager().registerListener(this, listener);
        }
    }

    @Override
    public void registerCommand(String name, CommandNode command) {
        if (command == null) {
            logger.warn("Attempted to register a null command for name '" + name + "'!");
            return;
        }
        getProxy().getPluginManager().registerCommand(this, new BungeeCommand(name, command));
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

    @Override
    public DebugLogger getDebugLogger() {
        return debugLogger;
    }

    @Override
    public void setDebugLoggers(DebugLogger... loggers) {
        debugLogger.setDebugLoggers(loggers);
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    @Override
    public void setErrorHandlers(ErrorHandler... errorHandlers) {
        errorHandler.setErrorHandlers(errorHandlers);
    }

    @Override
    public Timings getTimings() {
        return timings;
    }

    @Override
    public void onReload() {
        // No implementation, override to be called on reload.
    }
}
