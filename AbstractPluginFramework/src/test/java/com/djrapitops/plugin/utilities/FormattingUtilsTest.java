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
public class FormattingUtilsTest {
    
    public FormattingUtilsTest() {
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
        assertEquals(exp, FormattingUtils.removeLetters(string));
    }

    @Test
    public void testRemoveNumbers() {
        String string = "ABCZabcz123490;?=) ";
        String exp = "ABCZabcz;?=) ";
        assertEquals(exp, FormattingUtils.removeNumbers(string));
    }

    @Test
    public void testRemoveSymbols() {
        String string = "ABCZabcz123490;?=) ";
        String exp = "ABCZabcz123490 ";
        assertEquals(exp, FormattingUtils.removeSymbols(string));
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
