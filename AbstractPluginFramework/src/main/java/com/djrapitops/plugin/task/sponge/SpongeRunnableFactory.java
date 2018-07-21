package com.djrapitops.plugin.task.sponge;

import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

public class SpongeRunnableFactory implements RunnableFactory {

    private final SpongePlugin plugin;

    public SpongeRunnableFactory(SpongePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public PluginRunnable createNew(String name, AbsRunnable runnable) {
        return new AbsSpongeRunnable(name, plugin) {
            @Override
            public void run() {
                runnable.setCancellable(this);
                runnable.run();
            }
        };
    }
}
