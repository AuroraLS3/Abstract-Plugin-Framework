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
public class FormatUtilsTest {
    @Test
    public void parseVersionNumber() throws Exception {
        assertEquals(101010000000L, FormatUtils.parseVersionNumber("1.1.1"));
        assertEquals(101010100000L, FormatUtils.parseVersionNumber("1.1.1-Dev1"));
        assertEquals(102010100000L, FormatUtils.parseVersionNumber("1.2.1-Dev1"));
        assertEquals(302010100000L, FormatUtils.parseVersionNumber("3.2.1-Dev1"));
        assertEquals(302500100000L, FormatUtils.parseVersionNumber("3.2.50-Dev1"));
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
