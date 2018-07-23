/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.logging;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility used for logging to files.
 *
 * @author Rsl1122
 */
public class FileLogger {

    public static void appendToFile(File file, String... lines) throws IOException {
        appendToFile(file, Arrays.asList(lines));
    }

    public static void logToFile(File file, List<String> lines) throws IOException {
        Files.write(file.toPath(), lines, getCharset());
    }

    public static void appendToFile(File file, List<String> lines) throws IOException {
        if (!file.exists() && !file.createNewFile()) {
            return;
        }
        Files.write(file.toPath(), lines, getCharset(), StandardOpenOption.APPEND);
    }

    public static List<String> readContents(File file) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath(), getCharset())) {
            return lines.collect(Collectors.toList());
        }
    }

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

    private static Charset getCharset() {
        return Charset.forName("UTF-8");
    }

    private FileLogger() {
        /* Static method class */
    }
}