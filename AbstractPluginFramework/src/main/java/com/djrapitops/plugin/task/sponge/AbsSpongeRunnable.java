/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.task.sponge;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.SpongePlugin;
import com.djrapitops.plugin.api.systems.TaskCenter;
import com.djrapitops.plugin.task.IRunnable;
import com.djrapitops.plugin.task.ITask;
import org.spongepowered.api.scheduler.Task;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public abstract class AbsSpongeRunnable<T extends IPlugin> implements IRunnable, Runnable {

    private final T plugin;
    private final String name;

    private ITask task;

    public AbsSpongeRunnable(String name, IPlugin plugin) {
        if (plugin instanceof SpongePlugin) {
            this.plugin = (T) plugin;
        } else {
            throw new IllegalArgumentException("Given plugin was not of correct type");
        }
        this.name = name;
    }

    @Override
    public String getTaskName() {
        return name;
    }

    @Override
    public void cancel() {
        TaskCenter.taskCancelled(plugin.getClass(), name, getTaskId());
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public int getTaskId() {
        return 0;
    }

    @Override
    public ITask runTask() {
        task = new AbsSpongeTask(Task.builder().execute(this).submit(plugin));
        return this.task;
    }

    @Override
    public ITask runTaskAsynchronously() {
        task = new AbsSpongeTask(Task.builder().execute(this).async().submit(plugin));
        return this.task;
    }

    @Override
    public ITask runTaskLater(long delay) {
        task = new AbsSpongeTask(Task.builder().execute(this)
                .delayTicks(delay)
                .submit(plugin));
        return this.task;
    }

    @Override
    public ITask runTaskLaterAsynchronously(long delay) {
        task = new AbsSpongeTask(Task.builder().execute(this).async()
                .delayTicks(delay)
                .submit(plugin));
        return this.task;
    }

    @Override
    public ITask runTaskTimer(long delay, long period) {
        task = new AbsSpongeTask(Task.builder().execute(this)
                .delayTicks(delay)
                .intervalTicks(period)
                .submit(plugin));
        return this.task;
    }

    @Override
    public ITask runTaskTimerAsynchronously(long delay, long period) {
        task = new AbsSpongeTask(Task.builder().execute(this).async()
                .delayTicks(delay)
                .intervalTicks(period)
                .submit(plugin));
        return this.task;
    }
}