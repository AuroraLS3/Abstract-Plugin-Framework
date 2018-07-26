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
    public void merge() {
        String[] one = new String[]{"one"};
        String[] two = new String[]{"two"};
        String[] expected = new String[]{"one", "two"};

        String[] result = ArrayUtil.merge(one, two);

        assertArrayEquals(expected, result);
    }

    @Test
    public void mergeEmpty() {
        String[] one = new String[]{"one"};
        String[] two = new String[]{};
        String[] expected = new String[]{"one"};

        String[] result = ArrayUtil.merge(one, two);

        assertArrayEquals(expected, result);
    }

    @Test
    public void mergeNoArgs() {
        String[] expected = new String[]{};
        String[] result = ArrayUtil.merge();

        assertArrayEquals(expected, result);
    }

    @Test
    public void removeFirstArg() {
        String[] args = new String[]{"one", "two", "three", "four"};
        String[] expected = new String[]{"two", "three", "four"};
        String[] result = ArrayUtil.removeFirstArgument(args);

        assertArrayEquals(expected, result);
    }

    @Test
    public void removeFirstArgNoArgs() {
        String[] expected = new String[]{};
        String[] result = ArrayUtil.removeFirstArgument(expected);

        assertArrayEquals(expected, result);
    }

    @Test
    public void removeFirstArgOneArg() {
        String[] args = new String[]{"one"};
        String[] expected = new String[]{};
        String[] result = ArrayUtil.removeFirstArgument(args);

        assertArrayEquals(expected, result);
    }

}