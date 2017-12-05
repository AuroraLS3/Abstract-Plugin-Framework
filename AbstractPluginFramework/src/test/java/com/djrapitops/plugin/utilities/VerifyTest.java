package com.djrapitops.plugin.utilities;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class VerifyTest {
    @Test
    public void exists() throws Exception {
        assertFalse(Verify.exists(null));
        assertFalse(Verify.exists(new File("Nonexistent")));
        assertTrue(Verify.exists(new File("pom.xml")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void existCheck() throws Exception {
        Verify.existCheck(new File("Nonexistent"));
    }

    @Test
    public void isEmpty() throws Exception {
    }

    @Test
    public void isEmpty1() throws Exception {
    }

    @Test
    public void isEmpty2() throws Exception {
    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void contains1() throws Exception {
    }

    @Test
    public void contains2() throws Exception {
    }

    @Test
    public void contains3() throws Exception {
    }

    @Test
    public void equalsIgnoreCase() throws Exception {
    }

    @Test
    public void equalsOne() throws Exception {
    }

    @Test
    public void equals() throws Exception {
    }

    @Test
    public void notNull() throws Exception {
    }

    @Test
    public void containsNull() throws Exception {
    }

    @Test
    public void nullCheck() throws Exception {
    }

    @Test
    public void nullCheck1() throws Exception {
    }

    @Test
    public void hasPermission() throws Exception {
    }

}