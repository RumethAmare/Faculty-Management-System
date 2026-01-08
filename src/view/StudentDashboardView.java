package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class StudentDashboardView extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JButton btnEdit = new JButton("Edit Profile");
    private JLabel lblName = new JLabel("Example Student Name");
    private JLabel lblEmail = new JLabel("student@example.com");
    private JLabel lblMobile = new JLabel("0712345678");

    public StudentDashboardView() {
        setTitle("Student Dashboard - Faculty Management System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane.addTab("My Profile", createProfilePanel());
        tabbedPane.addTab("Timetable", createTimetablePanel());
        tabbedPane.addTab("Course Grades", createGradesPanel());

        add(tabbedPane);
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        panel.add(new JLabel("Student ID:"));
        panel.add(new JLabel("STU/2024/001"));

        panel.add(new JLabel("Full Name:"));
        panel.add(lblName);

        panel.add(new JLabel("Degree:"));
        panel.add(new JLabel("BSc in Information Technology"));

        panel.add(new JLabel("Email:"));
        panel.add(lblEmail);

        panel.add(new JLabel("Mobile:"));
        panel.add(lblMobile);

        panel.add(new JLabel(""));
        panel.add(btnEdit);

        return panel;
    }

    private JPanel createTimetablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        Object[][] data = {
                {"08:30 - 10:30", "OOP (Java)", "Database", "Networking", "Maths", "English"},
                {"10:30 - 12:30", "Lab", "OOP Lab", "Library", "Networking Lab", "Project"}
        };

        JTable timetable = new JTable(new DefaultTableModel(data, columns));
        panel.add(new JLabel("Weekly Class Schedule", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(new JScrollPane(timetable), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createGradesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Course Code", "Course Name", "Grade"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        model.addRow(new Object[]{"CSCI 21052", "Object Oriented Programming", "A"});
        model.addRow(new Object[]{"ETEC 21062", "Database Management", "B+"});

        JTable gradeTable = new JTable(model);
        panel.add(new JLabel("Enrolled Courses with Grades", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(new JScrollPane(gradeTable), BorderLayout.CENTER);

        return panel;
    }

    public void addEditProfileListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public String[] showEditProfileDialog() {
        JTextField nameField = new JTextField(lblName.getText());
        JTextField emailField = new JTextField(lblEmail.getText());
        JTextField mobileField = new JTextField(lblMobile.getText());

        Object[] fields = {
                "Name:", nameField,
                "Email:", emailField,
                "Mobile:", mobileField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Edit Profile", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            return new String[]{nameField.getText(), emailField.getText(), mobileField.getText()};
        }
        return null;
    }

    public void updateProfileDisplay(String name, String email, String mobile) {
        lblName.setText(name);
        lblEmail.setText(email);
        lblMobile.setText(mobile);
    }
}