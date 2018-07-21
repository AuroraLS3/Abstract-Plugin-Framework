package com.djrapitops.plugin.settings;

/**
 * Enum containing message components that are used in all plugins.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
@Deprecated
public enum DefaultMessages {
    ARROWS_RIGHT("»"),
    BALL("•");

    private final String text;

    DefaultMessages(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    /**
     * Alternative for toString.
     *
     * @return toString()
     */
    public String parse() {
        return this.toString();
    }

    /**
     * Replaces all REPLACE{x} strings with the given parameters.
     *
     * @param p Strings to replace REPLACE{x}:s with
     * @return String with placeholders replaced.
     */
    public String parse(String... p) {
        String returnValue = this.toString();
        for (int i = 0; i < p.length; i++) {
            returnValue = returnValue.replace("REPLACE" + i, p[i]);
        }
        return returnValue;
    }
}
