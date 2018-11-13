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

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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