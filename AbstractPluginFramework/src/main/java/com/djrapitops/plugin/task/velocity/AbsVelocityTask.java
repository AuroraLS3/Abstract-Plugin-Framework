/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.task.velocity;

import com.djrapitops.plugin.task.PluginTask;
import com.velocitypowered.api.scheduler.ScheduledTask;
import com.velocitypowered.api.scheduler.TaskStatus;

/**
 * {@link PluginTask} implementation for Velocity.
 *
 * @author Rsl1122
 */
class AbsVelocityTask implements PluginTask {

    private final ScheduledTask task;

    AbsVelocityTask(ScheduledTask task) {
        this.task = task;
    }

    @Override
    public int getTaskId() {
        return -1;
    }

    @Override
    public boolean isSync() {
        return false;
    }

    @Override
    public void cancel() {
        task.cancel();
    }

    boolean isFinished() {
        TaskStatus status = task.status();
        return status == TaskStatus.FINISHED || status == TaskStatus.CANCELLED;
    }
}