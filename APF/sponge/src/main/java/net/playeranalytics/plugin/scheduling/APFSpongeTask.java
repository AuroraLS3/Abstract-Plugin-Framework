package net.playeranalytics.plugin.scheduling;

public class APFSpongeTask implements Task {

    private final org.spongepowered.api.scheduler.Task task;

    public APFSpongeTask(org.spongepowered.api.scheduler.Task task) {this.task = task;}

    @Override
    public boolean isGameThread() {
        return !task.isAsynchronous();
    }

    @Override
    public void cancel() {
        task.cancel();
    }
}
