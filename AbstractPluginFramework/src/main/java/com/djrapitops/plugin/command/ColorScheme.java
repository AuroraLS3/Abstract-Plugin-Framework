package com.djrapitops.plugin.command;

import java.util.Arrays;
import java.util.List;

/**
 * Class that contains ChatColors for plugins.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class ColorScheme {

    private final List<String> colors;

    public ColorScheme(String... colors) {
        this(Arrays.asList(colors));
    }

    public ColorScheme(List<String> colors) {
        this.colors = colors;
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
