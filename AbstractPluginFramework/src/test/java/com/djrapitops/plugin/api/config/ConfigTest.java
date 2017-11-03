package com.djrapitops.plugin.api.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println("");
        config.save();

        List<String> original = Files.lines(copyFromFile.toPath()).collect(Collectors.toList());
        List<String> test = Files.lines(testFile.toPath()).collect(Collectors.toList());
        boolean different = false;
        for (int i = 0; i < original.size(); i++) {
            String origLine = original.get(i);
            String testLine = test.get(i).replace("    ", "  ");
            if (!origLine.equals(testLine)) {
                System.out.println((i + 1) + "! " + origLine);
                System.out.println((i + 1) + "! " + testLine);
                different = true;
            } else {
                System.out.println((i + 1) + ": " + origLine);
                System.out.println((i + 1) + ": " + testLine);
            }
        }
        assertFalse(different);
    }

}