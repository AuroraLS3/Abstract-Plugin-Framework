package com.djrapitops.plugin.utilities;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link EjectingQueue}.
 *
 * @author Rsl1122
 */
public class EjectingQueueTest {

    @Test
    public void firstElementIsRemovedWhenFull() {
        EjectingQueue<Integer> underTest = new EjectingQueue<>(2);
        underTest.add(1);
        underTest.add(2);
        underTest.add(3);

        assertEquals(Arrays.asList(2, 3), underTest.getElements());
    }

    @Test
    public void firstElementIsNotRemovedAfterClear() {
        // This test ensures that a clear call does not mess up internal size calculation.
        EjectingQueue<Integer> underTest = new EjectingQueue<>(3);
        underTest.add(1);
        underTest.add(2);
        underTest.clear();

        underTest.add(3);
        underTest.add(4);
        underTest.add(5);
        underTest.add(6);

        assertEquals(Arrays.asList(4, 5, 6), underTest.getElements());
    }

}