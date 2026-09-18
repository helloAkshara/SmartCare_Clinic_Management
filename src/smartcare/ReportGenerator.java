package smartcare;

import java.util.List;
import java.util.Map;

public class ReportGenerator {

    public static void generateReport(
            List<Patient> patients,
            List<Doctor> doctors,
            List<Appointment> appointments,
            Map<String, Integer> priorityCount) {

        int completed = 0;

        for (Appointment appointment : appointments) {
            if (appointment.getStatus().equals("Completed")) {
                completed++;
            }
        }

        System.out.println("\n======================================");
        System.out.println("          SMARTCARE REPORT");
        System.out.println("======================================");
        System.out.println("Total Patients     : " + patients.size());
        System.out.println("Total Doctors      : " + doctors.size());
        System.out.println("Appointments       : " + appointments.size());
        System.out.println("High Priority      : " +
                priorityCount.getOrDefault("HIGH", 0));
        System.out.println("Medium Priority    : " +
                priorityCount.getOrDefault("MEDIUM", 0));
        System.out.println("Low Priority       : " +
                priorityCount.getOrDefault("LOW", 0));
        System.out.println("Completed Visits   : " + completed);
        System.out.println("======================================");
    }
}