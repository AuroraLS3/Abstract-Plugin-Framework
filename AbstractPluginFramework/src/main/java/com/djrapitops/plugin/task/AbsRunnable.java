/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task;

/**
 * This class is an abstraction class used to contain run information for Plugin runnables.
 *
 * When creating a new task, RunnableFactory#createNew method should
 * be called with an implementation of this class as a parameter.
 *
 * @author Rsl1122
 */
public abstract class AbsRunnable implements Runnable {

    private PluginRunnable runnable;

    public AbsRunnable() {
    }

    public int getTaskId() {
        return runnable.getTaskId();
    }

    @Override
    public abstract void run();

    public void setCancellable(PluginRunnable cancellable) {
        this.runnable = cancellable;
    }

    public void cancel() {
        if (runnable != null) {
            runnable.cancel();
        }
    }
}
