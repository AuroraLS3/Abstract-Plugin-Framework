package com.djrapitops.plugin.task.bukkit;

import com.djrapitops.plugin.task.PluginTask;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * {@link PluginTask} implementation for Bukkit.
 *
 * @author Rsl1122
 */
public class AbsBukkitTask implements BukkitTask, PluginTask {

    private final BukkitTask task;

    AbsBukkitTask(BukkitTask task) {
        this.task = task;
    }

    @Override
    public int getTaskId() {
        return task.getTaskId();
    }

    @Override
    public Plugin getOwner() {
        return task.getOwner();
    }

    @Override
    public boolean isSync() {
        return task.isSync();
    }

    @Override
    public boolean isCancelled() {
        return task.isCancelled();
    }

    @Override
    public void cancel() {
        task.cancel();
    }

}
