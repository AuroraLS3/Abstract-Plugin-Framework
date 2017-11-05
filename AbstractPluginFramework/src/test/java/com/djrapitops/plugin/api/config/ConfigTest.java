package com.djrapitops.plugin.api.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class ConfigTest {

    private Config config;
    private File testFile;
    private File copyFromFile;

    @Before
    public void setUp() throws Exception {
        File tempTestFolder = new File("TempTestFolder");
        if (tempTestFolder.exists() && tempTestFolder.isDirectory()) {
            for (File f : tempTestFolder.listFiles()) {
                Files.deleteIfExists(f.toPath());
            }
        }
        testFile = new File("TempTestFolder", "test.yml");
        config = new Config(testFile);
        copyFromFile = new File("testconfig.yml");
        config.copyDefaults(copyFromFile);
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
        System.out.println("Testing save");
        config.save();

        List<String> original = readLines(copyFromFile);
        List<String> test = readLines();
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

    @Test
    public void set() throws IOException {
        config.save();
        config.set("Plugins.Example2.Block", "Test");
        config.save();
        List<String> lines = readLines();
        assertTrue(lines.contains("    Example2:"));
        assertTrue(lines.contains("        Block: Test"));
        config.set("Plugins.Example2.Block", "Test2");
        config.save();
        lines = readLines();
        lines.forEach(System.out::println);
        assertTrue(lines.contains("        Block: Test2"));
    }

    private List<String> readLines() throws IOException {
        return readLines(testFile);
    }

    private List<String> readLines(File file) throws IOException {
        try (Stream<String> s = Files.lines(file.toPath(), Charset.forName("UTF-8"))) {
            return s.collect(Collectors.toList());
        }
    }

    @Test
    public void stringList() throws IOException {
        List<String> stringList = config.getStringList("Plugins.Example.Block");
        List<String> expected = Arrays.asList("This", "That");
        assertEquals(expected, stringList);

        stringList.add("Thot");
        config.set("Plugins.Example.Block", stringList);
        config.save();

        List<String> lines = readLines();
        lines.forEach(System.out::println);
        assertTrue(lines.contains("            - Thot"));
        stringList = config.getStringList("Plugins.Example.Block");
        assertTrue(stringList.contains("Thot"));
    }

    @Test
    public void intList() throws IOException {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        config.set("Test.IntList", expected);
        config.save();
        readLines().forEach(System.out::println);
        List<Integer> intList = config.getIntList("Test.IntList");
        assertEquals(expected, intList);

        expected = Arrays.asList(1, 2, 3, 5);
        config.set("Test.IntList", expected);
        config.save();
        readLines().forEach(System.out::println);
        intList = config.getIntList("Test.IntList");
        assertEquals(expected, intList);
    }

    @Test
    public void read() throws IOException {
        config.save();
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        config.set("Test.IntList", expected);
        assertFalse(config.getIntList("Test.IntList").isEmpty());
        config.read();
        List<Integer> test = config.getIntList("Test.IntList");
        assertTrue(test.toString(), test.isEmpty());
    }

    @Test
    public void tDouble() throws IOException {
        double aDouble = config.getDouble("Double");
        double bDouble = config.getDouble("Double2");
        assertTrue(aDouble + "", 0.043 == aDouble);
        assertTrue(bDouble + "", 0.043 == bDouble);
    }

    @Test
    public void commentString() {
        assertEquals("#.##", config.getString("Customization.Formatting.DecimalPoints"));
        assertEquals("1 year, ", config.getString("Customization.Formatting.TimeAmount.Year"));
        assertEquals("", config.getString("Customization.F"));
        assertEquals("0s", config.getString("Customization.Formatting.TimeAmount.Zero"));
    }

    @Test
    public void wrapWithQuotes() throws IOException {
        config.set("Customization.Formatting.DecimalPoints", "#.###");
        config.set("T", "'Test', 'Two'");
        config.save();
        config.read();
        readLines().forEach(System.out::println);
        assertEquals("'Test', 'Two'", config.getString("T"));
        assertEquals("#.###", config.getString("Customization.Formatting.DecimalPoints"));
    }

    @Test
    public void copyDefaults() throws IOException {
        config.save();
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        config.set("Test.IntList", expected);
        config.set("Plugin.Locale", "EN");
        assertEquals(expected, config.getIntList("Test.IntList"));
        config.copyDefaults(copyFromFile);
        config.save();
        readLines().forEach(System.out::println);
        assertEquals(expected, config.getIntList("Test.IntList"));
        assertEquals("EN", config.getString("Plugin.Locale"));
    }

    @Test
    public void integer() {
        assertEquals(8804, config.getInt("WebServer.Port"));
        assertEquals(0, config.getInt("WebServer.P"));
    }

    @Test
    public void bool() {
        assertTrue(config.getBoolean("Plugin.Debug"));
        assertTrue(config.getBoolean("StringBool"));
        assertFalse(config.getBoolean("Plugin.D"));
    }
}