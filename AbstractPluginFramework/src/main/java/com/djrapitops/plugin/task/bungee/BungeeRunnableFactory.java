package com.djrapitops.plugin.task.bungee;

import com.djrapitops.plugin.BungeePlugin;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

public class BungeeRunnableFactory implements RunnableFactory {

    private final BungeePlugin plugin;

    public BungeeRunnableFactory(BungeePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public PluginRunnable createNew(String name, AbsRunnable runnable) {
        return new AbsBungeeRunnable(name, plugin) {
            @Override
            public void run() {
                runnable.setCancellable(this);
                runnable.run();
            }
        };
    }
}
