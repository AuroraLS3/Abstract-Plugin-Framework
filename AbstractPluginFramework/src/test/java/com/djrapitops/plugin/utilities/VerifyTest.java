package com.djrapitops.plugin.utilities;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * //TODO Class Javadoc Comment
 *
 * @author Rsl1122
 */
public class VerifyTest {
    @Test
    public void exists() {
        assertFalse(Verify.exists(null));
        assertFalse(Verify.exists(new File("Nonexistent")));
        assertTrue(Verify.exists(new File("pom.xml")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void existCheck() {
        Verify.existCheck(new File("Nonexistent"));
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void isEmpty1() {
    }

    @Test
    public void isEmpty2() {
    }

    @Test
    public void contains() {
    }

    @Test
    public void contains1() {
    }

    @Test
    public void contains2() {
    }

    @Test
    public void contains3() {
    }

    @Test
    public void equalsIgnoreCase() {
    }

    @Test
    public void equalsOne() {
    }

    @Test
    public void equals() {
    }

    @Test
    public void notNull() {
    }

    @Test
    public void containsNull() {
    }

    @Test
    public void nullCheck() {
    }

    @Test
    public void nullCheck1() {
    }

    @Test
    public void hasPermission() {
    }

}