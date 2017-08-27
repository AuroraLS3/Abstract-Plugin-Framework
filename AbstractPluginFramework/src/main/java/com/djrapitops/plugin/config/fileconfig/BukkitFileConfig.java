/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.config.fileconfig;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class BukkitFileConfig extends BukkitConfigSection implements IFileConfig {

    private final FileConfiguration fc;

    public BukkitFileConfig(FileConfiguration fc) {
        super(fc);
        this.fc = fc;
    }

    @Override
    public void copyDefaults() {
        fc.options().copyDefaults(true);
    }

    @Override
    public void save(File file) throws IOException {
        fc.save(file);
    }

    @Override
    public void load(File file) throws IOException {
        try {
            fc.load(file);
        } catch (InvalidConfigurationException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void addDefaults(Map<String, Object> defaults) {
        fc.addDefaults(defaults);
    }

    @Override
    public void addDefault(String path, Object defaultValue) {
        fc.addDefault(path, defaultValue);
    }
}