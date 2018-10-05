/*
 * Licence is provided in the jar as license.yml also here:
 * https://github.com/Rsl1122/Plan-PlayerAnalytics/blob/master/Plan/src/main/resources/license.yml
 */
package com.djrapitops.plugin.config;

import com.djrapitops.plugin.utilities.Verify;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a single node in a {@link Config}.
 * <p>
 * Can be used to copy default values to other config nodes with {@link ConfigNode#copyDefaults(ConfigNode)}.
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

    /**
     * Create a new ConfigNode.
     *
     * @param key    Key of the node, eg "Parent" or "Child"
     * @param parent Parent ConfigNode of this node.
     * @param value  Value of this node, null or empty if no value is wanted.
     */
    public ConfigNode(String key, ConfigNode parent, String value) {
        this.key = key;
        this.parent = parent;
        this.value = value != null ? value : "";
        childOrder = new ArrayList<>();
        children = new HashMap<>();
        comment = new ArrayList<>();
    }

    /**
     * Get the parent node of this node.
     *
     * @return parent or null if root node.
     */
    public ConfigNode getParent() {
        return parent;
    }

    /**
     * Get the children of this node.
     *
     * @return Map: String key - ConfigNode node relation.
     */
    public Map<String, ConfigNode> getChildren() {
        return children;
    }

    /**
     * Get keys of the children in the order they are preferred in.
     *
     * @return List of keys of the child nodes.
     */
    public List<String> getKeysInOrder() {
        return childOrder;
    }

    /**
     * Get a String found in relative path.
     *
     * @param path Relative path from this node, eg "Child.Example"
     * @return String found in the path, or empty string if no value exists.
     */
    public String getString(String path) {
        return getConfigNode(path).getString();
    }

    /**
     * Get a String representation of the value in this node.
     *
     * @return String set as value, or empty string if no value exists.
     */
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

    /**
     * Get a boolean found in relative path.
     *
     * @param path Relative path from this node, eg "Child.Example"
     * @return true if the value is set as "true", false otherwise.
     */
    public boolean getBoolean(String path) {
        return getConfigNode(path).getBoolean();
    }

    /**
     * Get boolean representation of this node.
     *
     * @return true if the value is set as "true", false otherwise.
     */
    public boolean getBoolean() {
        return Verify.equalsOne(value, "true", "'true'", "\"true\"");
    }

    /**
     * Get int found found in relative path.
     *
     * @param path Relative path from this node, eg "Child.Example"
     * @return a number if parsable from the String value, 0 otherwise
     */
    public int getInt(String path) {
        return getConfigNode(path).getInt();
    }

    /**
     * Get integer representation of this node.
     *
     * @return a number if parsable from the String value, 0 otherwise
     */
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

    /**
     * Get long found found in relative path.
     *
     * @param path Relative path from this node, eg "Child.Example"
     * @return a number if parsable from the String value, 0 otherwise
     */
    public long getLong(String path) {
        return getConfigNode(path).getLong();
    }

    /**
     * Get long representation of this node.
     *
     * @return a number if parsable from the String value, 0 otherwise
     */
    public long getLong() {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * Get double found found in relative path.
     *
     * @param path Relative path from this node, eg "Child.Example"
     * @return a number if parsable from the String value, 0.0 otherwise
     */
    public double getDouble(String path) {
        return getConfigNode(path).getDouble();
    }

    /**
     * Get double representation of this node.
     *
     * @return a number if parsable from the String value, 0.0 otherwise
     */
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

    /**
     * Get a String list found in relative path.
     *
     * @param path Relative path from this node, eg "Child.Example"
     * @return List of strings defined by this node.
     */
    public List<String> getStringList(String path) {
        return getConfigNode(path).getStringList();
    }

    /**
     * Get a String list represented by this node
     *
     * @return List of strings defined by this node.
     */
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

    /**
     * Get a Integer list found in relative path.
     *
     * @param path Relative path from this node, eg "Child.Example"
     * @return List of integers defined by this node. - If a value is not parsable, 0 instead.
     */
    public List<Integer> getIntList(String path) {
        return getConfigNode(path).getIntList();
    }

    /**
     * Get a Integer list represented by this node
     *
     * @return List of integers defined by this node. - If a value is not parsable, 0 instead.
     */
    public List<Integer> getIntList() {
        return getStringList().stream().map(this::getInteger).collect(Collectors.toList());
    }

    /**
     * Get a ConfigNode found in relative path.
     * <p>
     * If the path points to a non-existing node, new nodes will be created for the path with empty values.
     *
     * @param path Relative path from this node, eg "Child.Example"
     * @return a config node.
     */
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

    /**
     * Get comment present above this node.
     *
     * @return Lines of the comment.
     */
    public List<String> getComment() {
        return comment;
    }

    /**
     * Set comment present above this node.
     *
     * @param comment Lines of the comment.
     */
    public void setComment(List<String> comment) {
        this.comment = comment;
    }

    /**
     * Set a value to config node found in relative path.
     *
     * @param path  Relative path from this node, eg "Child.Example"
     * @param value Value to set the node to. Supports String, Boolean, Long, Double and List and ConfigNode.
     */
    public void set(String path, Object value) {
        ConfigNode node = getConfigNode(path);
        node.set(value);
    }

    /**
     * Set a value to this config node.
     *
     * @param value Value to set the node to. Supports String, Boolean, Long, Double and List and ConfigNode.
     */
    public void set(Object value) {
        if (value instanceof ConfigNode) {
            ConfigNode node = ((ConfigNode) value);
            addChild(node.key, node);
        } else if (value instanceof List) {
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

    /**
     * Find the root node and save.
     *
     * @throws IOException If the save can not be performed.
     * @see Config#save()
     */
    public void save() throws IOException {
        ConfigNode parent = this.parent;
        while (parent.parent != null) {
            parent = parent.parent;
        }
        parent.save();
    }

    /**
     * Get the raw value set to this node.
     *
     * @return Raw String representation in memory.
     */
    public String getValue() {
        return value;
    }

    /**
     * Add a new child to this node.
     *
     * @param name Key of the node.
     * @param node node.
     */
    public void addChild(String name, ConfigNode node) {
        children.put(name, node);
        if (!childOrder.contains(name)) {
            childOrder.add(name);
        }
    }

    /**
     * Get the key of this node.
     *
     * @param deep Should parent keys be appended before this node?
     * @return For example: "Child" or "Parent.Child" if deep is true.
     */
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

    /**
     * Sort the children of this node to alphabetical order
     */
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

    /**
     * Check if a child can be found in a relative path.
     * <p>
     * This method can be used for checking if a node exists to prevent unwanted node creation.
     *
     * @param path Relative path from this node, eg "Child.Example"
     * @return true if found, false if not.
     */
    public boolean contains(String path) {
        String[] split = path.split("\\.", 2);
        ConfigNode child = children.get(split[0]);
        if (child == null) {
            return false;
        }
        if (split.length <= 1) {
            return true;
        }
        return child.contains(split[1]);
    }

    /**
     * Copy default values from a node if no values exist for this node.
     * <p>
     * Non-existing nodes will be added from the given node.
     * <p>
     * Values are only held in memory unless {@link ConfigNode#save()} is called.
     *
     * @param node Node to copy things from.
     */
    public void copyDefaults(ConfigNode node) {
        for (String key : node.childOrder) {
            ConfigNode copyFromNode = node.getConfigNode(key);
            if (!contains(key)) {
                this.addChild(key, copyFromNode);
            } else {
                ConfigNode thisNode = this.getConfigNode(key);
                List<String> comment = thisNode.comment;
                List<String> copyComment = copyFromNode.comment;
                if (comment.size() < copyComment.size()) {
                    thisNode.comment = copyComment;
                }
                thisNode.copyDefaults(copyFromNode);
            }
        }
    }
}