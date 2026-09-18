package smartcare;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String patientId, name, contact;
    private int age;
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    public Patient(String patientId, String name, int age, String contact) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.contact = contact;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getContact() {
        return contact;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId +
                "\nName: " + name +
                "\nAge: " + age +
                "\nContact: " + contact;
    }
}