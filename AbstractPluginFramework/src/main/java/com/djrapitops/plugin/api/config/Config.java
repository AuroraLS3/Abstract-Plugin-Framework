/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.config;

import com.djrapitops.plugin.logging.FileLogger;
import com.djrapitops.plugin.utilities.Verify;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Config class for config management.
 *
 * @author Rsl1122
 */
public class Config extends ConfigNode {

    private static final String APF_NEWLINE = " APF_NEWLINE ";
    private String absolutePath;

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

    public void read() throws IOException {
        File file = getFile();
        Verify.isTrue(file != null, () -> new FileNotFoundException("File was null"));
        Verify.isTrue(file.exists() || file.createNewFile(), () ->
                new FileNotFoundException("Could not create file: " + absolutePath));
        childOrder.clear();
        this.getChildren().clear();
        processLines(readLines(file.toPath()), true);
    }

    public void copyDefaults(File from) throws IOException {
        copyDefaults(readLines(from.toPath()));
    }

    private List<String> readLines(Path from) throws IOException {
        try (Stream<String> s = Files.lines(from, Charset.forName("UTF-8"))) {
            return s.collect(Collectors.toList());
        }
    }

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
                    String comment = value.substring(indexOfHashTag + 1, value.length());
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

    @Override
    public void save() throws IOException {
        File file = getFile();
        Verify.nullCheck(file, () -> new IllegalStateException("Absolute Path was null (Not defined)"));
        Files.write(file.toPath(), processTree(), Charset.forName("UTF-8"));
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