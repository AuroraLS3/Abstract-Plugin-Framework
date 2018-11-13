/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
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
package com.djrapitops.plugin.task.bungee;

import com.djrapitops.plugin.BungeePlugin;
import com.djrapitops.plugin.api.TimeAmount;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.PluginTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.util.concurrent.TimeUnit;

/**
 * {@link PluginRunnable} implementation for Bungee.
 *
 * @author Rsl1122
 */
public abstract class AbsBungeeRunnable implements PluginRunnable, Runnable {

    private final String name;
    private final long time;

    private BungeePlugin plugin;
    private TaskScheduler scheduler;
    private int id = -1;

    AbsBungeeRunnable(String name, BungeePlugin plugin, long time) {
        this.name = name;
        this.time = time;
        this.plugin = plugin;
        this.scheduler = plugin.getProxy().getScheduler();
    }

    @Override
    public PluginTask runTask() {
        return runTaskAsynchronously();
    }

    @Override
    public PluginTask runTaskAsynchronously() {
        AbsBungeeTask task = new AbsBungeeTask(scheduler.runAsync(plugin, this));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskLater(long delay) {
        return runTaskLaterAsynchronously(delay);
    }

    @Override
    public PluginTask runTaskLaterAsynchronously(long delay) {
        AbsBungeeTask task = new AbsBungeeTask(scheduler.schedule(plugin, this, TimeAmount.ticksToMillis(delay), TimeUnit.MILLISECONDS));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskTimer(long delay, long period) {
        return runTaskTimerAsynchronously(delay, period);
    }

    @Override
    public PluginTask runTaskTimerAsynchronously(long delay, long period) {
        AbsBungeeTask task = new AbsBungeeTask(scheduler.schedule(plugin, this, TimeAmount.ticksToMillis(delay), TimeAmount.ticksToMillis(period), TimeUnit.MILLISECONDS));
        id = task.getTaskId();
        return task;
    }

    @Override
    public synchronized void cancel() {
        if (plugin == null) {
            return;
        }
        try {
            scheduler.cancel(id);
        } finally {
            // Clear instances so that cyclic references don't block GC
            plugin = null;
            scheduler = null;
        }
    }

    @Override
    public int getTaskId() {
        return id;
    }

    @Override
    public String getTaskName() {
        return name;
    }

    @Override
    public long getTime() {
        return time;
    }
}
