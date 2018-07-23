/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities;

import java.util.Objects;

/**
 * @author Rsl1122
 * @since 2.0.0
 */
public class Format {

    public static Format create(String string) {
        return new Format(string);
    }

    private String string;

    public Format(String string) {
        this.string = string;
    }

    public Format removeLetters() {
        string = string.replaceAll("[A-Za-z]", "");
        return this;
    }

    public Format removeSymbols() {
        string = string.replaceAll("[^a-zA-Z0-9_\\s]", "");
        return this;
    }

    public Format removeSymbolsButDot() {
        string = string.replaceAll("[^a-zA-Z0-9_\\s\\.]", "");
        return this;
    }

    public Format removeDot() {
        string = string.replaceAll("\\.", "");
        return this;
    }

    public Format removeNumbers() {
        string = string.replaceAll("[0-9]", "");
        return this;
    }

    public Format removeWhitespace() {
        string = string.replaceAll("[\\s]", "");
        return this;
    }

    public Format spaceWhitespace() {
        string = string.replaceAll("[\\s]", " ");
        return this;
    }

    public Format remove(String... regex) {
        Format removed = this;
        for (String reg : regex) {
            removed = this.remove(reg);
        }
        return removed;
    }

    public Format remove(String regex) {
        this.string = string.replaceAll(regex, "");
        return this;
    }

    public Format remove(char... characters) {
        Format removed = this;
        for (char character : characters) {
            removed = this.remove(character);
        }
        return removed;
    }

    public Format remove(char character) {
        this.string = string.replace(String.valueOf(character), "");
        return this;
    }

    public Format justNumbers() {
        return this.removeLetters().removeSymbols().removeWhitespace();
    }

    public Format justLetters() {
        return this.removeNumbers().removeSymbols().removeWhitespace();
    }

    public Format justSymbols() {
        return this.removeNumbers().removeLetters().removeWhitespace();
    }

    public Format upperCase() {
        string = string.toUpperCase();
        return this;
    }

    public Format lowerCase() {
        string = string.toLowerCase();
        return this;
    }

    public Format capitalize() {
        string = (string.charAt(0) + "").toUpperCase() + string.substring(1).toLowerCase();
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
