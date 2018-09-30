package com.djrapitops.plugin.command;

import java.util.Arrays;
import java.util.List;

/**
 * Class that contains ChatColors for plugins.
 * <p>
 * Colors should be defined in MineCraft format, eg. "§1" "§a" "§o§3"
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class ColorScheme {

    private final List<String> colors;

    /**
     * Create a new ColorScheme.
     *
     * @param colors colors in MineCraft format, eg. "§1" "§a" "§o§3"
     */
    public ColorScheme(String... colors) {
        this(Arrays.asList(colors));
    }

    /**
     * Create a new ColorScheme.
     *
     * @param colors colors in MineCraft format, eg. "§1" "§a" "§o§3"
     */
    public ColorScheme(List<String> colors) {
        this.colors = colors;
    }

    /**
     * Get a color with a particular index.
     *
     * @param i Index of the color.
     * @return a color code, eg "§1" or empty string if index is out of bounds.
     */
    public String getColor(int i) {
        if (i < colors.size()) {
            return colors.get(i);
        }
        return "";
    }

    /**
     * Retrieve the first defined color.
     *
     * @return a color code, eg "§1" or empty string if index is out of bounds.
     */
    public String getMainColor() {
        return getColor(0);
    }

    /**
     * Retrieve the second defined color.
     *
     * @return a color code, eg "§1" or empty string if index is out of bounds.
     */
    public String getSecondaryColor() {
        return getColor(1);
    }

    /**
     * Retrieve the third defined color.
     *
     * @return a color code, eg "§1" or empty string if index is out of bounds.
     */
    public String getTertiaryColor() {
        return getColor(2);
    }

    /**
     * Retrieve the fourth defined color.
     *
     * @return a color code, eg "§1" or empty string if index is out of bounds.
     */
    public String getExtraColor() {
        return getColor(3);
    }
}
