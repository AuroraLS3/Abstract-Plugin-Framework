/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
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
 * This class is an abstraction class used to contain run information for Plugin runnables.
 * <p>
 * When creating a new task, {@link RunnableFactory#create(String, AbsRunnable)} method should be
 * called with an implementation of this class as a parameter.
 *
 * @author AuroraLS3
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
