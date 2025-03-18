package org.ryan.models;

import java.time.LocalDateTime;

public class Appointment {
    private String appointmentId;
    private Patient patient;
    private PriorityLevel priority;
    private LocalDateTime scheduledTime;

    public Appointment(String appointmentId, Patient patient, PriorityLevel priority, LocalDateTime scheduledTime) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.priority = priority;
        this.scheduledTime = scheduledTime;
    }

    public String getAppointmentId() { return appointmentId; }
    public Patient getPatient() { return patient; }
    public PriorityLevel getPriority() { return priority; }
    public LocalDateTime getScheduledTime() { return scheduledTime; }

    @Override
    public String toString() {
        return "[" + priority + "] " + patient.getName() + " at " + scheduledTime;
    }
}
