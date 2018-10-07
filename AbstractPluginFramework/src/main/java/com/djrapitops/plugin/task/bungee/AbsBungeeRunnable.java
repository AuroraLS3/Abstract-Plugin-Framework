/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public abstract void run();

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
    public synchronized void cancel() throws IllegalStateException {
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
