package com.djrapitops.plugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author Rsl1122
 */
public abstract class BukkitPlugin extends JavaPlugin {

    private final Plugin plugin;

    public BukkitPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        plugin.onEnable();
    }

    @Override
    public void onDisable() {
        plugin.onDisable();
    }
}