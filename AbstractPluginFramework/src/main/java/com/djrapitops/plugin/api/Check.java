package com.djrapitops.plugin.api;

/**
 * Utility for checking what platform is in use.
 * <p>
 * Although this is a convenience class, most usages of this class can be considered an anti-pattern that suggests
 * inheritance, Single Responsibility Principle or class structure issues. Usages of this class can be replaced with a
 * split of the class on a per platform basis.
 *
 * @author Rsl1122
 */
public class Check {

    private static final boolean BUKKIT_AVAILABLE = isAvailable("org.bukkit.plugin.java.JavaPlugin");
    private static final boolean SPIGOT_AVAILABLE = isAvailable("org.spigotmc.CustomTimingsHandler");
    private static final boolean PAPER_AVAILABLE = isAvailable("co.aikar.timings.Timing");
    private static final boolean BUNGEE_AVAILABLE = isAvailable("net.md_5.bungee.api.plugin.Plugin");
    private static final boolean SPONGE_AVAILABLE = isAvailable("org.spongepowered.api.plugin.Plugin");
    private static final boolean VELOCITY_AVAILABLE = isAvailable("com.velocitypowered.api.plugin.Plugin");

    private Check() {
        /* Static method class. */
    }

    /**
     * Check if a class is loaded.
     *
     * @param clazz Fully qualified class name.
     * @return true/false
     */
    public static boolean isAvailable(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if classes related to Bukkit platform are loaded.
     *
     * @return true/false.
     */
    public static boolean isBukkitAvailable() {
        return BUKKIT_AVAILABLE;
    }

    /**
     * Check if classes related to Spigot platform are loaded.
     *
     * @return true/false.
     */
    public static boolean isSpigotAvailable() {
        return SPIGOT_AVAILABLE;
    }

    /**
     * Check if classes related to Paper platform are loaded.
     *
     * @return true/false.
     */
    public static boolean isPaperAvailable() {
        return PAPER_AVAILABLE;
    }

    /**
     * Check if classes related to Bungee platform are loaded.
     *
     * @return true/false.
     */
    public static boolean isBungeeAvailable() {
        return BUNGEE_AVAILABLE;
    }

    /**
     * Check if classes related to Sponge platform are loaded.
     *
     * @return true/false.
     */
    public static boolean isSpongeAvailable() {
        return SPONGE_AVAILABLE;
    }

    /**
     * Check if classes related to Velocity platform are loaded.
     *
     * @return true/false.
     */
    public static boolean isVelocityAvailable() {
        return VELOCITY_AVAILABLE;
    }
}


