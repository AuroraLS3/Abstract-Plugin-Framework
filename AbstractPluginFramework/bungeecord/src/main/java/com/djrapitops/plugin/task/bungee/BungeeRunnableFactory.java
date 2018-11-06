package com.djrapitops.plugin.task.bungee;

import com.djrapitops.plugin.BungeePlugin;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

/**
 * {@link RunnableFactory} implementation for Bungee.
 *
 * @author Rsl1122
 */
public class BungeeRunnableFactory extends RunnableFactory {

    private final BungeePlugin plugin;

    /**
     * Create a new BungeeRunnableFactory.
     *
     * @param plugin BungeePlugin this factory is for.
     */
    public BungeeRunnableFactory(BungeePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time) {
        return new AbsBungeeRunnable(name, plugin, time) {
            @Override
            public void run() {
                setCancellable(runnable, this);
                runnable.run();
            }
        };
    }

    @Override
    public void cancelAllKnownTasks() {
        plugin.getProxy().getScheduler().cancel(plugin);
    }
}
