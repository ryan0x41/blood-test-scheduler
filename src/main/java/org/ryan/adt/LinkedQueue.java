package org.ryan.adt;

import java.util.NoSuchElementException;

public class LinkedQueue<T> implements Queue<T> {

    // each node in the queue points to the next
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // each queue has a front, rear (back) and a size
    private Node<T> front;
    private Node<T> rear;
    private int size;

    // when a queue is made the front is the same as the rear and they are both null, size also set to 0
    public LinkedQueue() {
        this.front = this.rear = null;
        this.size = 0;
    }

    // add adding an item the process looks like
    //      - creating a new node with that object (item)
    //      - if the queue is not empty (rear != null) we point rear to our new node
    //      - if it is empty, we are inserting the first item so the rear is our new node
    //      - again if this is the first element we are inserting (front == null) front and rear are the same
    //      - increase the size by one
    @Override
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (rear != null) {
            rear.next = newNode;
        }
        rear = newNode;
        if (front == null) {
            front = newNode;
        }
        size++;
    }

    // responsible for removing the front element of the queue
    //      - throw an err if the queue is empty
    //      - save front.data before removing it
    //      - our new front is the next element in the queue
    //      - if the next element is null this means the queue is empty so set rear as null too
    //      - decrease size by 1
    //      - return our saved front.data
    @Override
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("queue is empty");

        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null; // Queue is now empty
        }
        size--;
        return data;
    }

    // litterally return the front elements data
    @Override
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("queue is empty");
        return front.data;
    }

    // simple
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // no explanation
    @Override
    public int size() {
        return size;
    }

    // not really that important so i wont explain
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("queue: ");
        Node<T> current = front;
        while (current != null) {
            sb.append(current.data).append(" -> ");
            current = current.next;
        }
        return sb.append("null").toString();
    }
}
