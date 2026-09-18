package smartcare;

import java.util.Map;

public class TriageEngine {

    public TriageResult evaluate(Map<Symptom, Integer> selectedSymptoms) {
        int score = 0;

        for (Map.Entry<Symptom, Integer> entry : selectedSymptoms.entrySet()) {
            Symptom symptom = entry.getKey();
            int multiplier = entry.getValue();

            score += symptom.getBaseScore() * multiplier;
        }

        if (score >= 8) {
            return new TriageResult(
                    "HIGH",
                    score,
                    "The project rules produced a high-priority score."
            );
        } else if (score >= 4) {
            return new TriageResult(
                    "MEDIUM",
                    score,
                    "The project rules produced a medium-priority score."
            );
        } else {
            return new TriageResult(
                    "LOW",
                    score,
                    "The project rules produced a low-priority score."
            );
        }
    }
}