package smartcare;

public class Appointment {
    private String appointmentId, date, time, status;
    private Patient patient;
    private Doctor doctor;

    public Appointment(String appointmentId, Patient patient, Doctor doctor,
                       String date, String time) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = "Scheduled";
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

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