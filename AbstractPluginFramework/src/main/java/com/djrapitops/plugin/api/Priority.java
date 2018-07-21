package com.djrapitops.plugin.api;

@Deprecated
public enum Priority {
    LOW("§e"),
    MEDIUM("§6"),
    HIGH("§c");

    private final String color;

    Priority(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}