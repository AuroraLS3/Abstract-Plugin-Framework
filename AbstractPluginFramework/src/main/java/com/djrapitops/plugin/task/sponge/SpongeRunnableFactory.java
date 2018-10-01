package com.djrapitops.plugin.task.sponge;

import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

/**
 * {@link RunnableFactory} implementation for Sponge.
 *
 * @author Rsl1122
 */
public class SpongeRunnableFactory extends RunnableFactory {

    private final SpongePlugin plugin;

    /**
     * Create new SpongeRunnableFactory.
     *
     * @param plugin SpongePlugin this factory is for.
     */
    public SpongeRunnableFactory(SpongePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time) {
        return new AbsSpongeRunnable(name, plugin, time) {
            @Override
            public void run() {
                setCancellable(runnable, this);
                runnable.run();
            }
        };
    }

    @Override
    public void cancelAllKnownTasks() {
        Sponge.getScheduler().getScheduledTasks(plugin).forEach(Task::cancel);
    }
}
