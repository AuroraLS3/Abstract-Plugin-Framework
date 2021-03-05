/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 AuroraLS3
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
package com.djrapitops.plugin.api.utility;

import org.junit.Test;

import static org.junit.Assert.*;

public class VersionTest {
    @Test
    public void isNewVersionAvailableComparesCorrectly() {
        assertTrue(Version.isNewVersionAvailable(new Version("1"), new Version("2")));
        assertFalse(Version.isNewVersionAvailable(new Version("1"), new Version("1")));
        assertFalse(Version.isNewVersionAvailable(new Version("1"), new Version("0")));
    }

    @Test
    public void parseVersionNumber() {
        assertEquals(10101000000000000L, new Version("1.1.1").getVersionLong());
        assertEquals(10101010000000000L, new Version("1.1.1-Dev1").getVersionLong());
        assertEquals(10201010000000000L, new Version("1.2.1-Dev1").getVersionLong());
        assertEquals(30201010000000000L, new Version("3.2.1-Dev1").getVersionLong());
        assertEquals(30250010000000000L, new Version("3.2.50-Dev1").getVersionLong());
        assertEquals(424643430000000000L, new Version("42.46.43.43").getVersionLong());
        assertTrue(new Version("3.2.50-Dev1").compareTo(new Version("42.46.43.43")) < 0);
        assertTrue(new Version("1.1.1").compareTo(new Version("1.1.1-Dev1")) < 0);
        assertTrue(new Version("1.1.1-Dev1").compareTo(new Version("1.2.1-Dev1")) < 0);
        assertTrue(new Version("3.2.1-Dev1").compareTo(new Version("3.2.50-Dev1")) < 0);
    }

}