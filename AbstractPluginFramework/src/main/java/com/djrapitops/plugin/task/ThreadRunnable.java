/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.task;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class ThreadRunnable implements IRunnable, Runnable {

    private final String name;
    private final int id;
    private final AbsRunnable runnable;
    private Thread thread;

    public ThreadRunnable(String name, int id, AbsRunnable runnable) {
        this.name = name;
        this.id = id;
        this.runnable = runnable;
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
        return id;
    }

    @Override
    public ITask runTask() {
        runThis();
        return null;
    }

    @Override
    public ITask runTaskAsynchronously() {
        runThis();
        return null;
    }

    @Override
    public ITask runTaskLater(long delay) {
        runThis();
        return null;
    }

    @Override
    public ITask runTaskLaterAsynchronously(long delay) {
        runThis();
        return null;
    }

    @Override
    public ITask runTaskTimer(long delay, long period) {
        runThis();
        return null;
    }

    @Override
    public ITask runTaskTimerAsynchronously(long delay, long period) {
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
}