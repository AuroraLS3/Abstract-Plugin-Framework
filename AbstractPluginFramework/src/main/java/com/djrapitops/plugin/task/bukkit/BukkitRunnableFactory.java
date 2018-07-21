package com.djrapitops.plugin.task.bukkit;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

public class BukkitRunnableFactory implements RunnableFactory {

    private final BukkitPlugin plugin;

    public BukkitRunnableFactory(BukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public PluginRunnable createNew(String name, AbsRunnable runnable) {
        return new AbsBukkitRunnable(name, plugin) {
            @Override
            public void run() {
                runnable.setCancellable(this);
                runnable.run();
            }
        };
    }
}
