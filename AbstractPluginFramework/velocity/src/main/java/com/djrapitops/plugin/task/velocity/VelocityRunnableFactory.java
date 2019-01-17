/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.djrapitops.plugin.task.velocity;

import com.djrapitops.plugin.VelocityPlugin;
import com.djrapitops.plugin.task.AbsRunnable;
import com.djrapitops.plugin.task.PluginRunnable;
import com.djrapitops.plugin.task.RunnableFactory;
import com.velocitypowered.api.scheduler.Scheduler;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link RunnableFactory} implementation for Velocity.
 *
 * @author Rsl1122
 */
public class VelocityRunnableFactory extends RunnableFactory {

    private final VelocityPlugin plugin;
    private final Scheduler scheduler;

    private Set<AbsVelocityRunnable> tasks;

    public VelocityRunnableFactory(VelocityPlugin plugin, Scheduler scheduler) {
        this.plugin = plugin;
        this.scheduler = scheduler;

        tasks = new HashSet<>();
    }

    @Override
    protected PluginRunnable createNewRunnable(String name, AbsRunnable runnable, long time) {
        cleanUp();

        return new AbsVelocityRunnable(name, plugin, scheduler, time) {
            @Override
            public void run() {
                setCancellable(runnable, this);
                runnable.run();
            }
        };
    }

    private synchronized void cleanUp() {
        tasks.removeIf(runnable -> runnable.getTask().isFinished());
    }

    @Override
    public synchronized void cancelAllKnownTasks() {
        tasks.forEach(PluginRunnable::cancel);
        tasks.clear();
    }
}