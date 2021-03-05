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
package com.djrapitops.plugin.utilities;

import com.google.common.collect.ImmutableList;

import java.util.ArrayDeque;

/**
 * Utility queue that removes first element in the queue after max size is reached.
 *
 * @param <E> Type of the elements in this Queue.
 * @author AuroraLS3
 */
public class EjectingQueue<E> {

    private final int maxSize;
    private final ArrayDeque<E> queue;
    private volatile int size;

    /**
     * Create a new EjectingQueue.
     *
     * @param maxSize Maximum size of elements in this queue.
     */
    public EjectingQueue(int maxSize) {
        this.maxSize = maxSize;
        queue = new ArrayDeque<>(maxSize);
        size = 0;
    }

    /**
     * Add an element to the queue.
     * <p>
     * Head of the queue is removed if the size is over max size.
     *
     * @param element Element to add.
     */
    public synchronized void add(E element) {
        if (size >= maxSize) {
            queue.poll();
            size--;
        }
        queue.add(element);
        size++;
    }

    /**
     * Clear the queue.
     */
    public void clear() {
        queue.clear();
        size = 0;
    }

    /**
     * Get the elements in this Queue.
     *
     * @return Immutable list of the elements in this queue.
     */
    public ImmutableList<E> getElements() {
        return ImmutableList.copyOf(queue);
    }
}