/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
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
package com.djrapitops.plugin.config;

import com.djrapitops.plugin.logging.FileLogger;
import com.djrapitops.plugin.utilities.Verify;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * In-memory configuration object for managing .yml like configuration files.
 * <p>
 * This config model does not comply with Bukkit or Bungee .yml parsing as it contains features that are not present,
 * like defining values for parent nodes.
 * <p>
 * Paths are separated with dots, eg: 'Parent.Child'.
 * <p>
 * If a non-existing node is attempted to be read, it will be initialized with an empty value.
 *
 * @author AuroraLS3
 * @see ConfigNode for variable accessing methods.
 * @deprecated Various bugs have been identified in this class and it is discouraged to continue using this utility.
 */
@Deprecated
public class Config extends ConfigNode {

    private static final String APF_NEWLINE = " APF_NEWLINE ";
    private final String absolutePath;

    /**
     * Create a Config by reading a file.
     * <p>
     * If the file does not exist, a new file is created at its path.
     *
     * @param file File to read/create the config from.
     */
    public Config(File file) {
        super("", null, "");
        File folder = file.getParentFile();
        this.absolutePath = file.getAbsolutePath();

        try {
            Verify.isTrue(folder.exists() || folder.mkdirs(), () ->
                    new FileNotFoundException("Folders could not be created for config file " + absolutePath));
            Verify.isTrue(file.exists() || file.createNewFile(), () ->
                    new FileNotFoundException("Could not create file: " + absolutePath));
            read();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * Create a config by reading a file and setting defaults.
     * <p>
     * If the file does not exist, a new file is created at its path.
     *
     * @param file     File to read/create the config from.
     * @param defaults lines of a read config file - indentation is required.
     */
    public Config(File file, List<String> defaults) {
        this(file);
        copyDefaults(defaults);
    }

    private Config(List<String> defaults) {
        super("", null, "");
        absolutePath = null;
        processLines(defaults, true);
    }

    private File getFile() {
        return absolutePath != null ? new File(absolutePath) : null;
    }

    /**
     * Read the values from the file defined at construction.
     * <p>
     * Replaces values in memory.
     *
     * @throws IOException If the file can not be read.
     */
    public void read() throws IOException {
        File file = getFile();
        Verify.isTrue(file != null, () -> new FileNotFoundException("File was null"));
        Verify.isTrue(file.exists() || file.createNewFile(), () ->
                new FileNotFoundException("Could not create file: " + absolutePath));
        childOrder.clear();
        this.getChildren().clear();
        processLines(readLines(file.toPath()), true);
    }

    /**
     * Copies defaults from an existing file.
     * <p>
     * Changes are not written to the config until {@link Config#save()} is called.
     *
     * @param from File to read config values from.
     * @throws IOException If the file can not be read.
     */
    public void copyDefaults(File from) throws IOException {
        copyDefaults(readLines(from.toPath()));
    }

    private List<String> readLines(Path from) throws IOException {
        try (Stream<String> s = Files.lines(from, StandardCharsets.UTF_8)) {
            return s.collect(Collectors.toList());
        }
    }

    /**
     * Copies defaults from existing lines, indentation is required.
     * <p>
     * Changes are not written to the config until {@link Config#save()} is called.
     *
     * @param lines Lines read from a file.
     */
    public void copyDefaults(List<String> lines) {
        copyDefaults(new Config(lines));
    }

    private void processLines(List<String> fileLines, boolean override) {
        List<String> comments = new ArrayList<>();
        int lastDepth = 0;
        ConfigNode parent = this;
        ConfigNode lastNode = this;
        for (String line : fileLines) {
            try {
                int depth = FileLogger.getIndentation(line);

                String trimmed = line.trim();
                // Comment
                if (trimmed.startsWith("#")) {
                    comments.add(trimmed);
                    continue;
                }

                String[] keyAndValue = trimmed.split(":", 2);
                if (keyAndValue.length <= 1) {
                    // Check if Split row String value or List value
                    String lastValue = lastNode.getValue();

                    boolean isListItem = trimmed.startsWith("-");
                    boolean wasListItem = lastValue.contains(APF_NEWLINE.trim()) || lastValue.trim().isEmpty();

                    if (isListItem && wasListItem) {
                        lastNode.set(lastValue + APF_NEWLINE + trimmed);
                    } else {
                        if ((lastValue.startsWith("\"") && trimmed.endsWith("\""))
                                || (lastValue.startsWith("'") && trimmed.endsWith("'"))) {
                            lastNode.set(lastValue.substring(1) + " " + trimmed.substring(0, trimmed.length() - 1));
                        } else {
                            lastNode.set(lastValue + " " + trimmed);
                        }
                    }

                    continue;
                }
                String configKey = keyAndValue[0];

                if (depth > lastDepth) {
                    parent = lastNode;
                } else if (depth < lastDepth) {
                    // Prevents incorrect indent in the case:
                    // 1:
                    //   2:
                    //     3:
                    // 1:
                    int nDepth = lastDepth;
                    while (nDepth > depth) {
                        nDepth = parent.depth;
                        parent = parent.parent;
                    }
                }

                String value = keyAndValue[1].trim();
                int indexOfHashTag = value.lastIndexOf(" #");
                String valueWithoutComment = indexOfHashTag < 0 ? value : value.substring(0, indexOfHashTag);
                if (indexOfHashTag > 0) {
                    String comment = value.substring(indexOfHashTag + 1);
                    comments.add(comment);
                }

                ConfigNode node = parent.getChildren().get(configKey);
                if (override || node == null) {
                    node = new ConfigNode(configKey, parent, valueWithoutComment);
                }
                node.depth = depth;
                node.setComment(new ArrayList<>(comments));
                comments.clear();
                lastNode = node;
                lastDepth = depth;
                parent.addChild(configKey, node);
            } catch (Exception e) {
                throw new IllegalStateException("Malformed File (" + absolutePath + "), Error on line " + fileLines.indexOf(line) + ": " + line, e);
            }
        }
    }

    /**
     * Save the config values to the file defined during construction.
     *
     * @throws IOException           If the file can not be written.
     * @throws IllegalStateException If the file path is null.
     */
    @Override
    public void save() throws IOException {
        File file = getFile();
        Verify.nullCheck(file, () -> new IllegalStateException("Absolute Path was null (Not defined)"));
        Files.write(file.toPath(), processTree(), StandardCharsets.UTF_8);
    }

    private List<String> processTree() {
        return getLines(this, 0);
    }

    private List<String> getLines(ConfigNode root, int depth) {
        List<String> lines = new ArrayList<>();
        Map<String, ConfigNode> children = root.getChildren();

        for (String key : root.childOrder) {
            ConfigNode node = children.get(key);
            String value = node.getValue();

            for (String commentLine : node.getComment()) {
                StringBuilder comment = new StringBuilder();
                addIndentation(depth, comment);
                comment.append(commentLine);
                lines.add(comment.toString());
            }

            StringBuilder b = new StringBuilder();
            addIndentation(depth, b);
            if (value.startsWith(APF_NEWLINE)) {
                // Keyline
                lines.add(b.append(key).append(":").toString());
                // List
                String[] list = value.split(APF_NEWLINE);
                for (String listValue : list) {
                    String v = listValue.trim();
                    if (v.isEmpty()) {
                        continue;
                    }
                    StringBuilder listBuilder = new StringBuilder();
                    addIndentation(depth + 1, listBuilder);
                    listBuilder.append(v);
                    lines.add(listBuilder.toString());
                }
            } else {
                if (value.isEmpty()) {
                    b.append(key).append(":");
                } else {
                    b.append(key).append(": ").append(value);
                }
                lines.add(b.toString());
            }
            lines.addAll(getLines(node, depth + 1));
        }
        return lines;
    }

    private void addIndentation(int depth, StringBuilder b) {
        for (int i = 0; i < depth; i++) {
            b.append("    ");
        }
    }
}