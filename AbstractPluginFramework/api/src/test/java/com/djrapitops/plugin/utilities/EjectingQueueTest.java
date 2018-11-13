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