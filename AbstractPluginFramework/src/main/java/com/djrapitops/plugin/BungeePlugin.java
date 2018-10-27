package com.djrapitops.plugin;

import com.djrapitops.plugin.api.utility.Version;
import com.djrapitops.plugin.benchmarking.Timings;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.bungee.BungeeCommand;
import com.djrapitops.plugin.logging.console.JavaUtilPluginLogger;
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
 * {@link IPlugin} implementation for Bungee.
 * <p>
 * This class should be extended when creating a bungee part of your plugin using this library.
 * It provides instances for a {@link PluginLogger}, {@link DebugLogger}, {@link ErrorHandler}, {@link Timings} and {@link RunnableFactory}.
 * <p>
 * If you wish to change default debug or error handling behavior, use
 * {@link IPlugin#setDebugLoggers}
 * {@link IPlugin#setErrorHandlers}
 *
 * @author Rsl1122
 * @see IPlugin for method overview.
 */
public abstract class BungeePlugin extends net.md_5.bungee.api.plugin.Plugin implements APFPlugin {

    protected PluginLogger logger;
    protected final CombineDebugLogger debugLogger;
    protected final DefaultErrorHandler errorHandler;
    protected final Timings timings;
    protected final RunnableFactory runnableFactory;

    protected boolean reloading;

    /**
     * Standard constructor that initializes the plugin with the default DebugLogger.
     */
    public BungeePlugin() {
        this(new CombineDebugLogger(new MemoryDebugLogger()));
    }

    /**
     * Constructor for defining a debug logger at creation time.
     *
     * @param debugLogger debug logger to use.
     */
    public BungeePlugin(CombineDebugLogger debugLogger) {
        this.debugLogger = debugLogger;
        this.runnableFactory = new BungeeRunnableFactory(this);
        this.timings = new Timings(debugLogger);
        this.logger = new JavaUtilPluginLogger(
                message -> getProxy().getConsole().sendMessage(new TextComponent(message)),
                this::getDebugLogger,
                this::getLogger
        );
        this.errorHandler = new DefaultErrorHandler(this, logger, () -> new File(getDataFolder(), "logs"));
    }

    @Override
    public void onDisable() {
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
