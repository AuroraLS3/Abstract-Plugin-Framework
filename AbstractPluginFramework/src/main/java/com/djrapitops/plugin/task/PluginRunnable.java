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
public interface PluginRunnable<T> {

    String getTaskName();

    void cancel();

    int getTaskId();

    PluginTask runTask();

    PluginTask runTaskAsynchronously();

    PluginTask runTaskLater(long delay);

    PluginTask runTaskLaterAsynchronously(long delay);

    PluginTask runTaskTimer(long delay, long period);

    PluginTask runTaskTimerAsynchronously(long delay, long period);
}
