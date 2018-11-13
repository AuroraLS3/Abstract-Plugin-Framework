package com.djrapitops.plugin.task.thread;

import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link RunnableFactory} implementation for testing purposes.
 *
 * @author Rsl1122
 */
public class ThreadRunnableFactory extends RunnableFactory {

    private volatile Set<ThreadRunnable> threadRunnables;

    /**
     * Standard constructor.
     */
    public ThreadRunnableFactory() {
        threadRunnables = new HashSet<>();
    }

    @Override
    protected PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time) {
        ThreadRunnable threadRunnable = new ThreadRunnable(name, runnable, time);
        setCancellable(runnable, threadRunnable);
        threadRunnables.add(threadRunnable);
        return threadRunnable;
    }

    @Override
    public void cancelAllKnownTasks() {
        threadRunnables.forEach(ThreadRunnable::cancel);
    }
}
