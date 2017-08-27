/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.config.fileconfig;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Set;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class BukkitConfigSection implements IConfigSection {

    private final ConfigurationSection cs;

    public BukkitConfigSection(ConfigurationSection cs) {
        this.cs = cs;
    }

    @Override
    public void set(String path, Object object) {
        cs.set(path, object);
    }

    @Override
    public boolean getBoolean(String path) {
        return cs.getBoolean(path);
    }

    @Override
    public Integer getInt(String path) {
        return cs.getInt(path);
    }

    @Override
    public Double getDouble(String path) {
        return cs.getDouble(path);
    }

    @Override
    public Long getLong(String path) {
        return cs.getLong(path);
    }

    @Override
    public String getString(String path) {
        return cs.getString(path);
    }

    @Override
    public List<String> getStringList(String path) {
        return cs.getStringList(path);
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return cs.getIntegerList(path);
    }


    @Override
    public IConfigSection getConfigSection(String path) {
        return new BukkitConfigSection(cs.getConfigurationSection(path));
    }

    @Override
    public Set<String> getKeys() {
        return cs.getKeys(false);
    }
}