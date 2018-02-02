/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author ristolah
 */
public class FormatUtilsTest {
    @Test
    public void parseVersionNumber() {
        assertEquals(10101000000000000L, FormatUtils.parseVersionNumber("1.1.1"));
        assertEquals(10101010000000000L, FormatUtils.parseVersionNumber("1.1.1-Dev1"));
        assertEquals(10201010000000000L, FormatUtils.parseVersionNumber("1.2.1-Dev1"));
        assertEquals(30201010000000000L, FormatUtils.parseVersionNumber("3.2.1-Dev1"));
        assertEquals(30250010000000000L, FormatUtils.parseVersionNumber("3.2.50-Dev1"));
        assertEquals(424643430000000000L, FormatUtils.parseVersionNumber("42.46.43.43"));
        assertTrue(FormatUtils.parseVersionNumber("3.2.50-Dev1") < FormatUtils.parseVersionNumber("42.46.43.43"));
        assertTrue(FormatUtils.parseVersionNumber("1.1.1") < FormatUtils.parseVersionNumber("1.1.1-Dev1"));
        assertTrue(FormatUtils.parseVersionNumber("1.1.1-Dev1") < FormatUtils.parseVersionNumber("1.2.1-Dev1"));
        assertTrue(FormatUtils.parseVersionNumber("3.2.1-Dev1") < FormatUtils.parseVersionNumber("3.2.50-Dev1"));
    }

    public FormatUtilsTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRemoveLetters() {
        String string = "ABCZabcz123490;?=) ";
        String exp = "123490;?=) ";
        assertEquals(exp, FormatUtils.removeLetters(string));
    }

    @Test
    public void testRemoveNumbers() {
        String string = "ABCZabcz123490;?=) ";
        String exp = "ABCZabcz;?=) ";
        assertEquals(exp, FormatUtils.removeNumbers(string));
    }

    @Test
    public void testRemoveSymbols() {
        String string = "ABCZabcz123490;?=) ";
        String exp = "ABCZabcz123490 ";
        assertEquals(exp, FormatUtils.removeSymbols(string));
    }

    @Test
    public void testSpaceWhitespace() {
    }

    @Test
    public void testParseVersionNumber() {
    }

    @Test
    public void testRemoveFirstArgument() {
    }

    @Test
    public void testMergeArrays() {
    }

    @Test
    public void testFormatLocation() {
    }

    @Test
    public void testCutDecimals() {
    }

    @Test
    public void testCollectionToStringNoBrackets() {
    }

}
