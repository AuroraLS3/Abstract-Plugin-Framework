/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.utility.log.DebugLog;
import com.djrapitops.plugin.benchmarking.Timings;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.sponge.SpongeCommand;
import com.djrapitops.plugin.logging.console.PluginLogger;
import com.djrapitops.plugin.logging.console.SpongePluginLogger;
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
    protected DebugLogger debugLogger;
    protected ErrorHandler errorHandler;
    protected final Timings timings;
    protected final RunnableFactory runnableFactory;

    public SpongePlugin() {
        this(new MemoryDebugLogger());
    }

    public SpongePlugin(DebugLogger debugLogger) {
        this.debugLogger = debugLogger;
        runnableFactory = new SpongeRunnableFactory(this);
        timings = new Timings();
        logger = new SpongePluginLogger(this, this::getDebugLogger);
        errorHandler = new DefaultErrorHandler(logger, new File(getDataFolder(), "logs"));
    }

    protected boolean reloading;

    private final Map<String, CommandMapping> commandMappings = new HashMap<>();

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

    public abstract Logger getLogger();

    @Override
    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    @Override
    public void registerCommand(String name, CommandNode command) {
        CommandManager commandManager = Sponge.getCommandManager();

        CommandMapping registered = commandMappings.get(name);
        if (registered != null) {
            commandManager.removeMapping(registered);
        }

        Optional<CommandMapping> register = commandManager.register(this, new SpongeCommand(command), name);
        register.ifPresent(commandMapping -> commandMappings.put(name, commandMapping));
        PluginCommon.saveCommandInstances(command, this.getClass());
    }

    public void registerListener(Object... listeners) {
        for (Object listener : listeners) {
            Sponge.getEventManager().registerListeners(this, listener);
            StaticHolder.saveInstance(listener.getClass(), getClass());
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

    private DebugLogger getDebugLogger() {
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
}