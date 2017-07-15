package com.djrapitops.plugin.config;

import com.djrapitops.plugin.BukkitPlugin;
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Config file class for creating Bukkit config file classes more easily.
 *
 * @author Rsl1122
 * @param <T> BukkitPlugin implementation this config class is for.
 * @since 2.0.0
 */
public class BukkitConfig<T extends BukkitPlugin> {

    private File folder;
    private String filename;
    private FileConfiguration fileConfig;

    public BukkitConfig(T plugin, String filename) throws IOException, InvalidConfigurationException {
        this(plugin.getDataFolder(), filename);
    }

    public BukkitConfig(File folder, String filename) throws IOException, InvalidConfigurationException {
        this.folder = folder;
        this.filename = filename;
        load();
    }

    public final File createFile() throws IOException {
        File file = new File(folder, filename + ".yml");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public final void load() throws IOException, InvalidConfigurationException {
        load(createFile());
    }

    public final void load(File file) throws IOException, InvalidConfigurationException {
        fileConfig = new YamlConfiguration();
        fileConfig.load(file);
    }

    public final void save() throws IOException {
        save(createFile());
    }

    public final void save(File file) throws IOException {
        fileConfig.save(file);
    }

    public final FileConfiguration getConfig() {
        return fileConfig;
    }
}
