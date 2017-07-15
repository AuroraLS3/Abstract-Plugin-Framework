/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task.bungee;

import com.djrapitops.plugin.task.ITask;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

/**
 *
 * @author Rsl1122
 */
public class AbsBungeeTask implements ScheduledTask, ITask<ScheduledTask> {

    private final ScheduledTask task;

    public AbsBungeeTask(ScheduledTask task) {
        this.task = task;
    }

    @Override
    public int getTaskId() {
        return task.getId();
    }

    @Override
    public Plugin getOwner() {
        return task.getOwner();
    }

    @Override
    public boolean isSync() {
        return false;
    }

    @Override
    public void cancel() {
        task.cancel();
    }

    @Override
    public int getId() {
        return this.getTaskId();
    }

    @Override
    public Runnable getTask() {
        return task.getTask();
    }

    @Override
    public ScheduledTask getWrappedTask() {
        return this;
    }
}
