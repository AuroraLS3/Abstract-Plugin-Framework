package com.djrapitops.plugin.api;

/**
 *
 * @author Rsl1122
 */
public class Check {

    private static final boolean BUKKIT_AVAILABLE = isAvailable("org.bukkit.plugin.java.JavaPlugin");
    private static final boolean SPIGOT_AVAILABLE = isAvailable("org.spigotmc.CustomTimingsHandler");
    private static final boolean PAPER_AVAILABLE = isAvailable("co.aikar.timings.Timing");
    private static final boolean BUNGEE_AVAILABLE = isAvailable("net.md_5.bungee.api.plugin.Plugin");
    private static final boolean SPONGE_AVAILABLE = isAvailable("org.spongepowered.api.plugin.Plugin");

    private static boolean isAvailable(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBukkitAvailable() {
        return BUKKIT_AVAILABLE;
    }

    public static boolean isSpigotAvailable() {
        return SPIGOT_AVAILABLE;
    }

    public static boolean isPaperAvailable() {
        return PAPER_AVAILABLE;
    }

    public static boolean isBungeeAvailable() {
        return BUNGEE_AVAILABLE;
    }

    public static boolean isSpongeAvailable() {
        return SPONGE_AVAILABLE;
    }

    private Check() {
        /* Hide constructor */
    }
}


