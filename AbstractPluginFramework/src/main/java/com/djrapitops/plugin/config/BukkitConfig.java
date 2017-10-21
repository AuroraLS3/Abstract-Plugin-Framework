package com.djrapitops.plugin.config;

import java.io.File;
import java.io.IOException;

import com.djrapitops.plugin.config.fileconfig.BukkitFileConfig;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Config file class for creating Bukkit config file classes more easily.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class BukkitConfig extends IConfig {

    public BukkitConfig(IPlugin plugin, String filename) throws IOException {
        this(plugin.getDataFolder(), filename);
    }

    public BukkitConfig(File folder, String filename) throws IOException {
        super(folder, filename);
        load();
    }

    public final void load(File file) throws IOException {
        fileConfig = new BukkitFileConfig(new YamlConfiguration());
        fileConfig.load(file);
    }

    public final void save(File file) throws IOException {
        fileConfig.save(file);
    }
}
