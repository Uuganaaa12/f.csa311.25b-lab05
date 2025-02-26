package edu.cmu.cs.cs214.rec02;

import java.util.Arrays;

/**
 * A resizable-array implementation of the {@link IntQueue} interface. The head
 * of
 * the queue starts out at the head of the array, allowing the queue to grow and
 * shrink in constant time.
 *
 * TODO: This implementation contains three bugs! Use your tests to determine
 * the
 * source of the bugs and correct them!
 *
 * @author Alex Lockwood
 * @author Ye Lu
 */
public class ArrayIntQueue implements IntQueue {

    private int[] elementData;

    private int head;

    private int size;

    private static final int INITIAL_SIZE = 10;

    public ArrayIntQueue() {
        elementData = new int[INITIAL_SIZE];
        head = 0;
        size = 0;
    }

    /** {@inheritDoc} */
    public void clear() {
        Arrays.fill(elementData, 0);
        size = 0;
        head = 0;
    }

    /** {@inheritDoc} */
    public Integer dequeue() {
        if (isEmpty()) {
            return null;
        }
        Integer value = elementData[head];
        elementData[head] = 0;
        head = (head + 1) % elementData.length;
        size--;
        return value;
    }

    /** {@inheritDoc} */
    public boolean enqueue(Integer value) {
        ensureCapacity();
        int tail = (head + size) % elementData.length;
        elementData[tail] = value;
        size++;
        return true;
    }

    /** {@inheritDoc} */
    public boolean isEmpty() {
        return size == 0; 
        // return size >= 0; testtt
    }

    /** {@inheritDoc} */
    public Integer peek() {
        if (isEmpty()) {
            return null;
        }
        return Integer.valueOf(elementData[head]);
    }

    // public Integer peek() {
    // return elementData[head];
    // } iim baisan testttt

    /** {@inheritDoc} */
    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elementData.length) {
            int newCapacity = 2 * elementData.length + 1;
            int[] newData = new int[newCapacity];
            for (int i = 0; i < size; i++) {
                newData[i] = elementData[(head + i) % elementData.length];
            }
            elementData = newData;
            head = 0;
        }
    }

}
