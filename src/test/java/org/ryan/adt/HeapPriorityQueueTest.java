// GPT: write me a junit test class for (code from HeapPriorityQueue)

package org.ryan.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeapPriorityQueueTest {
    private HeapPriorityQueue<Integer> minHeap;
    private HeapPriorityQueue<Integer> maxHeap;

    @BeforeEach
    void setUp() {
        // Min-Heap (smallest element has the highest priority)
        minHeap = new HeapPriorityQueue<>(Integer::compareTo);

        // Max-Heap (largest element has the highest priority)
        maxHeap = new HeapPriorityQueue<>((a, b) -> b.compareTo(a));
    }

    @Test
    void testIsEmpty() {
        assertTrue(minHeap.isEmpty(), "Heap should be empty initially");
        minHeap.insert(10);
        assertFalse(minHeap.isEmpty(), "Heap should not be empty after insertion");
    }

    @Test
    void testSize() {
        assertEquals(0, minHeap.size(), "Size should be 0 initially");
        minHeap.insert(10);
        assertEquals(1, minHeap.size(), "Size should be 1 after one insertion");
        minHeap.insert(20);
        assertEquals(2, minHeap.size(), "Size should be 2 after two insertions");
    }

    @Test
    void testInsertAndPeek_MinHeap() {
        minHeap.insert(30);
        minHeap.insert(10);
        minHeap.insert(20);

        assertEquals(10, minHeap.peek(), "Peek should return the smallest element in Min-Heap");
    }

    @Test
    void testInsertAndPeek_MaxHeap() {
        maxHeap.insert(30);
        maxHeap.insert(10);
        maxHeap.insert(20);

        assertEquals(30, maxHeap.peek(), "Peek should return the largest element in Max-Heap");
    }

    @Test
    void testRemove_MinHeap() {
        minHeap.insert(30);
        minHeap.insert(10);
        minHeap.insert(20);

        assertEquals(10, minHeap.remove(), "Removing should return the smallest element (Min-Heap)");
        assertEquals(20, minHeap.remove(), "Next remove should return the next smallest element");
        assertEquals(30, minHeap.remove(), "Next remove should return the last element");
        assertTrue(minHeap.isEmpty(), "Heap should be empty after removing all elements");
    }

    @Test
    void testRemove_MaxHeap() {
        maxHeap.insert(30);
        maxHeap.insert(10);
        maxHeap.insert(20);

        assertEquals(30, maxHeap.remove(), "Removing should return the largest element (Max-Heap)");
        assertEquals(20, maxHeap.remove(), "Next remove should return the next largest element");
        assertEquals(10, maxHeap.remove(), "Next remove should return the last element");
        assertTrue(maxHeap.isEmpty(), "Heap should be empty after removing all elements");
    }

    @Test
    void testRemoveFromEmptyHeap() {
        assertNull(minHeap.remove(), "Removing from an empty heap should return null");
    }

    @Test
    void testPeekOnEmptyHeap() {
        assertNull(minHeap.peek(), "Peeking an empty heap should return null");
    }
}
