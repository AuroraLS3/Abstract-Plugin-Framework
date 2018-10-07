/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task;

/**
 * Represents a task that is implemented by the platform.
 *
 * @author Rsl1122
 */
public interface PluginTask {

    /**
     * Retrieve the task ID provided by the platform.
     *
     * @return ID or -1 if undefined.
     */
    int getTaskId();

    /**
     * Check if the task is being run on the main thread of the platform.
     *
     * @return true/false
     */
    boolean isSync();

    /**
     * Cancel the task.
     */
    void cancel();
}
