package com.djrapitops.plugin.api.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.*;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class ConfigTest {

    @Before
    public void setUp() throws Exception {
        File tempTestFolder = new File("TempTestFolder");
        if (tempTestFolder.exists() && tempTestFolder.isDirectory()) {
            for (File f : tempTestFolder.listFiles()) {
                Files.deleteIfExists(f.toPath());
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        File tempTestFolder = new File("TempTestFolder");
        if (tempTestFolder.exists() && tempTestFolder.isDirectory()) {
            for (File f : tempTestFolder.listFiles()) {
                Files.deleteIfExists(f.toPath());
            }
        }
    }

    @Test
    public void save() throws Exception {
        File testFile = new File("TempTestFolder", "test.yml");
        Config config = new Config(testFile);
        File copyFromFile = new File("testconfig.yml");
        config.copyDefaults(copyFromFile);
        config.save();

        assertEquals(Files.lines(testFile.toPath()), Files.lines(copyFromFile.toPath()));
    }

}