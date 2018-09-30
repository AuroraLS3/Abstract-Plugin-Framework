package com.djrapitops.plugin.task.thread;

import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

/**
 * {@link RunnableFactory} implementation for testing purposes.
 *
 * @author Rsl1122
 */
public class ThreadRunnableFactory extends RunnableFactory {

    @Override
    protected PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time) {
        ThreadRunnable threadRunnable = new ThreadRunnable(name, runnable, time);
        setCancellable(runnable, threadRunnable);
        return threadRunnable;
    }
}
