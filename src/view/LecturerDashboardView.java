package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LecturerDashboardView extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();

    private JTable courseTable;
    private DefaultTableModel courseModel;

    public LecturerDashboardView() {
        setTitle("Lecturer Dashboard - Faculty Management System");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane.addTab("My Courses", createCoursePanel());
        tabbedPane.addTab("Department Info", createDepartmentPanel());

        add(tabbedPane);
    }

    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("Assigned Courses and Timetable");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitle, BorderLayout.NORTH);

        String[] columns = {"Course Code", "Course Name", "Credits", "Schedule"};
        courseModel = new DefaultTableModel(columns, 0);
        courseTable = new JTable(courseModel);
        panel.add(new JScrollPane(courseTable), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDepartmentPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Department: Computing & Technology"));
        panel.add(new JLabel("Head of Department: Prof. Saman Kumara"));
        panel.add(new JLabel("Office Location: Block C, 2nd Floor"));
        panel.add(new JLabel("Contact: computing@faculty.ac.lk"));

        return panel;
    }

    public DefaultTableModel getCourseModel() {
        return courseModel;
    }
}