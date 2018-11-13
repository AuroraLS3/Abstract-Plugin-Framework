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
package com.djrapitops.plugin.task.bungee;

import com.djrapitops.plugin.task.PluginTask;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

/**
 * {@link PluginTask} implementation for Bungee.
 *
 * @author Rsl1122
 */
public class AbsBungeeTask implements ScheduledTask, PluginTask {

    private final ScheduledTask task;

    AbsBungeeTask(ScheduledTask task) {
        this.task = task;
    }

    @Override
    public int getTaskId() {
        return task.getId();
    }

    @Override
    public Plugin getOwner() {
        return task.getOwner();
    }

    @Override
    public boolean isSync() {
        return false;
    }

    @Override
    public void cancel() {
        task.cancel();
    }

    @Override
    public int getId() {
        return this.getTaskId();
    }

    @Override
    public Runnable getTask() {
        return task.getTask();
    }
}
