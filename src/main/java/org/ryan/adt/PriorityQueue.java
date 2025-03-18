package org.ryan.adt;

public interface PriorityQueue<T> {
    void insert(T item);
    T remove();
    T peek();
    boolean isEmpty();
    int size();
}
