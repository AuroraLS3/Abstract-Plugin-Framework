/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link Format}.
 *
 * @author Rsl1122
 */
public class FormatTest {

    private static final String TEST_STRING = "ABCZabcz123490;?=) ";
    private static final String LETTERS = "ABCZabcz";
    private static final String NUMBERS = "123490";
    private static final String SYMBOLS = ";?=)";

    @Test
    public void justNumbersLeavesNumbersIntact() {
        Format result = new Format(TEST_STRING).justNumbers();
        assertEquals(NUMBERS, result.toString());
    }

    @Test
    public void justLettersLeavesLettersIntact() {
        Format result = new Format(TEST_STRING).justLetters();
        assertEquals(LETTERS, result.toString());
    }

    @Test
    public void justSymbolsLeavesSymbolsIntact() {
        Format result = new Format(TEST_STRING).justSymbols();
        assertEquals(SYMBOLS, result.toString());
    }

    @Test
    public void uppercaseWorks() {
        Format result = new Format(TEST_STRING).upperCase();
        assertEquals(TEST_STRING.toUpperCase(), result.toString());
    }

    @Test
    public void lowercaseWorks() {
        Format result = new Format(TEST_STRING).lowerCase();
        assertEquals(TEST_STRING.toLowerCase(), result.toString());
    }

    @Test
    public void capitalizationWorks() {
        Format result = new Format(TEST_STRING).capitalize();
        String r = result.toString();
        assertEquals('A', r.charAt(0));
        assertEquals('b', r.charAt(1));
    }

    @Test
    public void removesFirstAndLastLetters() {
        Format result = new Format(TEST_STRING).removeFirstAndLastChar();
        assertEquals("BCZabcz123490;?=)", result.toString());
    }
}
