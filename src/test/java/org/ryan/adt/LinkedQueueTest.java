// GPT generated just like HeapPriorityQueue test

package org.ryan.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedQueueTest {
    private LinkedQueue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new LinkedQueue<>();
    }

    @Test
    void testIsEmptyOnNewQueue() {
        assertTrue(queue.isEmpty(), "New queue should be empty");
        assertEquals(0, queue.size(), "New queue size should be 0");
    }

    @Test
    void testEnqueueIncreasesSize() {
        queue.enqueue(10);
        assertFalse(queue.isEmpty(), "Queue should not be empty after enqueue");
        assertEquals(1, queue.size(), "Queue size should be 1 after one enqueue");

        queue.enqueue(20);
        queue.enqueue(30);
        assertEquals(3, queue.size(), "Queue size should be 3 after three enqueues");
    }

    @Test
    void testPeekReturnsFrontElementWithoutRemoving() {
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(10, queue.peek(), "Peek should return the first enqueued element (10)");
        assertEquals(2, queue.size(), "Peek should not change the queue size");
    }

    @Test
    void testDequeueReturnsAndRemovesFrontElement() {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        assertEquals(10, queue.dequeue(), "Dequeue should return first enqueued element (10)");
        assertEquals(2, queue.size(), "Queue size should decrease after dequeue");
        assertEquals(20, queue.dequeue(), "Dequeue should return next front element (20)");
        assertEquals(1, queue.size(), "Queue size should be 1 after two dequeues");
        assertEquals(30, queue.dequeue(), "Dequeue should return last element (30)");
        assertTrue(queue.isEmpty(), "Queue should be empty after dequeuing all elements");
    }

    @Test
    void testDequeueOnEmptyQueueThrowsException() {
        assertThrows(NoSuchElementException.class, queue::dequeue, "Dequeuing from an empty queue should throw NoSuchElementException");
    }

    @Test
    void testPeekOnEmptyQueueThrowsException() {
        assertThrows(NoSuchElementException.class, queue::peek, "Peeking an empty queue should throw NoSuchElementException");
    }

    @Test
    void testToStringRepresentation() {
        assertEquals("queue: null", queue.toString(), "Empty queue should have correct string representation");

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        assertEquals("queue: 10 -> 20 -> 30 -> null", queue.toString(), "Queue should print elements in FIFO order");
    }
}
