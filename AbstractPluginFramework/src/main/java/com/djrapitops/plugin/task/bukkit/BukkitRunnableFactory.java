package com.djrapitops.plugin.task.bukkit;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

/**
 * {@link RunnableFactory} implementation for Bukkit.
 *
 * @author Rsl1122
 */
public class BukkitRunnableFactory extends RunnableFactory {

    private final BukkitPlugin plugin;

    /**
     * Create a new BukkitRunnableFactory.
     *
     * @param plugin BukkitPlugin this factory is for.
     */
    public BukkitRunnableFactory(BukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time) {
        return new AbsBukkitRunnable(name, plugin, time) {
            @Override
            public void run() {
                setCancellable(runnable, this);
                runnable.run();
            }
        };
    }

    @Override
    public void cancelAllKnownTasks() {
        plugin.getServer().getScheduler().cancelTasks(plugin);
    }
}
