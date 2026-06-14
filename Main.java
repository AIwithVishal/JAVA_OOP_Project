import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

class Student {
    private String name;
    private int grade;

    Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + "    Marks: " + grade;
    }
}

class GradeTracker {

    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(String name, int grade) {
        students.add(new Student(name, grade));
    }

    public int average() {

        if (students.isEmpty())
            return 0;

        int sum = 0;

        for (Student student : students) {
            sum += student.getGrade();
        }

        return sum / students.size();
    }

    public int highest() {

        if (students.isEmpty())
            return 0;

        int highest = students.get(0).getGrade();

        for (Student student : students) {

            if (student.getGrade() > highest) {
                highest = student.getGrade();
            }
        }

        return highest;
    }

    public int lowest() {

        if (students.isEmpty())
            return 0;

        int lowest = students.get(0).getGrade();

        for (Student student : students) {

            if (student.getGrade() < lowest) {
                lowest = student.getGrade();
            }
        }

        return lowest;
    }

    public String getAllStudents() {

        String result = "";

        for (Student student : students) {
            result += student + "\n";
        }

        return result;
    }

    public int totalStudents() {
        return students.size();
    }
}

class GradeTrackerGUI {

    private GradeTracker tracker;

    private JFrame frame;
    private JTextField nameField;
    private JTextField gradeField;
    private JTextArea area;

    private JButton addButton;
    private JButton reportButton;

    GradeTrackerGUI() {

        tracker = new GradeTracker();

        frame = new JFrame("Student Grade Tracker");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);

        JLabel gradeLabel = new JLabel("Grade:");
        gradeField = new JTextField(15);

        addButton = new JButton("Add Student");
        reportButton = new JButton("Generate Report");

        area = new JTextArea(15, 30);
        area.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(area);

        frame.add(nameLabel);
        frame.add(nameField);

        frame.add(gradeLabel);
        frame.add(gradeField);

        frame.add(addButton);

        frame.add(scrollPane);

        frame.add(reportButton);

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String name = nameField.getText();

                    int grade = Integer.parseInt(
                            gradeField.getText());

                    tracker.addStudent(name, grade);

                    area.setText(
                            tracker.getAllStudents());

                    nameField.setText("");
                    gradeField.setText("");

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Enter valid data!"
                    );
                }
            }
        });

        reportButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String report =
                        "Total Students: "
                                + tracker.totalStudents()
                                + "\n\nAverage Grade: "
                                + tracker.average()
                                + "\nHighest Grade: "
                                + tracker.highest()
                                + "\nLowest Grade: "
                                + tracker.lowest();

                JOptionPane.showMessageDialog(
                        frame,
                        report
                );
            }
        });

        frame.setVisible(true);
    }
}

public class Main {

    public static void main(String[] args) {

        new GradeTrackerGUI();

    }
}