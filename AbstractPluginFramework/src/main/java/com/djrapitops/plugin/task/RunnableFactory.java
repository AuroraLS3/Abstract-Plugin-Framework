/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task;

import com.djrapitops.plugin.Plugin;
import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.api.Check;
import com.djrapitops.plugin.task.bukkit.AbsBukkitRunnable;
import com.djrapitops.plugin.task.bungee.AbsBungeeRunnable;
import com.djrapitops.plugin.utilities.StackUtils;

/**
 * @author Rsl1122
 */
public class RunnableFactory {

    public static IRunnable createNew(AbsRunnable runnable) {
        return createNew(runnable.getName(), runnable);
    }

    public static IRunnable createNew(String name, AbsRunnable runnable) {
        Class callingPlugin = StackUtils.getCallingPlugin();
        Plugin instance = StaticHolder.getInstance(callingPlugin);
        StaticHolder.saveInstance(runnable.getClass(), callingPlugin);

        if (Check.isBukkitAvailable()) {
            return new AbsBukkitRunnable(name, instance.getProvider()) {
                @Override
                public void run() {
                    runnable.setCancellable(this);
                    runnable.run();
                }
            };
        } else if (Check.isBungeeAvailable()) {
            return new AbsBungeeRunnable(name, instance.getProvider()) {
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
