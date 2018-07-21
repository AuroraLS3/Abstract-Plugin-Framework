package com.djrapitops.plugin.task;

import java.util.TreeMap;

public abstract class RunnableFactory {

    private final TreeMap<Long, PluginRunnable> runningTasks;

    public RunnableFactory() {
        this.runningTasks = new TreeMap<>();
    }

    public PluginRunnable createNew(String name, AbsRunnable absRunnable) {
        long time = System.currentTimeMillis();
        PluginRunnable runnable = createNewRunnable(name, absRunnable, time);
        runningTasks.put(time, runnable);
        return runnable;
    }


    /**
     * Creates a new PluginRunnable.
     *
     * @return a new PluginRunnable.
     * @throws IllegalStateException If the plugin is disabled.
     */
    public abstract PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time);

    public TreeMap<Long, PluginRunnable> getRunningTasks() {
        return runningTasks;
    }
}
