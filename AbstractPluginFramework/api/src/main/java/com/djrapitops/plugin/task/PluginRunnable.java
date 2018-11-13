/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task;

/**
 * Interface for platform specific runnable implementations.
 *
 * @author Rsl1122
 */
public interface PluginRunnable {

    /**
     * Retrieve the name of the task, defined during creation.
     *
     * @return name.
     */
    String getTaskName();

    /**
     * Cancel the task.
     */
    void cancel();

    /**
     * Get the platform defined ID, -1 if undefined.
     *
     * @return ID or -1.
     */
    int getTaskId();

    /**
     * Schedule the task to be run, on main thread if possible.
     *
     * @return PluginTask to track the task.
     */
    PluginTask runTask();

    /**
     * Schedule the task to be run, on an asynchronous thread.
     *
     * @return PluginTask to track the task.
     */
    PluginTask runTaskAsynchronously();

    /**
     * Schedule the task to be run after a delay, on main thread if possible.
     *
     * @param delay Delay in server ticks - tick is 20 / second.
     * @return PluginTask to track the task.
     */
    PluginTask runTaskLater(long delay);

    /**
     * Schedule the task to be run after a delay, on an asynchronous thread.
     *
     * @param delay Delay in server ticks - tick is 20 / second.
     * @return PluginTask to track the task.
     */
    PluginTask runTaskLaterAsynchronously(long delay);

    /**
     * Schedule the task to be run after a delay periodically, on main thread if possible.
     *
     * @param delay  Delay in server ticks - tick is 20 / second.
     * @param period Period between each exectuion in server ticks - tick is 20 / second.
     * @return PluginTask to track the task.
     */
    PluginTask runTaskTimer(long delay, long period);

    /**
     * Schedule the task to be run after a delay periodically, on an asynchronous thread.
     *
     * @param delay  Delay in server ticks - tick is 20 / second.
     * @param period Period between each exectuion in server ticks - tick is 20 / second.
     * @return PluginTask to track the task.
     */
    PluginTask runTaskTimerAsynchronously(long delay, long period);

    /**
     * Retrieve the epoch millisecond the runnable was created.
     *
     * @return Milliseconds passed since epoch near the moment this runnable was constructed.
     */
    long getTime();
}
