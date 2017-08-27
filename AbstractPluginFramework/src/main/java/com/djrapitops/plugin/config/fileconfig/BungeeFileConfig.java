/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.config.fileconfig;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class BungeeFileConfig implements IFileConfig {

    private Configuration configuration;

    public BungeeFileConfig() {
    }

    @Override
    public void save(File file) throws IOException {
        if (configuration != null) {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        }

    }

    @Override
    public void load(File file) throws IOException {
        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    @Override
    public void addDefaults(Map<String, Object> defaults) {
        nullCheck();
        Collection<String> keys = configuration.getKeys();
        for (Map.Entry<String, Object> entry : defaults.entrySet()) {
            addDefault(entry.getKey(), entry.getValue(), keys);
        }
    }

    @Override
    public void addDefault(String path, Object defaultValue) {
        nullCheck();
        addDefault(path, defaultValue, configuration.getKeys());
    }

    private void addDefault(String path, Object defaultValue, Collection<String> keys) {
        nullCheck();
        if (!keys.contains(path)) {
            set(path, defaultValue);
        }
    }

    @Override
    public void copyDefaults() {
        // True by default.
    }

    @Override
    public void set(String path, Object object) {
        nullCheck();
        configuration.set(path, object);
    }

    @Override
    public boolean getBoolean(String path) {
        nullCheck();
        return configuration.getBoolean(path);
    }

    @Override
    public Integer getInt(String path) {
        nullCheck();
        return configuration.getInt(path);
    }

    private void nullCheck() {
        if (configuration == null) {
            throw new IllegalStateException("Configuration has not been loaded");
        }
    }

    @Override
    public Double getDouble(String path) {
        nullCheck();
        return configuration.getDouble(path);
    }

    @Override
    public Long getLong(String path) {
        nullCheck();
        return configuration.getLong(path);
    }

    @Override
    public String getString(String path) {
        nullCheck();
        return configuration.getString(path);
    }

    @Override
    public List<String> getStringList(String path) {
        nullCheck();
        return configuration.getStringList(path);
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        nullCheck();
        return configuration.getIntList(path);
    }

    @Override
    public IConfigSection getConfigSection(String path) {
        throw new UnsupportedOperationException("Bungee Config does not support config sections.");
    }

    @Override
    public Set<String> getKeys() {
        nullCheck();
        return new HashSet<>(configuration.getKeys());
    }
}