/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.task.sponge;

import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.PluginTask;
import org.spongepowered.api.scheduler.Task;

/**
 * {@link PluginRunnable} implementation for Sponge.
 *
 * @author Rsl1122
 */
abstract class AbsSpongeRunnable implements PluginRunnable, Runnable {

    private final String name;
    private final long time;

    private SpongePlugin plugin;

    private PluginTask task;

    AbsSpongeRunnable(String name, SpongePlugin plugin, long time) {
        this.time = time;
        this.plugin = plugin;
        this.name = name;
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