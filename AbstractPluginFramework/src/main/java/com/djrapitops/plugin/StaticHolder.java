package com.djrapitops.plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rsl1122
 */
public class StaticHolder {

    private static final Map<Class, IPlugin> plugins = new HashMap<>();
    private static final Map<Class, Class> classMap = new HashMap<>();

    public static void saveInstance(Class c, Class plugin) {
        classMap.put(c, plugin);
    }

    public static Class getProvidingPlugin(Class c) {
        return classMap.get(c);
    }

    public static void register(Class<? extends IPlugin> c, IPlugin plugin) {
        plugins.put(c, plugin);
    }

    public static void register(IPlugin plugin) {
        register(plugin.getClass(), plugin);
    }

    public static void unRegister(Class<? extends IPlugin> c) {
        plugins.remove(c);
    }

    public static IPlugin getInstance(Class c) {
        IPlugin iPlugin = plugins.get(c);
        if (iPlugin == null) {
            throw new IllegalStateException("Plugin has not been initialized: " + c.getSimpleName());
        }
        return iPlugin;
    }

    private StaticHolder() {
        /* Hide constructor */
    }
}
