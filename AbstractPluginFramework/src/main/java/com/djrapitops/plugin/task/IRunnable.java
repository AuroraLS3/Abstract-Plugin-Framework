/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task;

/**
 *
 * @author Rsl1122
 * @param <T>
 */
public interface IRunnable<T> {

    public String getTaskName();

    public void cancel();

    public int getTaskId();

    public ITask runTask();

    public ITask runTaskAsynchronously();

    public ITask runTaskLater(long delay);

    public ITask runTaskLaterAsynchronously(long delay);

    public ITask runTaskTimer(long delay, long period);

    public ITask runTaskTimerAsynchronously(long delay, long period);
}
