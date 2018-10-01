package com.djrapitops.plugin.task;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.utilities.StackUtils;

/**
 * Factory for creating runnable objects that can be scheduled on any server platform.
 * <p>
 * Obtain an instance from {@link IPlugin#getRunnableFactory()}.
 *
 * @author Rsl1122
 */
public abstract class RunnableFactory {

    @Deprecated
    public static PluginRunnable createNew(String name, AbsRunnable absRunnable) {
        Class callingPlugin = StackUtils.getCallingPlugin();
        StaticHolder.saveInstance(absRunnable.getClass(), callingPlugin);
        IPlugin plugin = StaticHolder.getInstance(callingPlugin);
        return plugin.getRunnableFactory().create(name, absRunnable);
    }

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
     * Creates a new PluginRunnable.
     *
     * @return a new PluginRunnable.
     * @throws IllegalStateException If the plugin is disabled.
     */
    protected abstract PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time);

    public abstract void cancelAllKnownTasks();

    protected void setCancellable(AbsRunnable runnable, PluginRunnable implementingRunnable) {
        runnable.setCancellable(implementingRunnable);
    }
}
