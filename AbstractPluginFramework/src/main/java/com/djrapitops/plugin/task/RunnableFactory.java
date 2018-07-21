package com.djrapitops.plugin.task;

public interface RunnableFactory {

    /**
     * Creates a new PluginRunnable.
     *
     * @return a new PluginRunnable.
     * @throws IllegalStateException If the plugin is disabled.
     */
    PluginRunnable createNew(String name, AbsRunnable runnable);
    
}
