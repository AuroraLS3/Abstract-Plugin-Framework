package com.djrapitops.plugin;

import com.djrapitops.plugin.api.utility.Version;
import com.djrapitops.plugin.benchmarking.Timings;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.bukkit.BukkitCommand;
import com.djrapitops.plugin.logging.console.JavaUtilPluginLogger;
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
 * {@link IPlugin} implementation for Bukkit.
 * <p>
 * This class should be extended when creating a bukkit part of your plugin using this library.
 * It provides instances for a {@link PluginLogger}, {@link DebugLogger}, {@link ErrorHandler}, {@link Timings} and {@link RunnableFactory}.
 * <p>
 * If you wish to change default debug or error handling behavior, use
 * {@link IPlugin#setDebugLoggers}
 * {@link IPlugin#setErrorHandlers}
 *
 * @author Rsl1122
 * @see IPlugin for method overview.
 */
public abstract class BukkitPlugin extends JavaPlugin implements APFPlugin {

    protected PluginLogger logger;
    protected final CombineDebugLogger debugLogger;
    protected final DefaultErrorHandler errorHandler;
    protected final Timings timings;
    protected final RunnableFactory runnableFactory;

    /**
     * Standard constructor that initializes the plugin with the default DebugLogger.
     */
    public BukkitPlugin() {
        this(new CombineDebugLogger(new MemoryDebugLogger()));
    }

    /**
     * Constructor for defining a debug logger at creation time.
     *
     * @param debugLogger debug logger to use.
     */
    public BukkitPlugin(CombineDebugLogger debugLogger) {
        this.debugLogger = debugLogger;
        this.runnableFactory = new BukkitRunnableFactory(this);
        this.timings = new Timings(debugLogger);
        this.logger = new JavaUtilPluginLogger(
                message -> getServer().getConsoleSender().sendMessage(message),
                this::getDebugLogger,
                getLogger()
        );
        this.errorHandler = new DefaultErrorHandler(this, logger, new File(getDataFolder(), "logs"));
    }

    protected boolean reloading;

    @Override
    public void onDisable() {
        runnableFactory.cancelAllKnownTasks();
    }

    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            if (listener == null) {
                logger.warn("Attempted to register a null listener!");
                continue;
            }
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void registerCommand(String name, CommandNode command) {
        if (command == null) {
            logger.warn("Attempted to register a null command for name '" + name + "'!");
            return;
        }
        getCommand(name).setExecutor(new BukkitCommand(command));
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
    public void setErrorHandlers(ErrorHandler... errorHandlers) {
        errorHandler.setErrorHandlers(errorHandlers);
    }

    @Override
    public void onReload() {
        // No implementation, override to be called on reload.
    }
}