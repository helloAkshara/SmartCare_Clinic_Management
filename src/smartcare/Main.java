package smartcare;

import java.time.LocalDate;
import java.util.*;

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
