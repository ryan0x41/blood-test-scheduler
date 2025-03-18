package org.ryan.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerQueueTest {
    private SchedulerQueue schedulerQueue;
    private Patient patient1, patient2, patient3;

    @BeforeEach
    void setUp() {
        schedulerQueue = new SchedulerQueue();

        // Create sample patients
        patient1 = new Patient("P1", "Alice", 30, "Dr. Smith", false);
        patient2 = new Patient("P2", "Bob", 40, "Dr. Brown", true);
        patient3 = new Patient("P3", "Charlie", 25, "Dr. Taylor", false);
    }

    @Test
    void testInitialQueueState() {
        assertTrue(schedulerQueue.isWaitingQueueEmpty(), "Waiting queue should start empty");
        assertTrue(schedulerQueue.isAppointmentQueueEmpty(), "Appointment queue should start empty");
        assertEquals(0, schedulerQueue.getWaitingQueueSize(), "Waiting queue size should be 0 initially");
        assertEquals(0, schedulerQueue.getAppointmentQueueSize(), "Appointment queue size should be 0 initially");
    }

    @Test
    void testAddToWaitingQueue() {
        schedulerQueue.addToWaitingQueue(patient1);
        assertFalse(schedulerQueue.isWaitingQueueEmpty(), "Waiting queue should not be empty after adding a patient");
        assertEquals(1, schedulerQueue.getWaitingQueueSize(), "Waiting queue size should be 1 after adding a patient");
    }

    @Test
    void testMoveToPriorityQueue() {
        schedulerQueue.addToWaitingQueue(patient1);
        schedulerQueue.addToWaitingQueue(patient2);
        schedulerQueue.addToWaitingQueue(patient3);

        assertTrue(schedulerQueue.moveToPriorityQueue(PriorityLevel.URGENT), "Should successfully move patient to priority queue");
        assertEquals(2, schedulerQueue.getWaitingQueueSize(), "Waiting queue size should decrease after moving to priority queue");
        assertEquals(1, schedulerQueue.getAppointmentQueueSize(), "Appointment queue size should increase after moving to priority queue");
    }

    @Test
    void testMoveToPriorityQueueFailsIfEmpty() {
        assertFalse(schedulerQueue.moveToPriorityQueue(PriorityLevel.LOW), "Should return false when trying to move from empty waiting queue");
    }

    @Test
    void testProcessNextAppointment() {
        schedulerQueue.addToWaitingQueue(patient1);
        schedulerQueue.moveToPriorityQueue(PriorityLevel.MEDIUM);

        Appointment appointment = schedulerQueue.processNextAppointment();
        assertNotNull(appointment, "Processed appointment should not be null");
        assertEquals(patient1.getName(), appointment.getPatient().getName(), "Processed appointment should belong to the correct patient");
        assertEquals(0, schedulerQueue.getAppointmentQueueSize(), "Appointment queue should be empty after processing the only appointment");
    }

    @Test
    void testProcessNextAppointmentThrowsExceptionIfEmpty() {
        assertThrows(NoSuchElementException.class, schedulerQueue::processNextAppointment, "Processing an empty appointment queue should throw an exception");
    }

    @Test
    void testUndoLastAction() {
        schedulerQueue.addToWaitingQueue(patient1);
        schedulerQueue.moveToPriorityQueue(PriorityLevel.URGENT);
        Appointment appointment = schedulerQueue.processNextAppointment();

        assertTrue(schedulerQueue.undoLastAction(), "Undo should succeed after processing an appointment");
        assertEquals(1, schedulerQueue.getAppointmentQueueSize(), "Appointment queue should contain the undone appointment");
    }

    @Test
    void testUndoFailsIfNoAction() {
        assertFalse(schedulerQueue.undoLastAction(), "Undo should fail if no action was taken");
    }

    @Test
    void testMarkNoShow() {
        schedulerQueue.addToWaitingQueue(patient1);
        schedulerQueue.moveToPriorityQueue(PriorityLevel.MEDIUM);

        Appointment appointment = schedulerQueue.processNextAppointment();
        String appointmentId = appointment.getAppointmentId();

        schedulerQueue.undoLastAction();

        assertTrue(schedulerQueue.markNoShow(appointmentId, "Patient didn't arrive"), "Marking no-show should succeed");
    }

    @Test
    void testMarkNoShowFailsIfNotFound() {
        assertFalse(schedulerQueue.markNoShow("INVALID_ID", "No-show reason"), "Marking no-show should fail for an invalid appointment ID");
    }

    @Test
    void testDisplayMethods() {
        schedulerQueue.addToWaitingQueue(patient1);
        schedulerQueue.addToWaitingQueue(patient2);
        assertTrue(schedulerQueue.displayWaitingQueue().contains("Alice"), "Display should show waiting patient Alice");
        assertTrue(schedulerQueue.displayWaitingQueue().contains("Bob"), "Display should show waiting patient Bob");

        schedulerQueue.moveToPriorityQueue(PriorityLevel.URGENT);
        assertTrue(schedulerQueue.displayAppointments().contains("Alice"), "Display should show Alice's scheduled appointment");
    }
}
