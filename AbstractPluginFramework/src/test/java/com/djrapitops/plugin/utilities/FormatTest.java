/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ristolah
 */
public class FormatTest {
    
    private final String string = "ABCZabcz123490;?=) ";
    private final String letters = "ABCZabcz";
    private final String numbers = "123490";
    private final String symbols = ";?=)";
    
    public FormatTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreate() {
        Format exp = new Format("Test");
        assertEquals(exp, Format.create("Test"));
    }

    @Test
    public void testJustNumbers() {
        Format result = new Format(string).justNumbers();
        assertEquals(numbers, result.toString());
    }

    @Test
    public void testJustLetters() {
        Format result = new Format(string).justLetters();
        assertEquals(letters, result.toString());
    }

    @Test
    public void testJustSymbols() {
        Format result = new Format(string).justSymbols();
        assertEquals(symbols, result.toString());
    }

    @Test
    public void testUpperCase() {
        Format result = new Format(string).upperCase();
        assertEquals(string.toUpperCase(), result.toString());
    }

    @Test
    public void testLowerCase() {
        Format result = new Format(string).lowerCase();
        assertEquals(string.toLowerCase(), result.toString());
    }

    @Test
    public void testCapitalize() {
        Format result = new Format(string).capitalize();
        String r = result.toString();
        assertEquals('A', r.charAt(0));
        assertEquals('b', r.charAt(1));
    }    
}
