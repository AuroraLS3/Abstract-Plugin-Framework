/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
 * @author AuroraLS3
 */
abstract class AbsVelocityRunnable implements PluginRunnable, Runnable {

    private final String name;
    private final long time;

    private final VelocityPlugin plugin;
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