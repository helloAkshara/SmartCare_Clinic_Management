package smartcare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClinicManager {

    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

    private TriageEngine triageEngine = new TriageEngine();

    private Map<String, Integer> priorityCount = new HashMap<>();

    public ClinicManager() {
        priorityCount.put("HIGH", 0);
        priorityCount.put("MEDIUM", 0);
        priorityCount.put("LOW", 0);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
        FileManager.savePatient(patient);
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        FileManager.saveDoctor(doctor);
    }

    public void addDoctorWithoutSaving(Doctor doctor) {
        doctors.add(doctor);
    }

    public Patient searchPatient(String id) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equalsIgnoreCase(id.trim())) {
                return patient;
            }
        }
        return null;
    }

    public Doctor searchDoctor(String id) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId().equalsIgnoreCase(id.trim())) {
                return doctor;
            }
        }
        return null;
    }

    public TriageResult performTriage(
            Patient patient,
            Map<Symptom, Integer> selectedSymptoms) {

        TriageResult result = triageEngine.evaluate(selectedSymptoms);

        priorityCount.put(
                result.getPriority(),
                priorityCount.getOrDefault(result.getPriority(), 0) + 1
        );

        return result;
    }

    public boolean bookAppointment(
            String id,
            Patient patient,
            Doctor doctor,
            String date,
            String time) {

        if (!doctor.getAvailableSlots().contains(time)) {
            return false;
        }

        if (!doctor.bookSlot(time)) {
            return false;
        }

        Appointment appointment =
                new Appointment(id, patient, doctor, date, time);

        appointments.add(appointment);
        FileManager.saveAppointment(appointment);

        return true;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public Map<String, Integer> getPriorityCount() {
        return priorityCount;
    }

    public void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        for (Patient patient : patients) {
            System.out.println("--------------------------------");
            System.out.println(patient);
        }
    }

    public void displayAllDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered.");
            return;
        }

        for (Doctor doctor : doctors) {
            System.out.println("--------------------------------");
            System.out.println(doctor);
        }
    }

    public void displayAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        for (Appointment appointment : appointments) {
            System.out.println("--------------------------------");
            System.out.println(appointment);
        }
    }
}