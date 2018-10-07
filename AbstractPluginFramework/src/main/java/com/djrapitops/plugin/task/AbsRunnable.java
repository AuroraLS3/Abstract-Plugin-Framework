/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task;

/**
 * This class is an abstraction class used to contain run information for Plugin runnables.
 * <p>
 * When creating a new task, {@link RunnableFactory#create(String, AbsRunnable)} method should be
 * called with an implementation of this class as a parameter.
 *
 * @author Rsl1122
 */
public abstract class AbsRunnable implements Runnable {

    private PluginRunnable runnable;

    /**
     * Get the ID defined by the delegated platform specific runnable.
     *
     * @return integer, -1 if undefined.
     */
    public int getTaskId() {
        return runnable != null ? runnable.getTaskId() : -1;
    }

    @Override
    public abstract void run();

    void setCancellable(PluginRunnable cancellable) {
        this.runnable = cancellable;
    }

    /**
     * Cancel the delegated platform specific runnable.
     */
    public void cancel() {
        if (runnable != null) {
            runnable.cancel();
        }
    }
}
