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

import static org.junit.Assert.assertArrayEquals;

/**
 * Test for the {@link ArrayUtil} class.
 *
 * @author Rsl1122
 */
public class ArrayUtilTest {

    @Test
    public void mergeCombinesArrays() {
        String[] one = new String[]{"one"};
        String[] two = new String[]{"two"};
        String[] expected = new String[]{"one", "two"};

        String[] result = ArrayUtil.merge(one, two);

        assertArrayEquals(expected, result);
    }

    @Test
    public void mergeCombinesEmptyArray() {
        String[] one = new String[]{"one"};
        String[] two = new String[]{};
        String[] expected = new String[]{"one"};

        String[] result = ArrayUtil.merge(one, two);

        assertArrayEquals(expected, result);
    }

    @Test
    public void mergeCreatesNoArgsArray() {
        String[] expected = new String[]{};
        String[] result = ArrayUtil.merge();

        assertArrayEquals(expected, result);
    }

    @Test
    public void firstArgumentIsRemoved() {
        String[] args = new String[]{"one", "two", "three", "four"};
        String[] expected = new String[]{"two", "three", "four"};
        String[] result = ArrayUtil.removeFirstArgument(args);

        assertArrayEquals(expected, result);
    }

    @Test
    public void argumentRemovalDoesNotLeadToOutOfBoundsOnEmptyArray() {
        String[] expected = new String[]{};
        String[] result = ArrayUtil.removeFirstArgument(expected);

        assertArrayEquals(expected, result);
    }

    @Test
    public void argumentRemovalLeadsToEmptyArray() {
        String[] args = new String[]{"one"};
        String[] expected = new String[]{};
        String[] result = ArrayUtil.removeFirstArgument(args);

        assertArrayEquals(expected, result);
    }

}