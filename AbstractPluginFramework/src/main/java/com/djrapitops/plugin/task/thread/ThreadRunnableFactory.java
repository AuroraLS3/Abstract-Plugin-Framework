package com.djrapitops.plugin.task.thread;

import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

public class ThreadRunnableFactory implements RunnableFactory {

    @Override
    public PluginRunnable createNew(String name, AbsRunnable runnable) {
        return new ThreadRunnable(name, runnable);
    }
}
