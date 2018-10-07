package com.djrapitops.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Rsl1122
 */
@Deprecated
public class StaticHolder {

    private static final Map<Class, IPlugin> plugins = new HashMap<>();
    private static final Map<Class, Class> classMap = new HashMap<>();

    @Deprecated
    public static void saveInstance(Class c, Class plugin) {
        classMap.put(c, plugin);
    }

    @Deprecated
    public static Class getProvidingPlugin(Class c) {
        return classMap.get(c);
    }

    @Deprecated
    public static void register(Class<? extends IPlugin> c, IPlugin plugin) {
        plugins.put(c, plugin);
    }

    @Deprecated
    public static void register(IPlugin plugin) {
        register(plugin.getClass(), plugin);
    }

    @Deprecated
    public static void unRegister(Class<? extends IPlugin> c) {
        List<Class> toRemove = classMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(c))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        for (Class aClass : toRemove) {
            toRemove.remove(aClass);
        }

        plugins.remove(c);
    }

    @Deprecated
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
