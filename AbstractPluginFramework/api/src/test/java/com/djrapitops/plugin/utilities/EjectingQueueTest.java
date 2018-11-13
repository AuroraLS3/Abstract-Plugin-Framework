package com.djrapitops.plugin.utilities;

import org.junit.Test;

import java.util.*;

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

    @Test
    public void concurrencyDoesNotCauseException() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Set<Boolean> run = new HashSet<>();
        run.add(true);

        List<Throwable> errors = new ArrayList<>();
        List<Boolean> runTimes = new ArrayList<>();

        EjectingQueue<String> queue = new EjectingQueue<>(25);

        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(() -> {
                while (!run.isEmpty()) {
                    try {
                        queue.add("Example");
                        runTimes.add(true);
                    } catch (Exception e) {
                        errors.add(e);
                    }
                }
            });
            threads.add(thread);
        }

        threads.forEach(Thread::start);

        while (runTimes.size() < 2000000) {
            Thread.sleep(1L);
        }

        run.clear();
        threads.forEach(Thread::interrupt);
        int size = errors.size();
        assertEquals(size + " errors occurred.", 0, size);
    }

}