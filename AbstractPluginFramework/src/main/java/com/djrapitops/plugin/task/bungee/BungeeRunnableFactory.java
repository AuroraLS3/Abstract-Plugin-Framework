package com.djrapitops.plugin.task.bungee;

import com.djrapitops.plugin.BungeePlugin;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

public class BungeeRunnableFactory extends RunnableFactory {

    private final BungeePlugin plugin;

    public BungeeRunnableFactory(BungeePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time) {
        return new AbsBungeeRunnable(name, plugin, time) {
            @Override
            public void run() {
                runnable.setCancellable(this);
                runnable.run();
            }
        };
    }
}
