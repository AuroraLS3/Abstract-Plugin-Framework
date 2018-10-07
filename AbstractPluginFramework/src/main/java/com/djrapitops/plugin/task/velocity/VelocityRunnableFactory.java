package com.djrapitops.plugin.task.velocity;

import com.djrapitops.plugin.VelocityPlugin;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;
import com.velocitypowered.api.scheduler.Scheduler;
import io.netty.util.internal.ConcurrentSet;

/**
 * {@link RunnableFactory} implementation for Velocity.
 *
 * @author Rsl1122
 */
public class VelocityRunnableFactory extends RunnableFactory {

    private final VelocityPlugin plugin;
    private final Scheduler scheduler;

    private final ConcurrentSet<AbsVelocityRunnable> tasks;

    public VelocityRunnableFactory(VelocityPlugin plugin, Scheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;

        tasks = new ConcurrentSet<>();
    }

    @Override
    protected PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time) {
        cleanUp();

        return new AbsVelocityRunnable(name, plugin, scheduler, time) {
            @Override
            public void run() {
                setCancellable(runnable, this);
                runnable.run();
            }
        };
    }

    private void cleanUp() {
        tasks.removeIf(runnable -> runnable.getTask().isFinished());
    }

    @Override
    public void cancelAllKnownTasks() {
        tasks.forEach(PluginRunnable::cancel);
        tasks.clear();
    }
}