package com.djrapitops.plugin.task;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.utilities.StackUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class RunnableFactory {

    private final Map<Long, PluginRunnable> runningTasks;

    public RunnableFactory() {
        this.runningTasks = new ConcurrentHashMap<>();
    }

    @Deprecated
    public static PluginRunnable createNew(String name, AbsRunnable absRunnable) {
        Class callingPlugin = StackUtils.getCallingPlugin();
        StaticHolder.saveInstance(absRunnable.getClass(), callingPlugin);
        IPlugin plugin = StaticHolder.getInstance(callingPlugin);
        return plugin.getRunnableFactory().create(name, absRunnable);
    }

    public PluginRunnable create(String name, AbsRunnable absRunnable) {
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
    protected abstract PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time);

    public Map<Long, PluginRunnable> getRunningTasks() {
        return runningTasks;
    }

    public void cancelled(PluginRunnable pluginRunnable) {
        runningTasks.remove(pluginRunnable.getTime());
    }

    public void cancelAllKnownTasks() {
        new ArrayList<>(runningTasks.values())
                .forEach(PluginRunnable::cancel);
    }

    protected void setCancellable(AbsRunnable runnable, PluginRunnable implementingRunnable) {
        runnable.setCancellable(implementingRunnable);
    }
}
