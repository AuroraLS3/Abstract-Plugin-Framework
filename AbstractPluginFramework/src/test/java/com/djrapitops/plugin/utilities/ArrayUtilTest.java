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