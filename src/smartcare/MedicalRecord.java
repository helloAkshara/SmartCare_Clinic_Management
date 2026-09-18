package smartcare;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
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

            if (i < symptoms.size() - 1) {
                symptomText.append(", ");
            }
        }

        return "Record ID: " + recordId +
                "\nDate: " + date +
                "\nSymptoms: " + symptomText +
                "\nDoctor Notes: " + doctorNotes +
                "\nPrescription: " + prescription +
                "\nFollow-up Date: " + followUpDate;
    }
}