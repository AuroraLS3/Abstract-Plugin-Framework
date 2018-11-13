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

import com.djrapitops.plugin.IPlugin;

/**
 * Factory for creating runnable objects that can be scheduled on any server platform.
 * <p>
 * Obtain an instance from {@link IPlugin#getRunnableFactory()}.
 *
 * @author Rsl1122
 */
public abstract class RunnableFactory {

    /**
     * Create a new {@link PluginRunnable} that can be scheduled.
     *
     * @param name     Name of the new task that is created when the {@link PluginRunnable} is executed.
     * @param runnable Abstract executable that can be cancelled.
     * @return a new {@link PluginRunnable} specific to the platform.
     */
    public PluginRunnable create(String name, AbsRunnable runnable) {
        long time = System.currentTimeMillis();
        return createNewRunnable(name, runnable, time);
    }

    /**
     * Create a new {@link PluginRunnable}.
     *
     * @param name     Name of the task when the PluginRunnable is started.
     * @param runnable Abstract executable that can be cancelled.
     * @param time     Time of creation.
     * @return a new {@link PluginRunnable} specific to the platform.
     */
    protected abstract PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time);

    /**
     * Cancel all known tasks of the plugin.
     */
    public abstract void cancelAllKnownTasks();

    protected void setCancellable(AbsRunnable runnable, PluginRunnable implementingRunnable) {
        runnable.setCancellable(implementingRunnable);
    }
}
