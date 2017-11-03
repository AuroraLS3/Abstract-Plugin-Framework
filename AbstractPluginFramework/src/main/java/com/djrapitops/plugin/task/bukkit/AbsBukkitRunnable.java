/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task.bukkit;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.task.IRunnable;
import com.djrapitops.plugin.task.ITask;
import com.djrapitops.plugin.api.systems.TaskCenter;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @param <T>
 * @author Rsl1122
 */
public abstract class AbsBukkitRunnable<T extends BukkitPlugin> extends BukkitRunnable implements IRunnable, Runnable {

    private final T plugin;
    private final String name;
    private int id = -1;

    public AbsBukkitRunnable(String name, IPlugin plugin) {
        this.name = name;
        if (plugin instanceof BukkitPlugin) {
            this.plugin = (T) plugin;
        } else {
            throw new IllegalArgumentException("Given plugin was not of correct type");
        }
    }

    @Override
    public abstract void run();

    @Override
    public ITask runTask() {
        AbsBukkitTask task = new AbsBukkitTask(super.runTask(plugin));
        id = task.getTaskId();
        TaskCenter.taskStarted(plugin.getClass(), task, name, this);
        return task;
    }

    @Override
    public ITask runTaskAsynchronously() {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskAsynchronously(plugin));
        id = task.getTaskId();
        TaskCenter.taskStarted(plugin.getClass(), task, name, this);
        return task;
    }

    @Override
    public ITask runTaskLater(long delay) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskLater(plugin, delay));
        id = task.getTaskId();
        TaskCenter.taskStarted(plugin.getClass(), task, name, this);
        return task;
    }

    @Override
    public ITask runTaskLaterAsynchronously(long delay) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskLaterAsynchronously(plugin, delay));
        id = task.getTaskId();
        TaskCenter.taskStarted(plugin.getClass(), task, name, this);
        return task;
    }

    @Override
    public ITask runTaskTimer(long delay, long period) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskTimer(plugin, delay, period));
        id = task.getTaskId();
        TaskCenter.taskStarted(plugin.getClass(), task, name, this);
        return task;
    }

    @Override
    public ITask runTaskTimerAsynchronously(long delay, long period) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskTimerAsynchronously(plugin, delay, period));
        id = task.getTaskId();
        TaskCenter.taskStarted(plugin.getClass(), task, name, this);
        return task;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        TaskCenter.taskCancelled(plugin.getClass(), name, id);
        plugin.getServer().getScheduler().cancelTask(id);
        super.cancel();
    }

    @Override
    public String getTaskName() {
        return name;
    }
}
