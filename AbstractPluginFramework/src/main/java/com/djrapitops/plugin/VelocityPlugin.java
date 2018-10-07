package com.djrapitops.plugin;

import com.djrapitops.plugin.benchmarking.Timings;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.velocity.VelocityCommand;
import com.djrapitops.plugin.logging.console.PluginLogger;
import com.djrapitops.plugin.logging.console.Slf4jPluginLogger;
import com.djrapitops.plugin.logging.debug.CombineDebugLogger;
import com.djrapitops.plugin.logging.debug.DebugLogger;
import com.djrapitops.plugin.logging.debug.MemoryDebugLogger;
import com.djrapitops.plugin.logging.error.DefaultErrorHandler;
import com.djrapitops.plugin.logging.error.ErrorHandler;
import com.djrapitops.plugin.task.RunnableFactory;
import com.djrapitops.plugin.task.velocity.VelocityRunnableFactory;
import com.djrapitops.plugin.utilities.Verify;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.io.File;

/**
 * {@link IPlugin} implementation for Velocity.
 * <p>
 * This class should be extended when creating a velocity part of your plugin using this library.
 * It provides instances for a {@link PluginLogger}, {@link DebugLogger}, {@link ErrorHandler}, {@link Timings} and {@link RunnableFactory}.
 * <p>
 * If you wish to change default debug or error handling behavior, use
 * {@link IPlugin#setDebugLoggers}
 * {@link IPlugin#setErrorHandlers}
 *
 * @author Rsl1122
 * @see IPlugin for method overview.
 */
public abstract class VelocityPlugin implements APFPlugin {

    protected final PluginLogger logger;
    protected final CombineDebugLogger debugLogger;
    protected final DefaultErrorHandler errorHandler;
    protected final Timings timings;
    protected final RunnableFactory runnableFactory;

    protected boolean reloading;

    /**
     * Standard constructor that initializes the plugin with the default DebugLogger.
     */
    public VelocityPlugin() {
        this(new CombineDebugLogger(new MemoryDebugLogger()));
    }

    /**
     * Constructor for defining a debug logger at creation time.
     *
     * @param debugLogger debug logger to use.
     */
    public VelocityPlugin(CombineDebugLogger debugLogger) {
        this.debugLogger = debugLogger;
        this.runnableFactory = new VelocityRunnableFactory(this, getProxy().getScheduler());
        this.timings = new Timings(debugLogger);
        this.logger = new Slf4jPluginLogger(getLogger(), this::getDebugLogger);
        this.errorHandler = new DefaultErrorHandler(this, logger, new File(getDataFolder(), "logs"));
    }

    /**
     * Implement this method by injecting ProxyServer in your plugin class.
     *
     * @return ProxyServer in use.
     */
    protected abstract ProxyServer getProxy();

    /**
     * Implement this method by injecting slf4j.Logger in your plugin class.
     *
     * @return Logger of the plugin.
     */
    protected abstract Logger getLogger();

    @Override
    public void onDisable() {
        runnableFactory.cancelAllKnownTasks();
    }

    @Override
    public void onReload() {

    }

    @Override
    public void reloadPlugin(boolean full) {
        PluginCommon.reload(this, full);
    }

    public void registerListener(Object... listeners) {
        for (Object listener : listeners) {
            getProxy().getEventManager().register(this, listener);
        }
    }

    @Override
    public void registerCommand(String name, CommandNode command) {
        getProxy().getCommandManager().register(new VelocityCommand(command), name);
    }

    @Override
    public String getVersion() {
        Plugin annotation = getClass().getAnnotation(Plugin.class);
        return Verify.nullCheck(
                annotation,
                () -> new IllegalStateException(getClass().getName() + " does not have required @Plugin annotation.")
        ).version();
    }

    @Override
    public CombineDebugLogger getDebugLogger() {
        return debugLogger;
    }

    @Override
    public DefaultErrorHandler getErrorHandler() {
        return errorHandler;
    }

    @Override
    public Timings getTimings() {
        return timings;
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
    public boolean isReloading() {
        return reloading;
    }

    @Override
    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    @Override
    public void setDebugLoggers(DebugLogger... loggers) {
        debugLogger.setDebugLoggers(loggers);
    }

    @Override
    public void setErrorHandlers(ErrorHandler... errorHandlers) {
        errorHandler.setErrorHandlers(errorHandlers);
    }
}