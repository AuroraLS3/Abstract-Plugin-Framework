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

    String getTaskName();

    void cancel();

    int getTaskId();

    ITask runTask();

    ITask runTaskAsynchronously();

    ITask runTaskLater(long delay);

    ITask runTaskLaterAsynchronously(long delay);

    ITask runTaskTimer(long delay, long period);

    ITask runTaskTimerAsynchronously(long delay, long period);
}
