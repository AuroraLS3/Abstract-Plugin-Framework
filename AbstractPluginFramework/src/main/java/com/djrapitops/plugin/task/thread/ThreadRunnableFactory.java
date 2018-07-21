package com.djrapitops.plugin.task.thread;

import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

public class ThreadRunnableFactory extends RunnableFactory {

    @Override
    public PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time) {
        return new ThreadRunnable(name, runnable, time);
    }
}
