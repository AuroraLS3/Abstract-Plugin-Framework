/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
import java.nio.file.Path;

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
 * @author AuroraLS3
 * @see IPlugin for method overview.
 */
public abstract class VelocityPlugin implements APFPlugin {

    protected final ProxyServer proxy;
    protected final PluginLogger logger;
    protected final CombineDebugLogger debugLogger;
    protected final DefaultErrorHandler errorHandler;
    protected final Timings timings;
    protected final RunnableFactory runnableFactory;
    private final Logger slf4jLogger;
    private final Path dataFolderPath;
    protected boolean reloading;

    /**
     * Standard constructor that initializes the plugin with the default DebugLogger.
     *
     * @param proxy          Injected ProxyServer.
     * @param slf4jLogger    Injected Logger.
     * @param dataFolderPath Injected Path to the plugin data folder.
     */
    public VelocityPlugin(ProxyServer proxy, Logger slf4jLogger, Path dataFolderPath) {
        this(proxy, slf4jLogger, dataFolderPath, new CombineDebugLogger(new MemoryDebugLogger()));
    }

    /**
     * Constructor for defining a debug logger at creation time.
     *
     * @param proxy          Injected ProxyServer.
     * @param slf4jLogger    Injected Logger.
     * @param dataFolderPath Injected Path to the plugin data folder.
     * @param debugLogger    debug logger to use.
     */
    public VelocityPlugin(ProxyServer proxy, Logger slf4jLogger, Path dataFolderPath, CombineDebugLogger debugLogger) {
        this.proxy = proxy;
        this.slf4jLogger = slf4jLogger;
        this.dataFolderPath = dataFolderPath;
        this.debugLogger = debugLogger;
        this.runnableFactory = new VelocityRunnableFactory(this, proxy.getScheduler());
        this.timings = new Timings(debugLogger);
        this.logger = new Slf4jPluginLogger(slf4jLogger, this::getDebugLogger);
        this.errorHandler = new DefaultErrorHandler(this, logger, new File(getDataFolder(), "logs"));
    }

    @Override
    public File getDataFolder() {
        return dataFolderPath.toFile();
    }

    /**
     * Implement this method by injecting ProxyServer in your plugin class.
     *
     * @return ProxyServer in use.
     */
    public ProxyServer getProxy() {
        return proxy;
    }

    /**
     * Implement this method by injecting slf4j.Logger in your plugin class.
     *
     * @return Logger of the plugin.
     */
    protected Logger getLogger() {
        return slf4jLogger;
    }

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