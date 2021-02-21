package net.playeranalytics.plugin.scheduling;

import cn.nukkit.scheduler.TaskHandler;

public class APFNukkitTask implements Task {

    private final TaskHandler task;

    public APFNukkitTask(TaskHandler task) {this.task = task;}

    @Override
    public boolean isGameThread() {
        return !task.isAsynchronous();
    }

    @Override
    public void cancel() {
        task.cancel();
    }
}
