package com.djrapitops.plugin.task;

import com.djrapitops.plugin.IPlugin;

/**
 * Factory for creating runnable objects that can be scheduled on any server platform.
 * <p>
 * Obtain an instance from {@link IPlugin#getRunnableFactory()}.
 *
 * @author Rsl1122
 */
public abstract class RunnableFactory {

    /**
     * Create a new {@link PluginRunnable} that can be scheduled.
     *
     * @param name     Name of the new task that is created when the {@link PluginRunnable} is executed.
     * @param runnable Abstract executable that can be cancelled.
     * @return a new {@link PluginRunnable} specific to the platform.
     */
    public PluginRunnable create(String name, AbsRunnable runnable) {
        long time = System.currentTimeMillis();
        return createNewRunnable(name, runnable, time);
    }

    /**
     * Create a new {@link PluginRunnable}.
     *
     * @param name     Name of the task when the PluginRunnable is started.
     * @param runnable Abstract executable that can be cancelled.
     * @param time     Time of creation.
     * @return a new {@link PluginRunnable} specific to the platform.
     */
    protected abstract PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time);

    /**
     * Cancel all known tasks of the plugin.
     */
    public abstract void cancelAllKnownTasks();

    protected void setCancellable(AbsRunnable runnable, PluginRunnable implementingRunnable) {
        runnable.setCancellable(implementingRunnable);
    }
}