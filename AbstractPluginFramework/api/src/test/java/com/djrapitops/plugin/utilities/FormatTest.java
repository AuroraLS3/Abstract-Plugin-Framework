/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Risto Lahtela
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
