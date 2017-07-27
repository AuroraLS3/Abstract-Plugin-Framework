/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.task.bukkit.AbsBukkitRunnable;
import com.djrapitops.plugin.task.bungee.AbsBungeeRunnable;
import com.djrapitops.plugin.utilities.Compatibility;

/**
 *
 * @author Rsl1122
 * @param <T>
 */
public class RunnableFactory<T extends IPlugin> {

    private final T plugin;

    public RunnableFactory(T plugin) {
        this.plugin = plugin;
    }

    public IRunnable createNew(AbsRunnable runnable) {
        return createNew(runnable.getName(), runnable);
    }

    public IRunnable createNew(String name, AbsRunnable runnable) {
        if (Compatibility.isBukkitAvailable()) {

            return new AbsBukkitRunnable(name, plugin) {
                @Override
                public void run() {
                    runnable.setCancellable(this);
                    runnable.run();
                }
            };
        } else if (Compatibility.isBungeeAvailable()) {
            return new AbsBungeeRunnable(name, plugin) {
                @Override
                public void run() {
                    runnable.setCancellable(this);
                    runnable.run();
                }
            };
        } else {
            // TODO new Thread runnable.
            return null;
        }
    }
}
