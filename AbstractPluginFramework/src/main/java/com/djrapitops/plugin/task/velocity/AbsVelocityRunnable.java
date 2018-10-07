/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.task.velocity;

import com.djrapitops.plugin.VelocityPlugin;
import com.djrapitops.plugin.api.TimeAmount;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.PluginTask;
import com.velocitypowered.api.scheduler.Scheduler;

import java.util.concurrent.TimeUnit;

/**
 * {@link PluginRunnable} implementation for Velocity.
 *
 * @author Rsl1122
 */
abstract class AbsVelocityRunnable implements PluginRunnable, Runnable {

    private final String name;
    private final long time;

    private VelocityPlugin plugin;
    private Scheduler scheduler;

    private AbsVelocityTask task;

    AbsVelocityRunnable(String name, VelocityPlugin plugin, Scheduler scheduler, long time) {
        this.time = time;
        this.scheduler = scheduler;
        this.plugin = plugin;
        this.name = name;
    }

    AbsVelocityTask getTask() {
        return task;
    }

    @Override
    public String getTaskName() {
        return name;
    }

    @Override
    public void cancel() {
        if (scheduler == null) {
            return;
        }
        try {
            if (task != null) {
                task.cancel();
            }
        } finally {
            // Clear instances so that cyclic references don't block GC
            scheduler = null;
        }
    }

    @Override
    public int getTaskId() {
        return -1;
    }

    @Override
    public PluginTask runTask() {
        return runTaskAsynchronously();
    }

    @Override
    public PluginTask runTaskAsynchronously() {
        task = new AbsVelocityTask(scheduler.buildTask(plugin, this).schedule());
        return this.task;
    }

    @Override
    public PluginTask runTaskLater(long delay) {
        return runTaskLaterAsynchronously(delay);
    }

    @Override
    public PluginTask runTaskLaterAsynchronously(long delay) {
        task = new AbsVelocityTask(scheduler.buildTask(plugin, this)
                .delay(TimeAmount.ticksToMillis(delay), TimeUnit.MILLISECONDS)
                .schedule());
        return this.task;
    }

    @Override
    public PluginTask runTaskTimer(long delay, long period) {
        return runTaskTimerAsynchronously(delay, period);
    }

    @Override
    public PluginTask runTaskTimerAsynchronously(long delay, long period) {
        task = new AbsVelocityTask(scheduler.buildTask(plugin, this)
                .delay(TimeAmount.ticksToMillis(delay), TimeUnit.MILLISECONDS)
                .repeat(TimeAmount.ticksToMillis(delay), TimeUnit.MILLISECONDS)
                .schedule());
        return this.task;
    }

    @Override
    public long getTime() {
        return time;
    }
}