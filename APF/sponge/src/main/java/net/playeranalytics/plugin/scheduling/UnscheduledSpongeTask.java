package net.playeranalytics.plugin.scheduling;

import java.util.function.Consumer;

public class UnscheduledSpongeTask implements UnscheduledTask {

    private final Object plugin;
    private final Runnable runnable;
    private final Consumer<Task> cancellableConsumer;

    public UnscheduledSpongeTask(Object plugin, Runnable runnable, Consumer<Task> cancellableConsumer) {
        this.plugin = plugin;
        this.runnable = runnable;
        this.cancellableConsumer = cancellableConsumer;
    }

    @Override
    public Task runTaskAsynchronously() {
        APFSpongeTask task = new APFSpongeTask(
                org.spongepowered.api.scheduler.Task.builder()
                        .execute(runnable)
                        .async()
                        .submit(plugin)
        );
        cancellableConsumer.accept(task);
        return task;
    }

    @Override
    public Task runTaskLaterAsynchronously(long delayTicks) {
        APFSpongeTask task = new APFSpongeTask(
                org.spongepowered.api.scheduler.Task.builder()
                        .execute(runnable)
                        .delayTicks(delayTicks)
                        .async()
                        .submit(plugin)
        );
        cancellableConsumer.accept(task);
        return task;
    }

    @Override
    public Task runTaskTimerAsynchronously(long delayTicks, long periodTicks) {
        APFSpongeTask task = new APFSpongeTask(
                org.spongepowered.api.scheduler.Task.builder()
                        .execute(runnable)
                        .delayTicks(delayTicks)
                        .intervalTicks(periodTicks)
                        .async()
                        .submit(plugin)
        );
        cancellableConsumer.accept(task);
        return task;
    }

    @Override
    public Task runTask() {
        APFSpongeTask task = new APFSpongeTask(
                org.spongepowered.api.scheduler.Task.builder()
                        .execute(runnable)
                        .submit(plugin)
        );
        cancellableConsumer.accept(task);
        return task;
    }

    @Override
    public Task runTaskLater(long delayTicks) {
        APFSpongeTask task = new APFSpongeTask(
                org.spongepowered.api.scheduler.Task.builder()
                        .execute(runnable)
                        .delayTicks(delayTicks)
                        .submit(plugin)
        );
        cancellableConsumer.accept(task);
        return task;
    }

    @Override
    public Task runTaskTimer(long delayTicks, long periodTicks) {
        APFSpongeTask task = new APFSpongeTask(
                org.spongepowered.api.scheduler.Task.builder()
                        .execute(runnable)
                        .delayTicks(delayTicks)
                        .intervalTicks(periodTicks)
                        .submit(plugin)
        );
        cancellableConsumer.accept(task);
        return task;
    }
}
