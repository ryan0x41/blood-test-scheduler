package org.ryan.adt;

import java.util.ArrayList;
import java.util.Comparator;

public class HeapPriorityQueue<T> implements PriorityQueue<T> {
    private ArrayList<T> heap;
    private Comparator<T> comparator;

    public HeapPriorityQueue(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    @Override
    public void insert(T item) {
        // add item to arraylist
        heap.add(item);
        // reorder
        heapifyUp(heap.size() - 1);
    }

    // to explain the following code imagine this heap stored in an array
    //          10          (index 0)
    //        /    \
    //      20      30      (index 1, 2)
    //     /  \    /
    //    40  50  60        (index 3, 4, 5)

    // in memory the heap in an ArrayList would look like this

    // index :  0,  1,  2,  3,  4,  5
    // heap  : [10, 20, 30, 40, 50, 60]

    // for any element in the heap at index i, we can find

    // the parent index  : (i - 1 / 2)
    // left child index  : (2 * i + 1)
    // right child index : (2 * i + 2)

    // if we want to find the parent of 30, which is index 2
    // it should look something like the following

    // parent index: (2 - 1) / 2 = 0.5, and in this case we always round down (truncate the decimal part)
    // which is 0, at index 0 the element is 10, we can confirm this is the parent of 30 by the graph

    // we use heapify up after inserting an element to reorder the tree
    // we start a loop that only stops if we reach the root
    private void heapifyUp(int index) {
        while (index > 0) {
            // parent index of the element from where we are heapifying
            int parentIndex = (index - 1) / 2;

            // we compare this element and its parent
            // if the element is greater than its parent, we swap
            if (comparator.compare(heap.get(index), heap.get(parentIndex)) >= 0) break;
            swap(index, parentIndex);

            // we now continue heapifying up from the parent
            index = parentIndex;
        }
    }

    // simple swap, create a temp object
    //      - set i to j (object to swap with)
    //      - set j to temp (i)
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // this has the following process of
    //      - removing the root element (element at index 0)
    //      - move the last element in the ArrayList to (index 0) make it the root
    //      - heapify down (reorder heap)
    @Override
    public T remove() {
        if(heap.isEmpty()) return null; // nothing to remove

        // get a reference to root (the element we will remove)
        T removedItem = heap.get(0);
        // set the root to the last element in the ArrayList, last element index = (heap.size() - 1)
        heap.set(0, heap.get(heap.size() - 1));
        // then remove the last element, emulating a replacement
        heap.remove(heap.size() - 1);

        // the heap will be empty in the case that there was one element in the ArrayList and it was removed
        if(!heap.isEmpty()) heapifyDown(0);

        // return the element we just removed
        return removedItem;
    }

    // using the formulas defined above we can define how to grab leftChild and rightChild

    // basically move the top element downwards until the heap is restored
    private void heapifyDown(int index) {
        int leftChild, rightChild, smallest;

        while ((leftChild = 2 * index + 1) < heap.size()) {
            rightChild = leftChild + 1;
            smallest = leftChild;

            // find the smallest of left and right child
            if (rightChild < heap.size() && comparator.compare(heap.get(rightChild), heap.get(leftChild)) < 0) {
                smallest = rightChild;
            }

            // if the current element is smaller than the smallest child, stop
            if (comparator.compare(heap.get(index), heap.get(smallest)) <= 0) break;

            // if its not, then swap them
            swap(index, smallest);

            // move down to the smallest new index
            index = smallest;
        }
    }

    // no explanation
    @Override
    public T peek() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    // no explanation
    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // no explanation
    @Override
    public int size() {
        return heap.size();
    }
}
