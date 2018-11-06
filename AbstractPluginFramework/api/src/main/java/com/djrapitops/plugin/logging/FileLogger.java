/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.logging;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Static logging utility for writing lines into files.
 *
 * @author Rsl1122
 */
public class FileLogger {

    private FileLogger() {
        /* Static method class */
    }

    /**
     * Append lines to a file.
     *
     * @param file  File to append to. Should be UTF-8 format.
     * @param lines Lines to append
     * @throws IOException If write fails.
     */
    public static void appendToFile(File file, String... lines) throws IOException {
        appendToFile(file, Arrays.asList(lines));
    }

    /**
     * Write lines to a file, overwriting existing file.
     *
     * @param file  File to write to. Should be UTF-8 format.
     * @param lines Lines to write.
     * @throws IOException If write fails.
     */
    public static void logToFile(File file, List<String> lines) throws IOException {
        Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
    }

    /**
     * Append lines to a file.
     *
     * @param file  File to append to. Should be UTF-8 format.
     * @param lines Lines to append
     * @throws IOException If write fails.
     */
    public static void appendToFile(File file, List<String> lines) throws IOException {
        if (!file.exists() && !file.createNewFile()) {
            return;
        }
        Files.write(file.toPath(), lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    /**
     * Read lines of a File.
     *
     * @param file File to read. Should be UTF-8 format.
     * @return read lines.
     * @throws IOException If read fails.
     */
    public static List<String> readContents(File file) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
            return lines.collect(Collectors.toList());
        }
    }

    /**
     * Get how many space characters are in front of a line.
     *
     * @param line Line to check.
     * @return For example: '  43432' returns 2.
     */
    public static int getIndentation(String line) {
        int indentation = 0;
        for (char c : line.toCharArray()) {
            if (c == ' ') {
                indentation++;
            } else {
                break;
            }
        }
        return indentation;
    }
}