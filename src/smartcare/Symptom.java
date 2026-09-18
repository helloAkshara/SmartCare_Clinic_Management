package smartcare;

public class Symptom {
    private String name;
    private int baseScore;

    public Symptom(String name, int baseScore) {
        this.name = name;
        this.baseScore = baseScore;
    }

    public String getName() {
        return name;
    }

    public int getBaseScore() {
        return baseScore;
    }

    @Override
    public String toString() {
        return name + " (base score: " + baseScore + ")";
    }
}