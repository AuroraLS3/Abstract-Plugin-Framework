/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.config;

import com.djrapitops.plugin.api.utility.log.FileLogger;
import com.djrapitops.plugin.api.utility.log.Log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class Config extends ConfigNode {

    private final File file;

    public Config(File file) {
        super("", null, "");
        this.file = file;
        try {
            read();
        } catch (IOException e) {
            Log.toLog(this.getClass().getName(), e);
        }
    }

    public Config(File file, List<String> defaults) {
        this(file);
        copyDefaults(defaults);
    }

    public void read() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        childOrder.clear();
        this.getChildren().clear();
        processLines(readLines(file), true);
    }

    public void copyDefaults(File from) throws IOException {
        copyDefaults(readLines(from));
    }

    private List<String> readLines(File from) throws IOException {
        try (Stream<String> s = Files.lines(from.toPath(), Charset.forName("UTF-8"))) {
            return s.collect(Collectors.toList());
        }
    }

    public void copyDefaults(List<String> lines) {
        processLines(lines, false);
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
                // Split row String value, List value
                if (keyAndValue.length <= 1) {
                    String lastValue = lastNode.getValue();

                    boolean isListItem = trimmed.startsWith("-");
                    boolean wasListItem = lastValue.contains("APF_NEWLINE") || lastValue.trim().isEmpty();

                    if (isListItem && wasListItem) {
                        lastNode.set(lastValue + " APF_NEWLINE " + trimmed);
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
                int indexOfHashtag = value.lastIndexOf(" #");
                String valueWithoutComment = indexOfHashtag < 0 ? value : value.substring(0, indexOfHashtag);

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
                throw new IllegalStateException("Malformed File (" + file.getName() + "), Error on line " + fileLines.indexOf(line) + ": " + line, e);
            }
        }
    }

    @Override
    public void save() throws IOException {
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
            if (value.startsWith(" APF_NEWLINE ")) {
                // Keyline
                lines.add(b.append(key).append(":").toString());
                // List
                String[] list = value.split(" APF_NEWLINE ");
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