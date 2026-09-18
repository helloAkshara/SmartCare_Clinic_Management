package smartcare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Doctor {
    private String doctorId, name, specialization;
    private List<String> availableSlots = new ArrayList<>();

    public Doctor(String doctorId, String name, String specialization) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;

        Collections.addAll(
                availableSlots,
                "09:00", "10:00", "11:00",
                "12:00", "14:00", "15:00", "16:00"
        );
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<String> getAvailableSlots() {
        return availableSlots;
    }

    public boolean bookSlot(String slot) {
        return availableSlots.remove(slot);
    }

    @Override
    public String toString() {
        return "Doctor ID: " + doctorId +
                "\nName: " + name +
                "\nSpecialization: " + specialization +
                "\nAvailable Slots: " + availableSlots;
    }
}