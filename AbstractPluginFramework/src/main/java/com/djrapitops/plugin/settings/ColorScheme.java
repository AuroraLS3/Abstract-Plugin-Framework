package com.djrapitops.plugin.settings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that contains ChatColors for Bukkit plugins.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class ColorScheme {

    private final List<String> colors;

    public ColorScheme(org.bukkit.ChatColor... colors) {
        this.colors = Arrays.stream(colors).map(c -> c.toString()).collect(Collectors.toList());
    }

    public ColorScheme(net.md_5.bungee.api.ChatColor... colors) {
        this.colors = Arrays.stream(colors).map(c -> c.toString()).collect(Collectors.toList());
    }

    public String getColor(int i) {
        final String color = colors.get(i);
        if (color != null) {
            return color;
        }
        return "";
    }

    public String getMainColor() {
        return getColor(0);
    }

    public String getSecondaryColor() {
        return getColor(1);
    }

    public String getTertiaryColor() {
        return getColor(2);
    }

    public String getExtraColor() {
        return getColor(3);
    }
}
