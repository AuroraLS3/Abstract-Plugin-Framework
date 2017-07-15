/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task;

import com.djrapitops.plugin.utilities.Verify;

/**
 * This class is an abstraction class used to contain run information for Bukkit
 * and Bungee runnables.
 *
 * When creating a new task, IPlugin#getRunnableFactory#createNew method should
 * be called with an implementation of this class as a parameter.
 *
 * @author Rsl1122
 */
public abstract class AbsRunnable {

    private IRunnable runnable;
    private final String name;

    public AbsRunnable() {
        name = "No name given";
    }

    public AbsRunnable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTaskName() {
        return getName();
    }

    public int getTaskId() {
        return runnable.getTaskId();
    }

    public abstract void run();

    public void setCancellable(IRunnable cancellable) {
        this.runnable = cancellable;
    }

    public void cancel() {
        Verify.nullCheck(runnable);
        runnable.cancel();
    }
}
