package com.djrapitops.plugin.utilities;

import com.google.common.collect.ImmutableList;

import java.util.ArrayDeque;

/**
 * Utility queue that removes first element in the queue after max size is reached.
 *
 * @param <E> Type of the elements in this Queue.
 * @author Rsl1122
 */
public class EjectingQueue<E> {

    private final int maxSize;
    private ArrayDeque<E> queue;
    private int size;

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
    public void add(E element) {
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