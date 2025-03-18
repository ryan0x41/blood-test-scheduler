# Blood Test Scheduler - Write Up

> Writing at 22:32 Tue 18 of March
## Development Process

- Base GUI
- Basic Interfaces
- Stack Implementation
- Priority Queue Implementation using Binary Heap
- Linked Queue and Schedule Queue implementation

- Testing

---

## Basic Interfaces
*Stacks and Queues*

To tackle the problem of patient scheduling, we can create the following 3 blueprints

```java
PriorityQueue<T>
Queue<T>
Stack<T>
```

Not much needed to be said here, stack has the usual `push()` and `pop()` same with both queue interfaces having `enqueue()` and `dequeue()`

---

## Stack and Queue Implementation
*a quick overview*

### Stack
*using nodes*

The stack implementation takes inspiration from linked lists, each element on the stack is treated like a node in a linked list, we can push, pop, peek elements as usual.

This stack implementation gives us the easy ability to undo actions, the way we can do this is by pushing each action onto the stack, then doing a `pop()` when needed to get to go back to the last state.

```java
public class LinkedStack<T> implements Stack<T> { ... }
```

### Priority Queue and Queue
*using binary heap*

This was an interesting one to solve, we have a `HeapBinaryQueue`  implementation of the interface `PriorityQueue<T>` which has ability such as `insert(T item)` and `T remove()`

The `LinkedQueue` implemenation is a little different and just like the `LinkedStack` uses nodes to represent elements. We can `enqueue` and `dequeue` as usual.

The `PriorityQueue<T>` implementation does not have `enqueue` and `dequeue` as we insert and remove elements just like an `ArrayList` and uses `heapifyUp(int index)` when inserting and `heapifyDown(int index)` when removing and also the internal `swap()` to swap elements while we do both of these actions.

*All code is commented and explained within the related files*

But ill throw in the `heapifyUp(int index)` as it was an interesting one to solve

```java
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
        // if the element is greater than its parent, we swap        if (comparator.compare(heap.get(index), heap.get(parentIndex)) >= 0) break;  
        swap(index, parentIndex);  
  
        // we now continue heapifying up from the parent  
        index = parentIndex;  
    }  
}
```

---

## Schedule Queue
*the wrapper*

This class mainly just takes our class implementations of our ADT's and gives us a nice interface to do some of the following.

```java
addToWatingQueue(Patient patient);
moveToPriorityQueue(PriorityLevel priority);
processNextAppointment();
undoLastAction();
markAsNoShow(String appointmendId, String reason);
displayWaitingQueue();
displayAppointments();
displayNoShows();
recursiveSearchByName();
recursiveSearchHelper();

// and then the following utility methods

isWaitingQueueEmpty();
isAppointmentQueueEmpty();
getWaitingQueueSize();
getAppointmentQueueSize();
```

This class is then used by the main class to give the GUI some functionality

---
## Testing
*GPT is good*

I try to avoid all usage when it comes to GPT and code generation, unless I am particularly stuck on a problem and need it to break down abstract questions, but in the case of unit tests, this is a repetitive procedure that was done originally with the class `LinkedStackTest` and I did not learn anything from it.

Unit testing in this case gives the benefit of testing classes without having to implement a GUI or command line interface to create, read and search through objects.

All testing is within the `test` folder.

---

## Conclusion

Fun project, I did not give myself enough time to plan, implement and review everything in detail, but it gave me time to catch up on things I am not to strong at currently. 
