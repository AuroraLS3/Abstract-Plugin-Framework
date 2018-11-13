/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
