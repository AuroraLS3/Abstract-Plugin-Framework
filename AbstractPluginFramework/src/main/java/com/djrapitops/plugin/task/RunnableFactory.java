/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task;

import com.djrapitops.plugin.IPlugin;
import com.djrapitops.plugin.StaticHolder;
import com.djrapitops.plugin.api.Check;
import com.djrapitops.plugin.task.bukkit.AbsBukkitRunnable;
import com.djrapitops.plugin.task.bungee.AbsBungeeRunnable;
import com.djrapitops.plugin.task.sponge.AbsSpongeRunnable;
import com.djrapitops.plugin.utilities.StackUtils;

/**
 * @author Rsl1122
 */
public class RunnableFactory {

    private static boolean testMode = false;

    public static IRunnable createNew(AbsRunnable runnable) {
        return createNew(runnable.getName(), runnable);
    }

    public static IRunnable createNew(String name, AbsRunnable runnable) {
        if (!testMode) {
            try {
                Class callingPlugin = StackUtils.getCallingPlugin();
                IPlugin instance = StaticHolder.getInstance(callingPlugin);
                StaticHolder.saveInstance(runnable.getClass(), callingPlugin);

                if (Check.isBukkitAvailable()) {
                    return new AbsBukkitRunnable(name, instance) {
                        @Override
                        public void run() {
                            runnable.setCancellable(this);
                            runnable.run();
                        }
                    };
                } else if (Check.isBungeeAvailable()) {
                    return new AbsBungeeRunnable(name, instance) {
                        @Override
                        public void run() {
                            runnable.setCancellable(this);
                            runnable.run();
                        }
                    };
                } else if (Check.isSpongeAvailable()) {
                    return new AbsSpongeRunnable(name, instance) {
                        @Override
                        public void run() {
                            runnable.setCancellable(this);
                            runnable.run();
                        }
                    };
                }
            } catch (NullPointerException ignored) {
            }
        }
        return new ThreadRunnable(name, 0, runnable);
    }

    public static void activateTestMode() {
        testMode = true;
    }
}
