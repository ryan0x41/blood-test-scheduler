package org.ryan.adt;

import java.util.EmptyStackException;

public class LinkedStack<T> implements Stack<T> {

    // a singular item in our stack is a node, each node points to the next node
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // define the top of the stack as a node and the size as an int
    private Node<T> top;
    private int size;

    // when we create a new linked stack
    // top == null as we have no elements, same with size == 0
    public LinkedStack() {
        this.top = null;
        this.size = 0;
    }

    // pushing a new element on to the stack will have the process of
    //      - creating a new node
    //      - the new node pointing to the previous top
    //      - updating the top to the newNode
    //      - increasing size by 1
    @Override
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    // popping an element of the stack will have the following process
    //      - checking if the stack is empty, if so we throw a EmptyStackException err
    //      - we want to pop the top of the stack, so we create data which references the data of the top node on the stack
    //      - we change top so it points to the next node, (below the top)
    //      - decrease size by 1
    //      - return the data associated with the node we just popped
    @Override
    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    // peeking the top element of the stack will have the following process
    //      - throw an error if the stack is empty
    //      - if not, return the data of the top node
    @Override
    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return top.data;
    }

    // checking is empty is simple, it will have the following process
    // if top is null, it means the stack is empty
    //      - return (top == null)
    @Override
    public boolean isEmpty() {
        return top == null;
    }

    // no explanation here
    @Override
    public int size() {
        return size;
    }

    // the toString method has the following process, we want to step down the stack from the top to bottom
    //      - create a new StringBuilder object
    //      - the current will be the top (first node), while current is not null we append the data to the string (sb)
    //      - we step down one node by saying the current node is the next
    //      - we do this until our current node is null, then finish off the string
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("stack: [ ");
        Node<T> current = top;
        while (current != null) {
            sb.append(current.data).append(" ");
            current = current.next;
        }
        return sb.append("]").toString();
    }
}
