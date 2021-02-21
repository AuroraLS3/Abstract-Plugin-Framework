package net.playeranalytics.plugin.scheduling;

import com.velocitypowered.api.scheduler.ScheduledTask;
import com.velocitypowered.api.scheduler.TaskStatus;

public class APFVelocityTask implements Task {

    private final ScheduledTask task;

    public APFVelocityTask(ScheduledTask task) {
        this.task = task;
    }

    @Override
    public boolean isGameThread() {
        return false;
    }

    @Override
    public void cancel() {
        task.cancel();
    }

    public TaskStatus status() {return task.status();}
}
