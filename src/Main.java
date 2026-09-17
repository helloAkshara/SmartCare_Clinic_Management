import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

class Patient {
    private String patientId, name, contact;
    private int age;
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    public Patient(String patientId, String name, int age, String contact) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.contact = contact;
    }

    public String getPatientId() { return patientId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getContact() { return contact; }
    public List<MedicalRecord> getMedicalRecords() { return medicalRecords; }
    public void addMedicalRecord(MedicalRecord record) { medicalRecords.add(record); }

    @Override
    public String toString() {
        return "Patient ID: " + patientId +
                "\nName: " + name +
                "\nAge: " + age +
                "\nContact: " + contact;
    }
}

class Doctor {
    private String doctorId, name, specialization;
    private List<String> availableSlots = new ArrayList<>();

    public Doctor(String doctorId, String name, String specialization) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        Collections.addAll(availableSlots, "09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00");
    }

    public String getDoctorId() { return doctorId; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public List<String> getAvailableSlots() { return availableSlots; }

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

class Symptom {
    private String name;
    private int baseScore;

    public Symptom(String name, int baseScore) {
        this.name = name;
        this.baseScore = baseScore;
    }

    public String getName() { return name; }
    public int getBaseScore() { return baseScore; }

    @Override
    public String toString() {
        return name + " (base score: " + baseScore + ")";
    }
}

class TriageResult {
    private String priority, reason;
    private int score;

    public TriageResult(String priority, int score, String reason) {
        this.priority = priority;
        this.score = score;
        this.reason = reason;
    }

    public String getPriority() { return priority; }
    public int getScore() { return score; }
    public String getReason() { return reason; }

    @Override
    public String toString() {
        return "Priority: " + priority +
                "\nScore: " + score +
                "\nReason: " + reason;
    }
}

class TriageEngine {
    public TriageResult evaluate(Map<Symptom, Integer> selectedSymptoms) {
        int score = 0;

        for (Map.Entry<Symptom, Integer> entry : selectedSymptoms.entrySet()) {
            Symptom symptom = entry.getKey();
            int multiplier = entry.getValue();
            score += symptom.getBaseScore() * multiplier;
        }

        if (score >= 8) {
            return new TriageResult("HIGH", score, "The project rules produced a high-priority score.");
        } else if (score >= 4) {
            return new TriageResult("MEDIUM", score, "The project rules produced a medium-priority score.");
        } else {
            return new TriageResult("LOW", score, "The project rules produced a low-priority score.");
        }
    }
}

class Appointment {
    private String appointmentId, date, time, status;
    private Patient patient;
    private Doctor doctor;

    public Appointment(String appointmentId, Patient patient, Doctor doctor, String date, String time) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = "Scheduled";
    }

    public String getAppointmentId() { return appointmentId; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }

    public void completeAppointment() {
        status = "Completed";
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId +
                "\nPatient: " + patient.getName() +
                "\nDoctor: " + doctor.getName() +
                "\nDate: " + date +
                "\nTime: " + time +
                "\nStatus: " + status;
    }
}

class MedicalRecord {
    private String recordId, date, doctorNotes, prescription, followUpDate;
    private List<Symptom> symptoms;

    public MedicalRecord(String recordId, String date, List<Symptom> symptoms,
                         String doctorNotes, String prescription, String followUpDate) {
        this.recordId = recordId;
        this.date = date;
        this.symptoms = new ArrayList<>(symptoms);
        this.doctorNotes = doctorNotes;
        this.prescription = prescription;
        this.followUpDate = followUpDate;
    }

    @Override
    public String toString() {
        StringBuilder symptomText = new StringBuilder();
        for (int i = 0; i < symptoms.size(); i++) {
            symptomText.append(symptoms.get(i).getName());
            if (i < symptoms.size() - 1) symptomText.append(", ");
        }

        return "Record ID: " + recordId +
                "\nDate: " + date +
                "\nSymptoms: " + symptomText +
                "\nDoctor Notes: " + doctorNotes +
                "\nPrescription: " + prescription +
                "\nFollow-up Date: " + followUpDate;
    }
}

class InputValidator {
    public static int readAge(Scanner sc) {
        while (true) {
            try {
                int age = Integer.parseInt(sc.nextLine().trim());
                if (age > 0 && age <= 120) return age;
                System.out.print("Enter a valid age (1-120): ");
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    public static String readNonEmpty(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty.");
        }
    }

    public static String readDate(Scanner sc) {
        while (true) {
            System.out.print("Enter appointment date (YYYY-MM-DD): ");
            String date = sc.nextLine().trim();
            try {
                LocalDate.parse(date);
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    public static int readChoice(Scanner sc, int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                if (choice >= min && choice <= max) return choice;
            } catch (NumberFormatException ignored) {
                // Ask again below.
            }
            System.out.print("Enter a valid choice (" + min + "-" + max + "): ");
        }
    }
}

class FileManager {
    public static void savePatient(Patient patient) {
        try (FileWriter writer = new FileWriter("patients.txt", true)) {
            writer.write(patient.getPatientId() + "|" + patient.getName() + "|" +
                    patient.getAge() + "|" + patient.getContact() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving patient data.");
        }
    }

    public static void saveDoctor(Doctor doctor) {
        try (FileWriter writer = new FileWriter("doctors.txt", true)) {
            writer.write(doctor.getDoctorId() + "|" + doctor.getName() + "|" +
                    doctor.getSpecialization() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving doctor data.");
        }
    }

    public static void saveAppointment(Appointment appointment) {
        try (FileWriter writer = new FileWriter("appointments.txt", true)) {
            writer.write(appointment.getAppointmentId() + "|" +
                    appointment.getPatient().getPatientId() + "|" +
                    appointment.getDoctor().getDoctorId() + "|" +
                    appointment.getDate() + "|" + appointment.getTime() + "|" +
                    appointment.getStatus() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving appointment data.");
        }
    }
}

class ReportGenerator {
    public static void generateReport(List<Patient> patients, List<Doctor> doctors,
                                      List<Appointment> appointments,
                                      Map<String, Integer> priorityCount) {
        int completed = 0;
        for (Appointment appointment : appointments) {
            if (appointment.getStatus().equals("Completed")) completed++;
        }

        System.out.println("\n======================================");
        System.out.println("          SMARTCARE REPORT");
        System.out.println("======================================");
        System.out.println("Total Patients     : " + patients.size());
        System.out.println("Total Doctors      : " + doctors.size());
        System.out.println("Appointments       : " + appointments.size());
        System.out.println("High Priority      : " + priorityCount.getOrDefault("HIGH", 0));
        System.out.println("Medium Priority    : " + priorityCount.getOrDefault("MEDIUM", 0));
        System.out.println("Low Priority       : " + priorityCount.getOrDefault("LOW", 0));
        System.out.println("Completed Visits   : " + completed);
        System.out.println("======================================");
    }
}

class ClinicManager {
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
            if (patient.getPatientId().equalsIgnoreCase(id.trim())) return patient;
        }
        return null;
    }

    public Doctor searchDoctor(String id) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorId().equalsIgnoreCase(id.trim())) return doctor;
        }
        return null;
    }

    public TriageResult performTriage(Patient patient, Map<Symptom, Integer> selectedSymptoms) {
        TriageResult result = triageEngine.evaluate(selectedSymptoms);
        priorityCount.put(result.getPriority(), priorityCount.getOrDefault(result.getPriority(), 0) + 1);
        return result;
    }

    public boolean bookAppointment(String id, Patient patient, Doctor doctor, String date, String time) {
        if (!doctor.getAvailableSlots().contains(time)) return false;
        if (!doctor.bookSlot(time)) return false;

        Appointment appointment = new Appointment(id, patient, doctor, date, time);
        appointments.add(appointment);
        FileManager.saveAppointment(appointment);
        return true;
    }

    public List<Patient> getPatients() { return patients; }
    public List<Doctor> getDoctors() { return doctors; }
    public List<Appointment> getAppointments() { return appointments; }
    public Map<String, Integer> getPriorityCount() { return priorityCount; }

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

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ClinicManager clinic = new ClinicManager();
    static int patientCounter = 1001;
    static int doctorCounter = 504;
    static int appointmentCounter = 1;
    static int recordCounter = 1;
    static List<Symptom> symptoms = new ArrayList<>();

    public static void main(String[] args) {
        loadSymptoms();
        addSampleDoctors();

        System.out.println("\n==============================================");
        System.out.println("              WELCOME TO SMARTCARE");
        System.out.println("       Clinic Management & Triage System");
        System.out.println("==============================================");
        System.out.println("\nEducational Rule-Based Triage Simulation");
        System.out.println("This system does not diagnose medical conditions.");
        System.out.println("It is not a replacement for professional medical care.");

        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": registerPatient(); break;
                case "2": clinic.displayAllPatients(); break;
                case "3": searchPatient(); break;
                case "4": registerDoctor(); break;
                case "5": clinic.displayAllDoctors(); break;
                case "6": performTriage(); break;
                case "7": bookAppointment(); break;
                case "8": clinic.displayAppointments(); break;
                case "9": addMedicalRecord(); break;
                case "10": viewPatientHistory(); break;
                case "11": ReportGenerator.generateReport(clinic.getPatients(), clinic.getDoctors(),
                        clinic.getAppointments(), clinic.getPriorityCount()); break;
                case "12":
                    System.out.println("\nThank you for using SmartCare.");
                    scanner.close();
                    return;
                default: System.out.println("Invalid choice. Please select 1-12.");
            }
        }
    }

    static void displayMenu() {
        System.out.println("\n============== SMARTCARE MENU ==============");
        System.out.println("1.  Register Patient");
        System.out.println("2.  View All Patients");
        System.out.println("3.  Search Patient");
        System.out.println("4.  Register Doctor");
        System.out.println("5.  View All Doctors");
        System.out.println("6.  Perform Triage");
        System.out.println("7.  Book Appointment");
        System.out.println("8.  View Appointments");
        System.out.println("9.  Add Medical Record");
        System.out.println("10. View Patient History");
        System.out.println("11. Generate Daily Report");
        System.out.println("12. Exit");
        System.out.println("============================================");
        System.out.print("Enter your choice: ");
    }

    static void registerPatient() {
        System.out.println("\n========== REGISTER PATIENT ==========");
        String name = InputValidator.readNonEmpty(scanner, "Enter patient name: ");
        System.out.print("Enter age: ");
        int age = InputValidator.readAge(scanner);
        String contact = InputValidator.readNonEmpty(scanner, "Enter contact number: ");

        String id = "P" + patientCounter++;
        clinic.addPatient(new Patient(id, name, age, contact));
        System.out.println("\nPatient registered successfully.");
        System.out.println("Patient ID: " + id);
    }

    static void searchPatient() {
        System.out.println("\n========== SEARCH PATIENT ==========");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();
        Patient patient = clinic.searchPatient(id);

        if (patient == null) System.out.println("Patient not found.");
        else System.out.println("\nPatient Found:\n" + patient);
    }

    static void registerDoctor() {
        System.out.println("\n========== REGISTER DOCTOR ==========");
        String name = InputValidator.readNonEmpty(scanner, "Enter doctor name: ");
        String specialization = InputValidator.readNonEmpty(scanner, "Enter specialization: ");

        String id = "D" + doctorCounter++;
        clinic.addDoctor(new Doctor(id, name, specialization));
        System.out.println("\nDoctor registered successfully.");
        System.out.println("Doctor ID: " + id);
    }

    static void addSampleDoctors() {
        clinic.addDoctorWithoutSaving(new Doctor("D501", "Dr. Rajiv Sharma", "General Medicine"));
        clinic.addDoctorWithoutSaving(new Doctor("D502", "Dr. Priya Mehta", "Cardiology"));
        clinic.addDoctorWithoutSaving(new Doctor("D503", "Dr. Ananya Verma", "Pediatrics"));
    }

    static void loadSymptoms() {
        symptoms.clear();
        symptoms.add(new Symptom("Headache", 1));
        symptoms.add(new Symptom("Fever", 1));
        symptoms.add(new Symptom("Cough", 1));
        symptoms.add(new Symptom("Cold", 1));
        symptoms.add(new Symptom("Difficulty Breathing", 4));
        symptoms.add(new Symptom("Chest Pain", 3));
        symptoms.add(new Symptom("Dizziness", 2));
        symptoms.add(new Symptom("Vomiting", 1));
        symptoms.add(new Symptom("Diarrhea", 1));
        symptoms.add(new Symptom("Abdominal Pain", 2));
        symptoms.add(new Symptom("Constipation", 1));
        symptoms.add(new Symptom("Sore Throat", 1));
        symptoms.add(new Symptom("Body Pain", 1));
        symptoms.add(new Symptom("Fatigue", 1));
        symptoms.add(new Symptom("Skin Rash", 1));
        symptoms.add(new Symptom("Loss of Appetite", 1));
        symptoms.add(new Symptom("Fainting / Unconsciousness", 5));
        symptoms.add(new Symptom("Heart Problem", 3));
        symptoms.add(new Symptom("Artery Problem", 3));
        symptoms.add(new Symptom("Seizure", 5));
    }

    static void performTriage() {
        System.out.println("\n========== TRIAGE SYSTEM ==========");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();
        Patient patient = clinic.searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("\nPatient: " + patient.getName());
        System.out.println("Select symptoms from the list below:");
        System.out.println("--------------------------------------------");
        for (int i = 0; i < symptoms.size(); i++) {
            System.out.println((i + 1) + ". " + symptoms.get(i).getName());
        }
        System.out.println("--------------------------------------------");
        System.out.println("Enter symptom numbers separated by spaces.");
        System.out.println("Example: 1 4 8");
        System.out.print("> ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("No symptoms selected.");
            return;
        }

        LinkedHashSet<Integer> selectedIndexes = new LinkedHashSet<>();
        String[] values = input.split("\\s+");

        for (String value : values) {
            try {
                int number = Integer.parseInt(value);
                if (number >= 1 && number <= symptoms.size()) {
                    selectedIndexes.add(number - 1);
                } else {
                    System.out.println("Symptom number " + number + " is out of range and was ignored.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid symptom entry '" + value + "' was ignored.");
            }
        }

        if (selectedIndexes.isEmpty()) {
            System.out.println("No valid symptoms selected.");
            return;
        }

        Map<Symptom, Integer> selectedSymptoms = new LinkedHashMap<>();

        for (int index : selectedIndexes) {
            Symptom symptom = symptoms.get(index);
            System.out.println("\nSeverity for " + symptom.getName() + ":");
            System.out.println("1. Mild");
            System.out.println("2. Moderate");
            System.out.println("3. Severe");
            System.out.print("Enter severity: ");
            int severity = InputValidator.readChoice(scanner, 1, 3);
            selectedSymptoms.put(symptom, severity);
        }

        TriageResult result = clinic.performTriage(patient, selectedSymptoms);

        System.out.println("\n========== TRIAGE RESULT ==========");
        System.out.println("Total Score : " + result.getScore());
        System.out.println("Priority    : " + result.getPriority());
        System.out.println("Reason      : " + result.getReason());
        System.out.println("===================================");

        if (result.getPriority().equals("HIGH")) {
            System.out.println("\nNOTICE: HIGH PRIORITY FLAG.");
            System.out.println("The project has flagged this case for human review.");
        }
    }

    static void bookAppointment() {
        System.out.println("\n========== BOOK APPOINTMENT ==========");
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();
        Patient patient = clinic.searchPatient(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        clinic.displayAllDoctors();
        System.out.print("\nEnter Doctor ID: ");
        String doctorId = scanner.nextLine().trim();
        Doctor doctor = clinic.searchDoctor(doctorId);

        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        if (doctor.getAvailableSlots().isEmpty()) {
            System.out.println("No available slots for this doctor.");
            return;
        }

        System.out.println("\nAvailable slots: " + doctor.getAvailableSlots());
        System.out.print("Enter time slot: ");
        String time = scanner.nextLine().trim();

        if (!doctor.getAvailableSlots().contains(time)) {
            System.out.println("Selected slot is not available.");
            return;
        }

        String date = InputValidator.readDate(scanner);
        String appointmentId = "A" + appointmentCounter++;

        if (clinic.bookAppointment(appointmentId, patient, doctor, date, time)) {
            System.out.println("\nAppointment booked successfully.");
            System.out.println("Appointment ID: " + appointmentId);
        } else {
            System.out.println("Unable to book appointment.");
        }
    }

    static void addMedicalRecord() {
        System.out.println("\n========== ADD MEDICAL RECORD ==========");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();
        Patient patient = clinic.searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        String symptomInput = InputValidator.readNonEmpty(scanner,
                "Enter symptoms separated by commas: ");
        List<Symptom> recordSymptoms = new ArrayList<>();

        for (String name : symptomInput.split(",")) {
            String trimmed = name.trim();
            if (!trimmed.isEmpty()) recordSymptoms.add(new Symptom(trimmed, 0));
        }

        String notes = InputValidator.readNonEmpty(scanner, "Doctor notes: ");
        String prescription = InputValidator.readNonEmpty(scanner, "Prescription information: ");
        String followUp = InputValidator.readNonEmpty(scanner, "Follow-up date: ");

        MedicalRecord record = new MedicalRecord(
                "R" + recordCounter++,
                LocalDate.now().toString(),
                recordSymptoms,
                notes,
                prescription,
                followUp
        );

        patient.addMedicalRecord(record);
        System.out.println("\nMedical record added successfully.");
    }

    static void viewPatientHistory() {
        System.out.println("\n========== PATIENT HISTORY ==========");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();
        Patient patient = clinic.searchPatient(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("\nPatient Information:\n" + patient);
        System.out.println("\n========== VISIT HISTORY ==========");

        if (patient.getMedicalRecords().isEmpty()) {
            System.out.println("No medical records available.");
        } else {
            for (MedicalRecord record : patient.getMedicalRecords()) {
                System.out.println("\n--------------------------------");
                System.out.println(record);
            }
        }
    }
}
