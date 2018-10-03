package com.djrapitops.plugin.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ConfigTest {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Config config;
    private File testFile;
    private File copyFromFile;

    @Before
    public void setUp() throws Exception {
        testFile = new File(temporaryFolder.getRoot(), "test.yml");
        config = new Config(testFile);
        copyFromFile = new File("src/test/resources/testconfig.yml");
        config.copyDefaults(copyFromFile);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void save() throws Exception {
        config.save();

        List<String> original = readLines(copyFromFile);
        List<String> test = readLines();

        StringBuilder differing = new StringBuilder();
        for (int i = 0; i < original.size(); i++) {
            String origLine = original.get(i);
            String testLine = test.get(i).replace("    ", "  ");
            if (!origLine.equals(testLine)) {
                differing.append(i + 1).append("! ").append(origLine).append("\n");
                differing.append(i + 1).append("! ").append(testLine).append("\n\n");
            }
        }
        assertEquals(differing.toString(), 0, differing.length());
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
        assertTrue(lines.contains("            - Thot"));
        stringList = config.getStringList("Plugins.Example.Block");
        assertTrue(stringList.contains("Thot"));
    }

    @Test
    public void intList() throws IOException {
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        config.set("Test.IntList", expected);
        config.save();
        List<Integer> intList = config.getIntList("Test.IntList");
        assertEquals(expected, intList);

        expected = Arrays.asList(1, 2, 3, 5);
        config.set("Test.IntList", expected);
        config.save();
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
    public void tDouble() {
        double aDouble = config.getDouble("Double");
        double bDouble = config.getDouble("Double2");
        assertEquals(aDouble + "", 0.043, aDouble, 0.0);
        assertEquals(bDouble + "", 0.043, bDouble, 0.0);
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
        assertEquals("'Test', 'Two'", config.getString("T"));
        assertEquals("#.###", config.getString("Customization.Formatting.DecimalPoints"));
    }

    @Test
    public void unWrapQuotes() {
        assertEquals("\"#0099C6\", \"#66AA00\", \"#316395\", \"#994499\", \"#22AA99\", \"#AAAA11\", \"#6633CC\", \"#E67300\", \"#329262\", \"#5574A6\"",
                config.getString("Theme.Graphs.WorldPie"));
    }

    @Test
    public void copyDefaultsNoOverride() throws IOException {
        config.save();
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        config.set("Test.IntList", expected);
        config.set("Plugin.Locale", "EN");
        List<String> expected2 = Arrays.asList("This", "That", "Thot");
        config.set("Plugins.Example.Block", expected2);
        assertEquals(expected, config.getIntList("Test.IntList"));
        config.copyDefaults(copyFromFile);
        config.save();
        assertEquals(expected, config.getIntList("Test.IntList"));
        assertEquals(expected2, config.getStringList("Plugins.Example.Block"));
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

    @Test
    public void testStringListItemDuplication() throws IOException {
        config.save();
        assertEquals(2, config.getStringList("Plugins.Example.Block").size());
        config.read();
        assertEquals(2, config.getStringList("Plugins.Example.Block").size());
        config.save();
        assertEquals(2, config.getStringList("Plugins.Example.Block").size());
        config.read();
        assertEquals(2, config.getStringList("Plugins.Example.Block").size());
        config.copyDefaults(copyFromFile);
        assertEquals(2, config.getStringList("Plugins.Example.Block").size());
    }

    @Test
    public void containsTest() {
        assertTrue(config.contains("Plugins"));
        assertTrue(config.contains("Plugins.Example"));
        assertTrue(config.contains("Plugins.Example.Block"));
    }

    @Test
    public void dashNameBoolean() throws IOException {
        testStringListItemDuplication();
        assertTrue(config.getBoolean("Plugin.Bungee-Override.CopyBungeeConfig"));
        assertFalse(config.getBoolean("Plugin.Bungee-Override.StandaloneMode"));
    }

    @Test
    public void nullFileConstructorProducesNPE() throws IOException {
        exception.expect(NullPointerException.class);
        Config config = new Config(null);
        config.save();
    }

    @Test
    public void nonexistentConfigFileDoesNotProduceNPE() throws IOException {
        File nonExistent = new File(temporaryFolder.getRoot(), "nonexistent.yml");
        Config config = new Config(nonExistent);
        ConfigNode node = config.getConfigNode("Test");
        node.set("Example.Test", true);
        node.save();

        assertTrue(config.getBoolean("Test.Example.Test"));
        assertTrue(nonExistent.exists());
    }

    @Test
    public void nonexistentFolderDoesNotProduceNPE() throws IOException {
        File nonExistentFolder = new File(temporaryFolder.getRoot(), "NoFolder");
        File nonExistent = new File(nonExistentFolder, "nonexistent.yml");
        Config config = new Config(nonExistent);
        ConfigNode node = config.getConfigNode("Test");
        node.set("Example.Test", true);
        node.save();

        assertTrue(config.getBoolean("Test.Example.Test"));
        assertTrue(nonExistent.exists());
        assertTrue(nonExistentFolder.exists());
    }

    @Test
    public void nonexistentFolderDoesNotProduceNPEAfterCopyingDefaults() throws IOException {
        File nonExistentFolder = new File(temporaryFolder.getRoot(), "NoFolder");
        File nonExistent = new File(nonExistentFolder, "nonexistent.yml");
        Config config = new Config(nonExistent);
        config.copyDefaults(copyFromFile);
        config.save();
        ConfigNode node = config.getConfigNode("Test");
        node.set("Example.Test", true);
        node.save();

        assertTrue(config.getBoolean("Test.Example.Test"));
        assertTrue(nonExistent.exists());
        assertTrue(nonExistentFolder.exists());
    }
}