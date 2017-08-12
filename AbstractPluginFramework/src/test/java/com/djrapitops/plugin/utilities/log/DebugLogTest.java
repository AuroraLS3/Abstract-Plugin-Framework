package com.djrapitops.plugin.utilities.log;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DebugLogTest {

    private PluginLog logger;
    private File tempTestFolder;
    private File debug;

    @Before
    public void setUp() throws IOException {
        tempTestFolder = new File("TempTestFolder");
        if (tempTestFolder.exists() && tempTestFolder.isDirectory()) {
            for (File f : tempTestFolder.listFiles()) {
                Files.deleteIfExists(f.toPath());
            }
        }
        logger = new PluginLog("true", "[Test]", tempTestFolder) {
            @Override
            public void info(String message) {
            }

            @Override
            public void infoColor(String message) {

            }

            @Override
            public void error(String message) {

            }
        };
        debug = new File(tempTestFolder, "DebugLog.txt");
        assertTrue(!debug.exists());
    }

    @Test
    public void testDebugAddlineMultithread() {
        logger.toLog("Test", new IllegalArgumentException("Test"));

        List<Integer> count = new CopyOnWriteArrayList<>();
        List<Integer> fail = new CopyOnWriteArrayList<>();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;

                    while (i < 100) {
                        logger.getDebug("Test").addLine("Test" + i);
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    fail.add(1);
                } finally {
                    count.add(1);
                }
            }
        };
        Set<Thread> threads = new HashSet<>();
        for (int x = 0; x < 100; x++) {
            threads.add(new Thread(runnable));
        }
        for (Thread t : threads) {
            t.start();
        }
        int size = 0;
        while ((size = count.size()) < 100) {
            if (size >= 100) {
                break;
            }
        }
        for (Thread t : threads) {
            t.stop();
        }
        assertEquals(0, fail.size());
    }

    @Test
    public void testDebugToLogMultithreadForException() throws Exception {
        List<Integer> count = new CopyOnWriteArrayList<>();
        List<Integer> fail = new CopyOnWriteArrayList<>();

        Runnable runnable = new Runnable() {
            private boolean run = false;

            @Override
            public void run() {
                try {
                    int i = 0;

                    while (i < 100) {
                        logger.getDebug("Test").addLine(String.valueOf(i)).toLog();
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    fail.add(1);
                } finally {
                    count.add(1);
                }
            }
        };
        Set<Thread> threads = new HashSet<>();
        for (int x = 0; x < 100; x++) {
            threads.add(new Thread(runnable));
        }
        System.out.println("Threads: " + threads.size());
        for (Thread t : threads) {
            t.start();
        }
        int size = 0;
        while ((size = count.size()) < 100) {
            if (size >= 100) {
                break;
            }
        }
        for (Thread t : threads) {
            t.stop();
        }
        assertEquals(0, fail.size());
    }
}
