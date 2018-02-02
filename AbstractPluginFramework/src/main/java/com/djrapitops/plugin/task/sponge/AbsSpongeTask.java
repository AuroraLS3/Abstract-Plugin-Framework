/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.task.sponge;

import com.djrapitops.plugin.task.ITask;
import org.spongepowered.api.scheduler.Task;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class AbsSpongeTask implements ITask<Task> {

    private final Task task;

    public AbsSpongeTask(Task task) {
        this.task = task;
    }

    @Override
    public int getTaskId() {
        return -1;
    }

    @Override
    public boolean isSync() {
        return !task.isAsynchronous();
    }

    @Override
    public void cancel() {
        task.cancel();
    }

    @Override
    public Task getWrappedTask() {
        return task;
    }
}