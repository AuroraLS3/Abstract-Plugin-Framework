package net.playeranalytics.plugin.scheduling;

import net.md_5.bungee.api.scheduler.ScheduledTask;

public class APFBungeeTask implements Task {

    private final ScheduledTask task;

    public APFBungeeTask(ScheduledTask task) {this.task = task;}

    @Override
    public boolean isGameThread() {
        return false;
    }

    @Override
    public void cancel() {
        task.cancel();
    }
}
