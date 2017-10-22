/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.utility.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class FileLogger {

    public static void appendToFile(File file, String... lines) throws IOException {
        appendToFile(file, Arrays.asList(lines));
    }

    public static void logToFile(File file, List<String> lines) throws IOException {
        Files.write(file.toPath(), lines, Charset.forName("UTF-8"));
    }

    public static void appendToFile(File file, List<String> lines) throws IOException {
        if (!file.exists()) {
            if (file.createNewFile()) {
                throw new FileNotFoundException("Failed to create a new file!: " + file.getAbsolutePath());
            }
        }
        Files.write(file.toPath(), lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    }

    public static List<String> readContents(File file) throws IOException {
        return Files.lines(file.toPath(), Charset.forName("UTF-8")).collect(Collectors.toList());
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
}