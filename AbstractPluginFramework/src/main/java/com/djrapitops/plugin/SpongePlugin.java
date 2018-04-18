/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin;

import com.djrapitops.plugin.api.Benchmark;
import com.djrapitops.plugin.api.utility.log.DebugLog;
import com.djrapitops.plugin.command.CommandNode;
import com.djrapitops.plugin.command.sponge.SpongeCommand;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.CommandMapping;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * IPlugin implementation for Sponge.
 *
 * @author Rsl1122
 */
public abstract class SpongePlugin implements IPlugin {

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
            case "INFO_COLOR":
                logger.info(s);
                break;
            case "W":
            case "WARN":
            case "WARNING":
                logger.warn(s);
                break;
            case "E":
            case "ERR":
            case "ERROR":
            case "SEVERE":
                logger.error(s);
                break;
            default:
                logger.info(s);
                break;
        }
    }

    protected abstract Logger getLogger();

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

    public static void sendPlayerMsg(Player player, String msg) {
        player.sendMessage(Text.of(msg));
    }
}