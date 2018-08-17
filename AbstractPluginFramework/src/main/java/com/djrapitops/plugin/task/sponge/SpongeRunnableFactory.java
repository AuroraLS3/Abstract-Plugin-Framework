package com.djrapitops.plugin.task.sponge;

import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

public class SpongeRunnableFactory extends RunnableFactory {

    private final SpongePlugin plugin;

    public SpongeRunnableFactory(SpongePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time) {
        return new AbsSpongeRunnable(name, plugin, time) {
            @Override
            public void run() {
                runnable.setCancellable(this);
                runnable.run();
            }
        };
    }
}
