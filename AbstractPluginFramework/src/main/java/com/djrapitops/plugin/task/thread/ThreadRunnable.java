/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.task.thread;

import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.PluginTask;

/**
 * PluginRunnable implementation for tasks where no scheduler is available.
 *
 * @author Rsl1122
 */
public class ThreadRunnable implements PluginRunnable, Runnable {

    private final String name;
    private final AbsRunnable runnable;
    private final long time;
    private Thread thread;

    public ThreadRunnable(String name, AbsRunnable runnable, long time) {
        this.name = name;
        this.runnable = runnable;
        this.time = time;
        runnable.setCancellable(this);
    }

    @Override
    public String getTaskName() {
        return name;
    }

    @Override
    public void cancel() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public int getTaskId() {
        return -1;
    }

    @Override
    public PluginTask runTask() {
        runThis();
        return null;
    }

    @Override
    public PluginTask runTaskAsynchronously() {
        runThis();
        return null;
    }

    @Override
    public PluginTask runTaskLater(long delay) {
        runThis();
        return null;
    }

    @Override
    public PluginTask runTaskLaterAsynchronously(long delay) {
        runThis();
        return null;
    }

    @Override
    public PluginTask runTaskTimer(long delay, long period) {
        runThis();
        return null;
    }

    @Override
    public PluginTask runTaskTimerAsynchronously(long delay, long period) {
        runThis();
        return null;
    }

    public void runThis() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        runnable.run();
    }

    @Override
    public long getTime() {
        return time;
    }
}