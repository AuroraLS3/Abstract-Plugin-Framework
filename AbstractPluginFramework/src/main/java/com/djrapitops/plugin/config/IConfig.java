/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.config;

import com.djrapitops.plugin.config.fileconfig.IFileConfig;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public abstract class IConfig {

    private final File folder;
    private final String fileName;
    private File file;
    protected IFileConfig fileConfig;

    public IConfig(File folder, String fileName) throws IOException {
        this.folder = folder;
        this.fileName = fileName;
        load();
    }

    public File copyFromStream(InputStream inputStream) throws IOException {
        try {
            if (folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(folder, fileName);
            if (!file.exists()) {
                Files.copy(inputStream, file.toPath());
            }
            return file;
        } finally {
            inputStream.close();
        }
    }

    public File createFile() throws IOException {
        file = new File(folder, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public void save() throws IOException {
        save(file);
    }

    public abstract void save(File file) throws IOException;

    public final void load() throws IOException {
        load(createFile());
    }

    public abstract void load(File file) throws IOException;

    public IFileConfig getConfig() {
        return fileConfig;
    }


}