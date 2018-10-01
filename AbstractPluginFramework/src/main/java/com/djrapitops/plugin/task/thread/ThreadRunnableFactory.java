package com.djrapitops.plugin.task.thread;

import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;
import io.netty.util.internal.ConcurrentSet;

/**
 * {@link RunnableFactory} implementation for testing purposes.
 *
 * @author Rsl1122
 */
public class ThreadRunnableFactory extends RunnableFactory {

    private final ConcurrentSet<ThreadRunnable> threadRunnables;

    /**
     * Standard constructor.
     */
    public ThreadRunnableFactory() {
        threadRunnables = new ConcurrentSet<>();
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
