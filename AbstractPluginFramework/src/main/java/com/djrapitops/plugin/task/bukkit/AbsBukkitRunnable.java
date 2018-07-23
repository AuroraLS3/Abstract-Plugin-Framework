/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task.bukkit;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.PluginTask;
import com.djrapitops.plugin.task.RunnableFactory;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @param <T>
 * @author Rsl1122
 */
public abstract class AbsBukkitRunnable<T extends BukkitPlugin> extends BukkitRunnable implements PluginRunnable, Runnable {

    private final String name;
    private final long time;
    private int id = -1;

    private T plugin;
    private RunnableFactory runnableFactory;

    public AbsBukkitRunnable(String name, IPlugin plugin, long time) {
        this.name = name;
        this.time = time;
        if (plugin instanceof BukkitPlugin) {
            this.plugin = (T) plugin;
        } else {
            throw new IllegalArgumentException("Given plugin was not of correct type");
        }
        runnableFactory = plugin.getRunnableFactory();
    }

    @Override
    public abstract void run();

    @Override
    public PluginTask runTask() {
        AbsBukkitTask task = new AbsBukkitTask(super.runTask(plugin));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskAsynchronously() {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskAsynchronously(plugin));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskLater(long delay) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskLater(plugin, delay));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskLaterAsynchronously(long delay) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskLaterAsynchronously(plugin, delay));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskTimer(long delay, long period) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskTimer(plugin, delay, period));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskTimerAsynchronously(long delay, long period) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskTimerAsynchronously(plugin, delay, period));
        id = task.getTaskId();
        return task;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        if (plugin == null) {
            return;
        }
        try {
            runnableFactory.cancelled(this);
            plugin.getServer().getScheduler().cancelTask(id);
            super.cancel();
        } finally {
            // Clear instances so that cyclic references don't block GC
            runnableFactory = null;
            plugin = null;
        }
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
