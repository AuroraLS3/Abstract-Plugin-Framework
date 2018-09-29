/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin;

import com.djrapitops.plugin.benchmarking.Timings;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.sponge.SpongeCommand;
import com.djrapitops.plugin.logging.console.PluginLogger;
import com.djrapitops.plugin.logging.console.SpongePluginLogger;
import com.djrapitops.plugin.logging.debug.CombineDebugLogger;
import com.djrapitops.plugin.logging.debug.DebugLogger;
import com.djrapitops.plugin.logging.debug.MemoryDebugLogger;
import com.djrapitops.plugin.logging.error.DefaultErrorHandler;
import com.djrapitops.plugin.logging.error.ErrorHandler;
import com.djrapitops.plugin.task.RunnableFactory;
import com.djrapitops.plugin.task.sponge.SpongeRunnableFactory;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.CommandMapping;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * IPlugin implementation for Sponge.
 *
 * @author Rsl1122
 */
public abstract class SpongePlugin implements IPlugin {

    protected final PluginLogger logger;
    protected final CombineDebugLogger debugLogger;
    protected final DefaultErrorHandler errorHandler;
    protected final Timings timings;
    protected final RunnableFactory runnableFactory;

    public SpongePlugin() {
        this(new CombineDebugLogger(new MemoryDebugLogger()));
    }

    public SpongePlugin(CombineDebugLogger debugLogger) {
        this.debugLogger = debugLogger;
        runnableFactory = new SpongeRunnableFactory(this);
        timings = new Timings(debugLogger);
        logger = new SpongePluginLogger(this, this::getDebugLogger);
        errorHandler = new DefaultErrorHandler(logger, new File(getDataFolder(), "logs"));
    }

    protected boolean reloading;

    private final Map<String, CommandMapping> commandMappings = new HashMap<>();

    @Override
    public void onDisable() {
        Class<? extends IPlugin> pluginClass = getClass();
        runnableFactory.cancelAllKnownTasks();
    }

    @Override
    public void reloadPlugin(boolean full) {
        PluginCommon.reload(this, full);
    }

    public abstract Logger getLogger();

    @Override
    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    @Override
    public void registerCommand(String name, CommandNode command) {
        if (command == null) {
            logger.warn("Attempted to register a null command for name '" + name + "'!");
            return;
        }

        CommandManager commandManager = Sponge.getCommandManager();

        CommandMapping registered = commandMappings.get(name);
        if (registered != null) {
            commandManager.removeMapping(registered);
        }

        Optional<CommandMapping> register = commandManager.register(this, new SpongeCommand(command), name);
        register.ifPresent(commandMapping -> commandMappings.put(name, commandMapping));
    }

    public void registerListener(Object... listeners) {
        for (Object listener : listeners) {
            if (listener == null) {
                logger.warn("Attempted to register a null listener!");
                continue;
            }
            Sponge.getEventManager().registerListeners(this, listener);
        }
    }

    @Listener
    public void reload(GameReloadEvent event) {
        reloadPlugin(true);
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

    @Override
    public boolean isReloading() {
        return reloading;
    }
}