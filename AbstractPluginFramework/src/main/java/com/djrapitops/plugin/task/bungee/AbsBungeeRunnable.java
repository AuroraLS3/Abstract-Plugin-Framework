/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task.bungee;

import com.djrapitops.plugin.BungeePlugin;
import java.util.concurrent.TimeUnit;

import com.djrapitops.plugin.IPlugin;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import com.djrapitops.plugin.api.TimeAmount;
import com.djrapitops.plugin.task.IRunnable;
import com.djrapitops.plugin.task.ITask;

/**
 *
 * @author Rsl1122
 * @param <T>
 */
public abstract class AbsBungeeRunnable<T extends BungeePlugin> implements IRunnable, Runnable {

    private final T plugin;
    private final String name;
    private final TaskScheduler scheduler;
    private int id = -1;

    public AbsBungeeRunnable(String name, IPlugin plugin) {
        this.name = name;
        this.plugin = (T) plugin;
        scheduler = this.plugin.getProxy().getScheduler();
    }

    @Override
    public abstract void run();

    @Override
    public ITask runTask() {
        return runTaskAsynchronously();
    }

    @Override
    public ITask runTaskAsynchronously() {
        AbsBungeeTask task = new AbsBungeeTask(scheduler.runAsync(plugin, this));
        id = task.getTaskId();
        plugin.taskStatus().taskStarted(task, name, this);
        return task;
    }

    @Override
    public ITask runTaskLater(long delay) {

        return runTaskLaterAsynchronously(delay);
    }

    @Override
    public ITask runTaskLaterAsynchronously(long delay) {
        AbsBungeeTask task = new AbsBungeeTask(scheduler.schedule(plugin, this, TimeAmount.ticksToMillis(delay), TimeUnit.MILLISECONDS));
        id = task.getTaskId();
        plugin.taskStatus().taskStarted(task, name, this);
        return task;
    }

    @Override
    public ITask runTaskTimer(long delay, long period) {
        return runTaskTimerAsynchronously(delay, period);
    }

    @Override
    public ITask runTaskTimerAsynchronously(long delay, long period) {
        AbsBungeeTask task = new AbsBungeeTask(scheduler.schedule(plugin, this, TimeAmount.ticksToMillis(delay), TimeAmount.ticksToMillis(period), TimeUnit.MILLISECONDS));
        id = task.getTaskId();
        plugin.taskStatus().taskStarted(task, name, this);
        return task;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        plugin.taskStatus().taskCancelled(name, id);
        scheduler.cancel(id);        
    }

    @Override
    public int getTaskId() {
        return id;
    }

    @Override
    public String getTaskName() {
        return name;
    }
}
