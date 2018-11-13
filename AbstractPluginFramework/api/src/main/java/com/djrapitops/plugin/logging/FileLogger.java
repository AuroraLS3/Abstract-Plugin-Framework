/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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