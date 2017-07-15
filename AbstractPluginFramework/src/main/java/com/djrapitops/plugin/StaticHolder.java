package com.djrapitops.plugin;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class StaticHolder {
    private static final String apfVersion = "2.0.0";
    private static final Map<Class, BukkitPlugin> INSTANCES_BUKKIT = new HashMap<>();
    private static final Map<Class, BungeePlugin> INSTANCES_BUNGEE = new HashMap<>();
    private static Class utilityProvider = null;
    
    final static void setInstance(Class c, BukkitPlugin instance) {
        utilityProvider = c;
        INSTANCES_BUKKIT.put(c, instance);
    }
    
    final static void setInstance(Class c, BungeePlugin instance) {
        utilityProvider = c;
        INSTANCES_BUNGEE.put(c, instance);
    }
    
    public final static <T extends IPlugin> T getInstance(Class<T> c) {
        BukkitPlugin pluginBukkit = INSTANCES_BUKKIT.get(c);
        if (pluginBukkit != null && pluginBukkit.getClass() == c) {
            return (T) pluginBukkit;
        }
        BungeePlugin pluginBungee = INSTANCES_BUNGEE.get(c);
        if (pluginBungee != null && pluginBungee.getClass() == c) {
            return (T) pluginBungee;
        }
        return null;
    }
    
    public final static String getAPFVersion() {
        return apfVersion;
    }
    
    public static Class getUtilityProviderClass() {
        return utilityProvider;
    }
}
