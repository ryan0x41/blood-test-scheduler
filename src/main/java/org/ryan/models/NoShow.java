package org.ryan.models;

public class NoShow {
    private Patient patient;
    private String reason;

    public NoShow(Patient patient, String reason) {
        this.patient = patient;
        this.reason = reason;
    }

    public Patient getPatient() { return patient; }
    public String getReason() { return reason; }

    @Override
    public String toString() {
        return patient.getName() + " (reason: " + reason + ")";
    }
}
