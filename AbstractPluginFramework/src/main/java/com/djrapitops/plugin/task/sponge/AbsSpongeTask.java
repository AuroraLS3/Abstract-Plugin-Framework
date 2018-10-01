/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.task.sponge;

import com.djrapitops.plugin.task.PluginTask;
import org.spongepowered.api.scheduler.Task;

/**
 * {@link PluginTask} implementation for Sponge.
 *
 * @author Rsl1122
 */
class AbsSpongeTask implements PluginTask {

    private final Task task;

    AbsSpongeTask(Task task) {
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
}