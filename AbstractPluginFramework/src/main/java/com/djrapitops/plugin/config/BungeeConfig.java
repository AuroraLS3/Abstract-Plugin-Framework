package com.djrapitops.plugin.config;

import com.djrapitops.plugin.config.fileconfig.BungeeFileConfig;

import java.io.File;
import java.io.IOException;

/**
 * Config file class for creating Bukkit config file classes more easily.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class BungeeConfig extends IConfig {

    public BungeeConfig(IPlugin plugin, String filename) throws IOException {
        this(plugin.getDataFolder(), filename);
    }

    public BungeeConfig(File folder, String filename) throws IOException {
        super(folder, filename);
        load();
    }

    public final void load(File file) throws IOException {
        fileConfig = new BungeeFileConfig();
        fileConfig.load(file);
    }

    public final void save(File file) throws IOException {
        fileConfig.save(file);
    }
}
