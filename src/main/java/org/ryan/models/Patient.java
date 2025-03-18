package org.ryan.models;

public class Patient {
    private String id;
    private String name;
    private int age;
    private String gpDetails;
    private boolean fromHospitalWard;

    public Patient(String id, String name, int age, String gpDetails, boolean fromHospitalWard) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gpDetails = gpDetails;
        this.fromHospitalWard = fromHospitalWard;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGpDetails() { return gpDetails; }
    public boolean isFromHospitalWard() { return fromHospitalWard; }

    @Override
    public String toString() {
        return name + " (age: " + age + ", gp: " + gpDetails + ", ward: " + (fromHospitalWard ? "yes" : "no") + ")";
    }
}
