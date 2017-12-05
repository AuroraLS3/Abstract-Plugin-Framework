package com.djrapitops.plugin.task.bukkit;

import com.djrapitops.plugin.task.ITask;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author Rsl1122
 */
public class AbsBukkitTask implements BukkitTask, ITask<BukkitTask> {

    private final BukkitTask task;

    public AbsBukkitTask(BukkitTask task) {
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

    @Override
    public BukkitTask getWrappedTask() {
        return task;
    }

}
