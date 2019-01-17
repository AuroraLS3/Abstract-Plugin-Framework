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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VerifyTest {

    @Test
    public void emptyStringIsEmpty() {
        assertTrue(Verify.isEmpty(""));
    }

    @Test
    public void nullStringIsEmpty() {
        assertTrue(Verify.isEmpty((String) null));
    }

    @Test
    public void stringIsNotEmpty() {
        assertFalse(Verify.isEmpty("Test"));
    }

    @Test
    public void emptyCollectionIsEmpty() {
        assertTrue(Verify.isEmpty(Collections.emptyList()));
    }

    @Test
    public void nullCollectionIsEmpty() {
        assertTrue(Verify.isEmpty((Collection<Object>) null));
    }

    @Test
    public void collectionIsNotEmpty() {
        assertFalse(Verify.isEmpty(Collections.singletonList("")));
    }

    @Test
    public void emptyMapIsEmpty() {
        assertTrue(Verify.isEmpty(Collections.emptyMap()));
    }

    @Test
    public void nullMapIsEmpty() {
        assertTrue(Verify.isEmpty((Map<Object, Object>) null));
    }

    @Test
    public void mapIsNotEmpty() {
        Map<Object, Object> map = new HashMap<>();
        map.put("item", "");
        assertFalse(Verify.isEmpty(map));
    }

    @Test
    public void containsElement() {
        assertTrue(Verify.contains("Element", "", "Element"));
    }

    @Test
    public void doesNotContainElement() {
        assertFalse(Verify.contains("Not Found", "", "Element"));
    }

    @Test
    public void collectionContainsElement() {
        assertTrue(Verify.contains("Element", Collections.singletonList("Element")));
    }

    @Test
    public void collectionDoesNotContainElement() {
        assertFalse(Verify.contains("Element", Collections.emptyList()));
    }

    @Test
    public void stringContainsOne() {
        assertTrue(Verify.containsOne("To Check", "To"));
    }

    @Test
    public void nullContainsOneNull() {
        assertTrue(Verify.containsOne(null, null, null));
    }

    @Test
    public void nullDoesNotContainOne() {
        assertFalse(Verify.containsOne(null, ""));
    }

    @Test
    public void nullArrayIsNotContainedOne() {
        assertFalse(Verify.containsOne("", (String[]) null));
    }

    @Test
    public void stringDoesNotContainOne() {
        assertFalse(Verify.containsOne("To Check", "E", "K"));
    }
}