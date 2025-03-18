package org.ryan.adt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.EmptyStackException;
import static org.junit.jupiter.api.Assertions.*;

class LinkedStackTest {

    private LinkedStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new LinkedStack<>();
    }

    @Test
    void testPushAndSize() {
        assertEquals(0, stack.size());
        stack.push(1);
        assertEquals(1, stack.size());
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.size());
    }

    @Test
    void testPop() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPopThrowsExceptionWhenEmpty() {
        assertThrows(EmptyStackException.class, stack::pop);
    }

    @Test
    void testPeek() {
        stack.push(5);
        assertEquals(5, stack.peek());
        stack.push(10);
        assertEquals(10, stack.peek());
    }

    @Test
    void testPeekThrowsExceptionWhenEmpty() {
        assertThrows(EmptyStackException.class, stack::peek);
    }

    @Test
    void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(42);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testToString() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals("stack: [ 3 2 1 ]", stack.toString());
    }
}
