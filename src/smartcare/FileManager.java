package smartcare;

import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    public static void savePatient(Patient patient) {
        try (FileWriter writer = new FileWriter("patients.txt", true)) {
            writer.write(
                    patient.getPatientId() + "|" +
                            patient.getName() + "|" +
                            patient.getAge() + "|" +
                            patient.getContact() + "\n"
            );
        } catch (IOException e) {
            System.out.println("Error saving patient data.");
        }
    }

    public static void saveDoctor(Doctor doctor) {
        try (FileWriter writer = new FileWriter("doctors.txt", true)) {
            writer.write(
                    doctor.getDoctorId() + "|" +
                            doctor.getName() + "|" +
                            doctor.getSpecialization() + "\n"
            );
        } catch (IOException e) {
            System.out.println("Error saving doctor data.");
        }
    }

    public static void saveAppointment(Appointment appointment) {
        try (FileWriter writer = new FileWriter("appointments.txt", true)) {
            writer.write(
                    appointment.getAppointmentId() + "|" +
                            appointment.getPatient().getPatientId() + "|" +
                            appointment.getDoctor().getDoctorId() + "|" +
                            appointment.getDate() + "|" +
                            appointment.getTime() + "|" +
                            appointment.getStatus() + "\n"
            );
        } catch (IOException e) {
            System.out.println("Error saving appointment data.");
        }
    }
}