package com.djrapitops.plugin.utilities;

import com.djrapitops.plugin.StaticHolder;

/**
 *
 * @author Rsl1122
 */
public class Compatibility {
    
    public static Class getUtilityProviderClass() {
        return StaticHolder.getUtilityProviderClass();
    }

    public static boolean isSpigotAvailable() {
        try {
            Class.forName("org.spigotmc.CustomTimingsHandler");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBukkitAvailable() {
        try {
            Class.forName("org.bukkit.plugin.java.JavaPlugin");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBungeeAvailable() {
        try {
            Class.forName("net.md_5.bungee.api.plugin.Plugin");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
