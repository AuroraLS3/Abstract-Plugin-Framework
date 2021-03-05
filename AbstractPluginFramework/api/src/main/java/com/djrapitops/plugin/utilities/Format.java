/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.djrapitops.plugin.utilities;

import java.util.Objects;

/**
 * Utility for formatting Strings.
 *
 * @author AuroraLS3
 * @since 2.0.0
 */
public class Format {

    private String string;

    /**
     * Create a new Format utility.
     *
     * @param string String to modify.
     */
    public Format(String string) {
        this.string = string;
    }

    @Deprecated
    public static Format create(String string) {
        return new Format(string);
    }

    /**
     * Remove all letters using regex.
     *
     * @return called formatter.
     */
    public Format removeLetters() {
        string = string.replaceAll("[A-Za-z]", "");
        return this;
    }

    /**
     * Remove all symbols using regex.
     *
     * @return called formatter.
     */
    public Format removeSymbols() {
        string = string.replaceAll("[^a-zA-Z0-9_\\s]", "");
        return this;
    }

    /**
     * Remove all symbols, except '.', using regex.
     *
     * @return called formatter.
     */
    public Format removeSymbolsButDot() {
        string = string.replaceAll("[^a-zA-Z0-9_\\s\\.]", "");
        return this;
    }

    /**
     * Remove dots using regex.
     *
     * @return called formatter.
     */
    public Format removeDot() {
        string = string.replaceAll("\\.", "");
        return this;
    }

    /**
     * Remove all numbers using regex.
     *
     * @return called formatter.
     */
    public Format removeNumbers() {
        string = string.replaceAll("[0-9]", "");
        return this;
    }

    /**
     * Remove all whitespace using regex.
     *
     * @return called formatter.
     */
    public Format removeWhitespace() {
        string = string.replaceAll("[\\s]", "");
        return this;
    }

    /**
     * Replace all whitespace with spaces.
     *
     * @return called formatter.
     */
    public Format spaceWhitespace() {
        string = string.replaceAll("[\\s]", " ");
        return this;
    }

    /**
     * Remove parts using multiple regex patterns.
     *
     * @param regex Patterns
     * @return called formatter.
     */
    public Format remove(String... regex) {
        Format removed = this;
        for (String reg : regex) {
            removed = this.remove(reg);
        }
        return removed;
    }

    /**
     * Remove parts using a regex pattern.
     *
     * @param regex Pattern.
     * @return called formatter.
     */
    public Format remove(String regex) {
        this.string = string.replaceAll(regex, "");
        return this;
    }

    /**
     * Remove all of specific characters.
     *
     * @param characters Characters to remove.
     * @return called formatter.
     */
    public Format remove(char... characters) {
        Format removed = this;
        for (char character : characters) {
            removed = this.remove(character);
        }
        return removed;
    }

    /**
     * Remove all of a specific character.
     *
     * @param character Character to remove.
     * @return called formatter.
     */
    public Format remove(char character) {
        this.string = string.replace(String.valueOf(character), "");
        return this;
    }

    /**
     * Remove everything but numbers.
     *
     * @return called formatter.
     */
    public Format justNumbers() {
        return this.removeLetters().removeSymbols().removeWhitespace();
    }

    /**
     * Remove everything but letters.
     *
     * @return called formatter.
     */
    public Format justLetters() {
        return this.removeNumbers().removeSymbols().removeWhitespace();
    }

    /**
     * Remove everything but symbols.
     *
     * @return called formatter.
     */
    public Format justSymbols() {
        return this.removeNumbers().removeLetters().removeWhitespace();
    }

    /**
     * Uppercase the string.
     *
     * @return called formatter.
     */
    public Format upperCase() {
        string = string.toUpperCase();
        return this;
    }

    /**
     * Lowercase the string.
     *
     * @return called formatter.
     */
    public Format lowerCase() {
        string = string.toLowerCase();
        return this;
    }

    /**
     * Capitalize the first letter and lowercase the rest.
     *
     * @return called formatter.
     */
    public Format capitalize() {
        string = (string.charAt(0) + "").toUpperCase() + string.substring(1).toLowerCase();
        return this;
    }

    /**
     * Remove both end characters of the string.
     *
     * @return called formatter.
     */
    public Format removeFirstAndLastChar() {
        string = string.substring(1, string.length() - 1);
        return this;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.string);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Format other = (Format) obj;
        return Objects.equals(this.string, other.string);
    }

}
