/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.task.sponge;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.PluginTask;
import com.djrapitops.plugin.task.RunnableFactory;
import org.spongepowered.api.scheduler.Task;

/**
 * PluginRunnable implementation for Sponge.
 *
 * @author Rsl1122
 */
public abstract class AbsSpongeRunnable<T extends IPlugin> implements PluginRunnable, Runnable {

    private final String name;
    private final long time;

    private T plugin;
    private RunnableFactory runnableFactory;

    private PluginTask task;

    public AbsSpongeRunnable(String name, IPlugin plugin, long time) {
        this.time = time;
        if (plugin instanceof SpongePlugin) {
            this.plugin = (T) plugin;
        } else {
            throw new IllegalArgumentException("Given plugin was not of correct type");
        }
        this.name = name;
        this.runnableFactory = plugin.getRunnableFactory();
    }

    @Override
    public String getTaskName() {
        return name;
    }

    @Override
    public void cancel() {
        if (plugin == null) {
            return;
        }
        try {
            if (task != null) {
                task.cancel();
            }
        } finally {
            // Clear instances so that cyclic references don't block GC
            plugin = null;
            runnableFactory = null;
        }
    }

    @Override
    public int getTaskId() {
        return -1;
    }

    @Override
    public PluginTask runTask() {
        task = new AbsSpongeTask(Task.builder().execute(this).submit(plugin));
        return this.task;
    }

    @Override
    public PluginTask runTaskAsynchronously() {
        task = new AbsSpongeTask(Task.builder().execute(this).async().submit(plugin));
        return this.task;
    }

    @Override
    public PluginTask runTaskLater(long delay) {
        task = new AbsSpongeTask(Task.builder().execute(this)
                .delayTicks(delay)
                .submit(plugin));
        return this.task;
    }

    @Override
    public PluginTask runTaskLaterAsynchronously(long delay) {
        task = new AbsSpongeTask(Task.builder().execute(this).async()
                .delayTicks(delay)
                .submit(plugin));
        return this.task;
    }

    @Override
    public PluginTask runTaskTimer(long delay, long period) {
        task = new AbsSpongeTask(Task.builder().execute(this)
                .delayTicks(delay)
                .intervalTicks(period)
                .submit(plugin));
        return this.task;
    }

    @Override
    public PluginTask runTaskTimerAsynchronously(long delay, long period) {
        task = new AbsSpongeTask(Task.builder().execute(this).async()
                .delayTicks(delay)
                .intervalTicks(period)
                .submit(plugin));
        return this.task;
    }

    @Override
    public long getTime() {
        return time;
    }
}