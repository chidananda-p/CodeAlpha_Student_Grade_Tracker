import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GradeTracker {

    private final ArrayList<Student> students;

    public GradeTracker() {
        this.students = new ArrayList<>();
    }

    public void addStudent(String name, double score) {
        students.add(new Student(name, score));
    }

    public boolean updateStudent(int index, String name, double score) {
        if (index >= 0 && index < students.size()) {
            students.set(index, new Student(name, score));
            return true;
        }
        return false;
    }

    public boolean deleteStudent(int index) {
        if (index >= 0 && index < students.size()) {
            students.remove(index);
            return true;
        }
        return false;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public int getStudentCount() {
        return students.size();
    }

    public double[] getScoresArray() {
        double[] scores = new double[students.size()];
        for (int i = 0; i < students.size(); i++) {
            scores[i] = students.get(i).getScore();
        }
        return scores;
    }

    public double calculateAverage() {
        double[] scores = getScoresArray();
        if (scores.length == 0) return 0.0;

        double sum = 0.0;
        for (double score : scores) {
            sum += score;
        }
        return Math.round((sum / scores.length) * 100.0) / 100.0;
    }

    public double calculateHighest() {
        double[] scores = getScoresArray();
        if (scores.length == 0) return 0.0;

        double max = scores[0];
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > max) {
                max = scores[i];
            }
        }
        return max;
    }

    public double calculateLowest() {
        double[] scores = getScoresArray();
        if (scores.length == 0) return 0.0;

        double min = scores[0];
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] < min) {
                min = scores[i];
            }
        }
        return min;
    }

    public Student getHighestStudent() {
        if (students.isEmpty()) return null;
        Student top = students.get(0);
        for (int i = 1; i < students.size(); i++) {
            if (students.get(i).getScore() > top.getScore()) {
                top = students.get(i);
            }
        }
        return top;
    }

    public Student getLowestStudent() {
        if (students.isEmpty()) return null;
        Student lowest = students.get(0);
        for (int i = 1; i < students.size(); i++) {
            if (students.get(i).getScore() < lowest.getScore()) {
                lowest = students.get(i);
            }
        }
        return lowest;
    }

    public String generateSummaryReport() {
        StringBuilder sb = new StringBuilder();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        sb.append("========================================================================\n");
        sb.append("                    STUDENT GRADE SUMMARY REPORT                        \n");
        sb.append("                    Generated on: ").append(timestamp).append("\n");
        sb.append("========================================================================\n\n");

        sb.append("------------------------------------------------------------------------\n");
        sb.append(" 1. OVERALL CLASS STATISTICS\n");
        sb.append("------------------------------------------------------------------------\n");
        sb.append(String.format("  * Total Students : %d\n", students.size()));
        sb.append(String.format("  * Class Average  : %.2f%%\n", calculateAverage()));

        Student top = getHighestStudent();
        sb.append(String.format("  * Highest Score  : %.2f%% (%s)\n",
                calculateHighest(), (top != null ? top.getName() : "N/A")));

        Student lowest = getLowestStudent();
        sb.append(String.format("  * Lowest Score   : %.2f%% (%s)\n\n",
                calculateLowest(), (lowest != null ? lowest.getName() : "N/A")));

        sb.append("------------------------------------------------------------------------\n");
        sb.append(" 2. COMPLETE STUDENT ROSTER\n");
        sb.append("------------------------------------------------------------------------\n");
        sb.append(String.format("%-6s | %-26s | %-8s | %-6s | %-8s\n",
                "No.", "Student Name", "Score", "Grade", "Status"));
        sb.append("------------------------------------------------------------------------\n");

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            sb.append(String.format("%-6d | %-26s | %6.2f%%  | %-6s | %-8s\n",
                    (i + 1),
                    s.getName(),
                    s.getScore(),
                    s.getLetterGrade(),
                    s.getStatus()));
        }

        sb.append("------------------------------------------------------------------------\n");
        sb.append("                         END OF SUMMARY REPORT                          \n");
        sb.append("========================================================================\n");

        return sb.toString();
    }

    public void loadSampleData() {
        students.clear();
        addStudent("Eshwar", 95.5);
        addStudent("Jaydev", 35.0);
        addStudent("Sonal", 92.5);
        addStudent("Lokesh", 25.0);
        addStudent("Amar", 85.0);
        addStudent("Lucky", 68.5);
        addStudent("Nora", 54.0);
        addStudent("Isro", 98.5);
        addStudent("Nanda", 75.0);
    }
}
