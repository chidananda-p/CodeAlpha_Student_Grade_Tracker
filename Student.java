/**
 * Model representing a student and their academic grade score.
 */
public class Student {
    private String name;
    private double score; // Score in range [0.0, 100.0]

    public Student(String name, double score) {
        setName(name);
        setScore(score);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        this.name = name.trim();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        if (score < 0.0 || score > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0. Given: " + score);
        }
        this.score = Math.round(score * 100.0) / 100.0;
    }

    public String getLetterGrade() {
        if (score >= 90.0) return "A";
        if (score >= 80.0) return "B";
        if (score >= 70.0) return "C";
        if (score >= 60.0) return "D";
        return "F";
    }

    public boolean isPassed() {
        return score >= 60.0;
    }

    public String getStatus() {
        return isPassed() ? "Passed" : "Failed";
    }

    @Override
    public String toString() {
        return name + " - " + score + " (" + getLetterGrade() + ")";
    }
}
