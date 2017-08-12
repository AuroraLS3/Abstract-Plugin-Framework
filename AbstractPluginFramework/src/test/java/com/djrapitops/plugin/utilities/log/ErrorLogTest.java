package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.utilities.Verify;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ErrorLogTest {

    private PluginLog logger;
    private File tempTestFolder;
    private File errors;

    @Before
    public void setUp() throws IOException {
        tempTestFolder = new File("TempTestFolder");
        if (tempTestFolder.exists() && tempTestFolder.isDirectory()) {
            for (File f : tempTestFolder.listFiles()) {
                Files.deleteIfExists(f.toPath());
            }
        }
        logger = new PluginLog("console", "[Test]", tempTestFolder) {
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
        errors = new File(tempTestFolder, "Errors.txt");
        assertTrue(!errors.exists());
    }

    @Test
    public void toLog() throws Exception {
        IllegalStateException testException = new IllegalStateException("Test");
        logger.toLog("Test", testException);
        assertTrue(errors.exists());
        List<String> lines = null;
        try (Stream<String> lineStream = Files.lines(errors.toPath(), StandardCharsets.UTF_8)) {
            lines = lineStream.collect(Collectors.toList());
        }
        assertTrue(!Verify.isEmpty(lines));
        logger.toLog("Test", new IllegalArgumentException("Test"));
        for (int i = 0; i < 5; i++) {
            logger.toLog("Test", testException);
        }
        ErrorLogManager errorLogManager = logger.getErrorLogManager();
        List<ErrorObject> errorObjects = errorLogManager.readFile();
        assertTrue(errorObjects.toString(), 2 == errorObjects.size());
    }

    @Test
    public void toLog2() throws Exception {
        for (int i = 0; i < 30; i++) {
            logger.toLog("Test", new IllegalStateException("Test" + i));
        }
        assertTrue(errors.exists());
        List<String> lines = null;
        try (Stream<String> lineStream = Files.lines(errors.toPath(), StandardCharsets.UTF_8)) {
            lines = lineStream.collect(Collectors.toList());
        }
        assertTrue(!Verify.isEmpty(lines));
        ErrorLogManager errorLogManager = logger.getErrorLogManager();
        List<ErrorObject> errorObjects = errorLogManager.readFile();
        assertTrue(errorObjects.toString(), 30 == errorObjects.size());
    }

    @Test
    public void toLogMultiple() throws Exception {
        List<Throwable> test = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            test.add(new IllegalStateException("Test"));
        }
        logger.toLog("Test", test);
        assertTrue(errors.exists());
        List<String> lines = null;
        try (Stream<String> lineStream = Files.lines(errors.toPath(), StandardCharsets.UTF_8)) {
            lines = lineStream.collect(Collectors.toList());
        }
        assertTrue(!Verify.isEmpty(lines));
        ErrorLogManager errorLogManager = logger.getErrorLogManager();
        List<ErrorObject> errorObjects = errorLogManager.readFile();
        assertTrue(errorObjects.toString(), 1 == errorObjects.size());
    }

    @Test
    public void toLogMultiThread() throws Exception {

        List<Integer> count = new CopyOnWriteArrayList<>();
        List<Integer> fail = new CopyOnWriteArrayList<>();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;

                    while (i < 500) {
                        logger.toLog("Test" + i, new IllegalStateException("Test"));
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
        assertTrue(errors.exists());
        List<String> lines = null;
        try (Stream<String> lineStream = Files.lines(errors.toPath(), StandardCharsets.UTF_8)) {
            lines = lineStream.collect(Collectors.toList());
        }
        assertTrue(!Verify.isEmpty(lines));
        ErrorLogManager errorLogManager = logger.getErrorLogManager();
        List<ErrorObject> errorObjects = errorLogManager.readFile();
        assertEquals(500, errorObjects.size());
    }

    @Test
    public void logReadMultithread() throws Exception {
        logger.toLog("Test", new IllegalArgumentException("Test"));

        List<Integer> count = new CopyOnWriteArrayList<>();
        List<Integer> fail = new CopyOnWriteArrayList<>();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;

                    while (i < 100) {
                        logger.getErrorLogManager().readFile();
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
            t.interrupt();
        }
        assertEquals(0, fail.size());
    }
}