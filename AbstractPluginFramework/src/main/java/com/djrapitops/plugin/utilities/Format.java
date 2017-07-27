/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities;

import java.util.Objects;

/**
 *
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
        string = FormattingUtils.removeLetters(string);
        return this;
    }

    public Format removeSymbols() {
        string = FormattingUtils.removeSymbols(string);
        return this;
    }
    public Format removeSymbolsButDot() {
        string = FormattingUtils.removeSymbolsButDot(string);
        return this;
    }
    public Format removeDot() {
        string = string.replaceAll("\\.", "");
        return this;
    }

    public Format removeNumbers() {
        string = FormattingUtils.removeNumbers(string);
        return this;
    }

    public Format removeWhitespace() {
        string = FormattingUtils.removeWhitespace(string);
        return this;
    }

    public Format spaceWhitespace() {
        string = FormattingUtils.spaceWhitespace(string);
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
