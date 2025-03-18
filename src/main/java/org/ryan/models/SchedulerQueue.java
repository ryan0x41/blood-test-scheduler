package org.ryan.models;

import org.ryan.adt.HeapPriorityQueue;
import org.ryan.adt.LinkedQueue;
import org.ryan.adt.LinkedStack;
import org.ryan.adt.PriorityQueue;
import org.ryan.adt.Queue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class SchedulerQueue {
    // we define some of the following variables as the interface they will implement, this allows dependency
    // injection if we ever want to use a different implementation of Queue, PriorityQueue etc ..
    private final Queue<Patient> waitingQueue;  // fifo queue for new patients
    private final PriorityQueue<Appointment> appointmentQueue; // priority queue for appointments
    private final LinkedStack<Appointment> undoStack; // a stack to allow undo's

    private final List<NoShow> noShows;  // a list to track no-shows

    public SchedulerQueue() {
        this.waitingQueue = new LinkedQueue<>(); // normal queue for waiting patients

        // ------------------------------------------------------------------------------------------------------------
        // this is alot to explain for one line but this is new to me so
        // ------------------------------------------------------------------------------------------------------------
        // we define a new HeapPriorityQueue as the PriorityQueue implemenation
        // we need to tell HeapPriorityQueue how to compare appointments, we do this by using a lambda
        // expression, and we are basically passing HeapPriorityQueue a function, which it will have the
        // ability to call, Comparator needs this function to compare appointments

        // the function uses a lambda expression, basically the function takes an appointment and extracts the priority
        // and converts it to an integer with ordinal, Comparator can use this function to compare two appointments to
        // see which has the higher priority
        this.appointmentQueue = new HeapPriorityQueue<>(Comparator.comparing(a -> a.getPriority().ordinal()));
        // ------------------------------------------------------------------------------------------------------------

        // undoStack is linkedStack and noShows are ArrayList
        this.undoStack = new LinkedStack<>();
        this.noShows = new ArrayList<>();
    }

    // takes a patient and adds it to the waiting queue
    public void addToWaitingQueue(Patient patient) {
        waitingQueue.enqueue(patient);
    }

    // move a patient into the priority queue using a priority level
    public boolean moveToPriorityQueue(PriorityLevel priority) {
        // we cannot move a patient from the waiting queue if its empty
        if (waitingQueue.isEmpty()) {
            return false;
        }

        // the next patient to add to the priority queue will be the top element of the waiting queue
        Patient nextPatient = waitingQueue.dequeue();

        // create a new appointment with the patient
        Appointment appointment = new Appointment(
            "A" + System.currentTimeMillis(), // generate unique appointmentId
            nextPatient,
            priority,
            LocalDateTime.now()
        );

        // insert that appointment into the priority queue
        appointmentQueue.insert(appointment);

        // we didnt fail so return true
        return true;
    }

    // process the appointment (highest priority)
    // processing is litterally the concept of removing the appointment from the appointment queue
    public Appointment processNextAppointment() {
        if (appointmentQueue.isEmpty()) {
            throw new NoSuchElementException("no appointments available");
        }

        Appointment next = appointmentQueue.remove();

        // and storing the on the undoStack so we have the ability to go back (pop)
        undoStack.push(next);
        return next;
    }

    // undo the last processed appointment
    public boolean undoLastAction() {
        if (undoStack.isEmpty()) {
            return false;
        }

        Appointment lastRemoved = undoStack.pop();

        // reinsert the lastRemoved into the appointmentQueue
        appointmentQueue.insert(lastRemoved);
        return true;
    }

    // given an id and a reason, mark a patient as a no show
    public boolean markNoShow(String appointmentId, String reason) {
        if (appointmentQueue.isEmpty()) {
            return false;
        }

        // create temp list to hold appointments
        List<Appointment> allAppointments = new ArrayList<>();

        // remove all from queue
        while (!appointmentQueue.isEmpty()) {
            Appointment appt = appointmentQueue.remove();
            allAppointments.add(appt);
        }

        // find with matching id
        Appointment noShowAppointment = null;
        for (Appointment appt : allAppointments) {
            if (appt.getAppointmentId().equals(appointmentId)) {
                noShowAppointment = appt;
                break;
            }
        }

        // restore
        for (Appointment appt : allAppointments) {
            if (noShowAppointment == null || !appt.getAppointmentId().equals(appointmentId)) {
                appointmentQueue.insert(appt);
            } else {
            }
        }

        // if we found appt, add it to noShows
        if (noShowAppointment != null) {
            noShows.add(new NoShow(noShowAppointment.getPatient(), reason));
            return true;
        }

        return false;
    }

    // display waiting queue, more StringBuilder stuff i wont explain
    public String displayWaitingQueue() {
        StringBuilder sb = new StringBuilder("waiting patients:\n");
        int count = 1;

        Queue<Patient> tempQueue = new LinkedQueue<>();
        while (!waitingQueue.isEmpty()) {
            Patient p = waitingQueue.dequeue();
            sb.append(count++).append(". ").append(p).append("\n");
            tempQueue.enqueue(p);
        }

        // restore waiting queue
        while (!tempQueue.isEmpty()) {
            waitingQueue.enqueue(tempQueue.dequeue());
        }

        return sb.toString();
    }

    // display scheduled appointments, more sb stuff i wont explain
    public String displayAppointments() {
        StringBuilder sb = new StringBuilder("Scheduled Appointments:\n");
        int count = 1;

        List<Appointment> tempList = new ArrayList<>();
        while (!appointmentQueue.isEmpty()) {
            Appointment a = appointmentQueue.remove();
            sb.append(count++).append(". ").append(a).append("\n");
            tempList.add(a);
        }

        // restore
        for (Appointment a : tempList) {
            appointmentQueue.insert(a);
        }

        return sb.toString();
    }

    // display no shows
    public String displayNoShows() {
        StringBuilder sb = new StringBuilder("recent no-shows:\n");
        for (NoShow noShow : noShows) {
            sb.append("- ").append(noShow).append("\n");
        }
        return sb.toString();
    }

    // other methods we might need
    public boolean isWaitingQueueEmpty() { return waitingQueue.isEmpty(); }
    public boolean isAppointmentQueueEmpty() { return appointmentQueue.isEmpty(); }
    public int getWaitingQueueSize() { return waitingQueue.size(); }
    public int getAppointmentQueueSize() { return appointmentQueue.size(); }
}
