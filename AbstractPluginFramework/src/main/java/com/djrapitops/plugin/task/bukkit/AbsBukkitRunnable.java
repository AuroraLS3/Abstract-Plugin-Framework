/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task.bukkit;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.PluginTask;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * {@link PluginRunnable} implementation for Bukkit.
 *
 * @author Rsl1122
 */
public abstract class AbsBukkitRunnable extends BukkitRunnable implements PluginRunnable, Runnable {

    private final String name;
    private final long time;
    private int id = -1;

    private BukkitPlugin plugin;

    AbsBukkitRunnable(String name, BukkitPlugin plugin, long time) {
        this.name = name;
        this.time = time;
        this.plugin = plugin;
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
    public synchronized void cancel() {
        if (plugin == null) {
            return;
        }
        try {
            plugin.getServer().getScheduler().cancelTask(id);
            super.cancel();
        } finally {
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
