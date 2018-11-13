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
package com.djrapitops.plugin.task.bukkit;

import com.djrapitops.plugin.BukkitPlugin;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.PluginTask;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * {@link PluginRunnable} implementation for Bukkit.
 *
 * @author Rsl1122
 */
public abstract class AbsBukkitRunnable extends BukkitRunnable implements PluginRunnable, Runnable {

    private final String name;
    private final long time;
    private int id = -1;

    private BukkitPlugin plugin;

    AbsBukkitRunnable(String name, BukkitPlugin plugin, long time) {
        this.name = name;
        this.time = time;
        this.plugin = plugin;
    }

    @Override
    public PluginTask runTask() {
        AbsBukkitTask task = new AbsBukkitTask(super.runTask(plugin));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskAsynchronously() {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskAsynchronously(plugin));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskLater(long delay) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskLater(plugin, delay));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskLaterAsynchronously(long delay) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskLaterAsynchronously(plugin, delay));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskTimer(long delay, long period) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskTimer(plugin, delay, period));
        id = task.getTaskId();
        return task;
    }

    @Override
    public PluginTask runTaskTimerAsynchronously(long delay, long period) {
        AbsBukkitTask task = new AbsBukkitTask(super.runTaskTimerAsynchronously(plugin, delay, period));
        id = task.getTaskId();
        return task;
    }

    @Override
    public synchronized void cancel() {
        if (plugin == null) {
            return;
        }
        try {
            plugin.getServer().getScheduler().cancelTask(id);
            super.cancel();
        } finally {
            plugin = null;
        }
    }

    @Override
    public String getTaskName() {
        return name;
    }

    @Override
    public long getTime() {
        return time;
    }
}
