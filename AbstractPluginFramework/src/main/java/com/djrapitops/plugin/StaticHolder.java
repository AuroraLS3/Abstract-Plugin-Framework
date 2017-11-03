package com.djrapitops.plugin;

import com.djrapitops.plugin.api.systems.NotificationCenter;
import com.djrapitops.plugin.task.RunnableFactory;
import com.djrapitops.plugin.api.systems.TaskCenter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rsl1122
 */
public class StaticHolder {

    private static final Map<Class, Plugin> plugins = new HashMap<>();
    private static final Map<Class, Class> classMap = new HashMap<>();

    private static final NotificationCenter notificationCenter = new NotificationCenter();
    private static final TaskCenter taskCenter = new TaskCenter();
    private static final RunnableFactory runnableFactory = new RunnableFactory();

    public static void saveInstance(Class c, Class plugin) {
        classMap.put(c, plugin);
    }

    public static Class getProvidingPlugin(Class c) {
        return classMap.get(c);
    }

    public static NotificationCenter getNotificationCenter() {
        return notificationCenter;
    }

    public static <T extends Plugin> void register(T plugin) {
        plugins.put(plugin.getClass(), plugin);
    }

    public static void unRegister(Class<? extends Plugin> c) {
        plugins.remove(c);
    }

    public static TaskCenter getTaskCenter() {
        return taskCenter;
    }

    public static Plugin getInstance(Class c) {
        return plugins.get(c);
    }

    public static RunnableFactory getRunnableFactory() {
        return runnableFactory;
    }
}
