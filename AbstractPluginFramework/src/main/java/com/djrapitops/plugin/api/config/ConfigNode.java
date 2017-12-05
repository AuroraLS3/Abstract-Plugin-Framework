/* 
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.api.config;

import com.djrapitops.plugin.utilities.Verify;

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
        return getStringFrom(value);
    }

    private String getStringFrom(String value) {
        String s = value.trim();
        boolean surroundedWithSingleQuotes = s.startsWith("'") && s.endsWith("'");
        boolean surroundedWithDoubleQuotes = s.startsWith("\"") && s.endsWith("\"");
        if (surroundedWithSingleQuotes || surroundedWithDoubleQuotes) {
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

    public boolean getBoolean(String path) {
        return getConfigNode(path).getBoolean();
    }

    public boolean getBoolean() {
        return Verify.equalsOne(value, "true", "'true'", "\"true\"");
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
            try {
                return Double.parseDouble(value.replace(',', '.'));
            } catch (NumberFormatException e2) {
                return 0.0;
            }
        }
    }

    public List<String> getStringList(String path) {
        return getConfigNode(path).getStringList();
    }

    public List<String> getStringList() {
        String[] lines = value.split(" APF_NEWLINE ");
        List<String> values = new ArrayList<>();
        for (String line : lines) {
            String trim = line.trim();
            if (trim.isEmpty()) {
                continue;
            }
            if (trim.startsWith("-")) {
                String s = trim.substring(1).trim();
                values.add(getStringFrom(s));
            } else {
                if (values.isEmpty()) {
                    values.add(trim);
                } else {
                    String oldValue = values.get(values.size() - 1);
                    values.remove(oldValue);
                    values.add(getStringFrom(oldValue + trim));
                }
            }
        }
        return values;
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
            ConfigNode parent = node;
            node = node.children.get(key);
            if (node == null) {
                ConfigNode newN = new ConfigNode(key, parent, "");
                parent.addChild(key, newN);
                node = newN;
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

    public void set(String path, Object value) {
        String[] split = path.split("\\.");
        ConfigNode node = getConfigNode(path);
        node.set(value);
    }

    public void set(Object value) {
        if (value instanceof List) {
            StringBuilder valueBuilder = new StringBuilder();
            for (Object o : ((List) value)) {
                valueBuilder.append(" APF_NEWLINE - ").append(o.toString());
            }
            this.value = valueBuilder.toString();
        } else {
            String s = value.toString();
            if (s.startsWith("'") && s.endsWith("'")) {
                s = '"' + s + '"';
            } else if (s.startsWith("#") || (s.startsWith("\"") && s.endsWith("\""))) {
                s = "'" + s + "'";
            }
            this.value = s;
        }
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
        if (!childOrder.contains(name)) {
            childOrder.add(name);
        }
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

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder(key);
        toString.append(":\n");
        for (String key : childOrder) {
            toString.append(this.key).append(".").append(children.get(key).toString());
        }
        return toString.toString();
    }

    public boolean contains(String key) {
        String[] split = key.split("\\.", 2);
        ConfigNode child = children.get(split[0]);
        if (child == null) {
            return false;
        }
        if (split.length <= 1) {
            return true;
        }
        return child.contains(split[1]);
    }

    public void copyDefaults(ConfigNode config) {
        for (String key : config.childOrder) {
            ConfigNode copyFromNode = config.getConfigNode(key);
            if (!contains(key)) {
                this.addChild(key, copyFromNode);
            } else {
                ConfigNode thisNode = this.getConfigNode(key);
                List<String> copyComment = copyFromNode.comment;
                if (!copyComment.isEmpty()) {
                    thisNode.comment = copyComment;
                }
                thisNode.copyDefaults(copyFromNode);
            }
        }
    }
}