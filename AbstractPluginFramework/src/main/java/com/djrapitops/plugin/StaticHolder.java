package com.djrapitops.plugin;

import com.djrapitops.plugin.api.systems.NotificationCenter;
import com.djrapitops.plugin.utilities.status.TaskCenter;

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

    public static void saveInstance(Class c, Class plugin) {
        classMap.put(c, plugin);
    }

    public static Class getProvidingPlugin(Class c) {
        return classMap.get(c);
    }

    public static NotificationCenter getNotificationCenter() {
        return notificationCenter;
    }

    public static <T extends Plugin> void register(Class c, T plugin) {
        plugins.put(c, plugin);
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
}
