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
package com.djrapitops.plugin.task.sponge;

import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.PluginTask;
import org.spongepowered.api.scheduler.Task;

/**
 * {@link PluginRunnable} implementation for Sponge.
 *
 * @author AuroraLS3
 */
abstract class AbsSpongeRunnable implements PluginRunnable, Runnable {

    private final String name;
    private final long time;

    private SpongePlugin plugin;

    private PluginTask task;

    AbsSpongeRunnable(String name, SpongePlugin plugin, long time) {
        this.time = time;
        this.plugin = plugin;
        this.name = name;
    }

    @Override
    public String getTaskName() {
        return name;
    }

    @Override
    public void cancel() {
        if (plugin == null) {
            return;
        }
        try {
            if (task != null) {
                task.cancel();
            }
        } finally {
            // Clear instances so that cyclic references don't block GC
            plugin = null;
        }
    }

    @Override
    public int getTaskId() {
        return -1;
    }

    @Override
    public PluginTask runTask() {
        task = new AbsSpongeTask(Task.builder().execute(this).submit(plugin));
        return this.task;
    }

    @Override
    public PluginTask runTaskAsynchronously() {
        task = new AbsSpongeTask(Task.builder().execute(this).async().submit(plugin));
        return this.task;
    }

    @Override
    public PluginTask runTaskLater(long delay) {
        task = new AbsSpongeTask(Task.builder().execute(this)
                .delayTicks(delay)
                .submit(plugin));
        return this.task;
    }

    @Override
    public PluginTask runTaskLaterAsynchronously(long delay) {
        task = new AbsSpongeTask(Task.builder().execute(this).async()
                .delayTicks(delay)
                .submit(plugin));
        return this.task;
    }

    @Override
    public PluginTask runTaskTimer(long delay, long period) {
        task = new AbsSpongeTask(Task.builder().execute(this)
                .delayTicks(delay)
                .intervalTicks(period)
                .submit(plugin));
        return this.task;
    }

    @Override
    public PluginTask runTaskTimerAsynchronously(long delay, long period) {
        task = new AbsSpongeTask(Task.builder().execute(this).async()
                .delayTicks(delay)
                .intervalTicks(period)
                .submit(plugin));
        return this.task;
    }

    @Override
    public long getTime() {
        return time;
    }
}