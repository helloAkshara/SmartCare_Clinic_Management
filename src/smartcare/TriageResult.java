package smartcare;

public class TriageResult {
    private String priority, reason;
    private int score;

    public TriageResult(String priority, int score, String reason) {
        this.priority = priority;
        this.score = score;
        this.reason = reason;
    }

    public String getPriority() {
        return priority;
    }

    public int getScore() {
        return score;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "Priority: " + priority +
                "\nScore: " + score +
                "\nReason: " + reason;
    }
}