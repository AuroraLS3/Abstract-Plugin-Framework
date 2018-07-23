package com.djrapitops.plugin.task;

import java.util.HashMap;
import java.util.Map;

public abstract class RunnableFactory {

    private final Map<Long, PluginRunnable> runningTasks;

    public RunnableFactory() {
        this.runningTasks = new HashMap<>();
    }

    public PluginRunnable createNew(String name, AbsRunnable absRunnable) {
        long time = System.currentTimeMillis();
        PluginRunnable runnable = createNewRunnable(name, absRunnable, time);
        while (runningTasks.containsKey(time)) {
            time++;
        }
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

    public Map<Long, PluginRunnable> getRunningTasks() {
        return runningTasks;
    }

    public void cancelled(PluginRunnable pluginRunnable) {
        runningTasks.remove(pluginRunnable.getTime());
    }
}
