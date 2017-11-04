/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.config;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class ConfigNode {

    private final String key;
    ConfigNode parent;
    List<String> childOrder;
    int depth;
    private Map<String, ConfigNode> children;
    private List<String> comment;

    private String value;

    public ConfigNode(String key, ConfigNode parent, String value) {
        this.key = key;
        this.parent = parent;
        this.value = value;
        childOrder = new ArrayList<>();
        children = new HashMap<>();
        comment = new ArrayList<>();
    }

    public ConfigNode getParent() {
        return parent;
    }

    public Map<String, ConfigNode> getChildren() {
        return children;
    }

    public List<String> getKeysInOrder() {
        return childOrder;
    }

    public String getString(String path) {
        return getConfigNode(path).getString();
    }

    public String getString() {
//        boolean surroundedWithSingleQuotes = value.startsWith("'") && value.endsWith("'");
//        boolean surroundedWithDoubleQuotes = value.startsWith("\"") && value.endsWith("\"");
//        if (surroundedWithSingleQuotes || surroundedWithDoubleQuotes) {
//            value = value.substring(1, value.length() - 2);
//        }
        return value;
    }

    public boolean getBoolean(String path) {
        return getConfigNode(path).getBoolean();
    }

    public boolean getBoolean() {
        return "true".equals(value);
    }

    public int getInt(String path) {
        return getConfigNode(path).getInt();
    }

    public int getInt() {
        return getInteger(value);
    }

    private int getInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public long getLong(String path) {
        return getConfigNode(path).getLong();
    }

    public long getLong() {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public double getDouble(String path) {
        return getConfigNode(path).getDouble();
    }

    public double getDouble() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public List<String> getStringList(String path) {
        return getConfigNode(path).getStringList();
    }

    public List<String> getStringList() {
        String[] lines = value.split(" APF_NEWLINE ");
        return Arrays.asList(lines);
    }

    public List<Integer> getIntList(String path) {
        return getConfigNode(path).getIntList();
    }

    public List<Integer> getIntList() {
        return getStringList().stream().map(this::getInteger).collect(Collectors.toList());
    }

    public ConfigNode getConfigNode(String path) {
        String[] split = path.split("\\.");
        ConfigNode node = this;
        for (String key : split) {
            node = node.children.get(key);
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }

    public void set(Object value) {
        this.value = value.toString();
    }

    public void save() throws IOException {
        ConfigNode parent = this.parent;
        while (parent.parent != null) {
            parent = parent.parent;
        }
        parent.save();
    }

    public String getValue() {
        return value;
    }

    public void addChild(String name, ConfigNode node) {
        children.put(name, node);
        childOrder.add(name);
    }

    public String getKey(boolean deep) {
        if (deep) {
            if (parent != null) {
                String s = parent.getKey(true) + "." + key;
                if (s.startsWith(".")) {
                    return s.substring(1);
                }
                return s;
            }
        }
        return key;
    }

    public void sort() {
        Collections.sort(childOrder);
    }
}