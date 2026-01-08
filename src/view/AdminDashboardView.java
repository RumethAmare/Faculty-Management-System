package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminDashboardView extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();

    private JTable studentTable;
    private DefaultTableModel studentModel;
    private JButton btnAddStudent = new JButton("Add Student");
    private JButton btnUpdateStudent = new JButton("Update");
    private JButton btnDeleteStudent = new JButton("Delete");

    private JTable lecturerTable;
    private DefaultTableModel lecturerModel;
    private JButton btnAddLecturer = new JButton("Add Lecturer");
    private JButton btnUpdateLecturer = new JButton("Update");
    private JButton btnDeleteLecturer = new JButton("Delete");

    private JTable courseTable;
    private DefaultTableModel courseModel;
    private JButton btnAddCourse = new JButton("Add Course");
    private JButton btnUpdateCourse = new JButton("Update");
    private JButton btnDeleteCourse = new JButton("Delete");

    private JTable deptTable;
    private DefaultTableModel deptModel;
    private JButton btnAddDept = new JButton("Add Department");
    private JButton btnUpdateDept = new JButton("Update");
    private JButton btnDeleteDept = new JButton("Delete");

    private JTable degreeTable;
    private DefaultTableModel degreeModel;
    private JButton btnAddDegree = new JButton("Add Degree");
    private JButton btnUpdateDegree = new JButton("Update");
    private JButton btnDeleteDegree = new JButton("Delete");

    public AdminDashboardView() {
        setTitle("Admin Dashboard - Faculty Management System");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane.addTab("Students", createStudentPanel());
        tabbedPane.addTab("Lecturers", createLecturerPanel());
        tabbedPane.addTab("Courses", createCoursePanel());
        tabbedPane.addTab("Departments", createDepartmentPanel());
        tabbedPane.addTab("Degrees", createDegreePanel());

        add(tabbedPane);
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name", "Email", "Degree"};
        studentModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(studentModel);
        panel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAddStudent); btnPanel.add(btnUpdateStudent); btnPanel.add(btnDeleteStudent);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createLecturerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name", "Email", "Department"};
        lecturerModel = new DefaultTableModel(columns, 0);
        lecturerTable = new JTable(lecturerModel);
        panel.add(new JScrollPane(lecturerTable), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAddLecturer); btnPanel.add(btnUpdateLecturer); btnPanel.add(btnDeleteLecturer);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Course ID", "Name", "Credits", "Lecturer ID"};
        courseModel = new DefaultTableModel(columns, 0);
        courseTable = new JTable(courseModel);
        panel.add(new JScrollPane(courseTable), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAddCourse); btnPanel.add(btnUpdateCourse); btnPanel.add(btnDeleteCourse);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createDepartmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Dept ID", "Name", "HOD", "Staff Count"};
        deptModel = new DefaultTableModel(columns, 0);
        deptTable = new JTable(deptModel);
        panel.add(new JScrollPane(deptTable), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAddDept); btnPanel.add(btnUpdateDept); btnPanel.add(btnDeleteDept);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createDegreePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Degree ID", "Name", "Department ID"};
        degreeModel = new DefaultTableModel(columns, 0);
        degreeTable = new JTable(degreeModel);
        panel.add(new JScrollPane(degreeTable), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAddDegree); btnPanel.add(btnUpdateDegree); btnPanel.add(btnDeleteDegree);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    public String[] showStudentDialog(String title, String[] data) {
        JTextField id = new JTextField(data != null ? data[0] : "");
        if (data != null) id.setEditable(false);
        JTextField name = new JTextField(data != null ? data[1] : "");
        JTextField email = new JTextField(data != null ? data[2] : "");
        JTextField degree = new JTextField(data != null ? data[3] : "");
        Object[] fields = {"ID:", id, "Name:", name, "Email:", email, "Degree:", degree};
        int res = JOptionPane.showConfirmDialog(this, fields, title, JOptionPane.OK_CANCEL_OPTION);
        return res == JOptionPane.OK_OPTION ? new String[]{id.getText(), name.getText(), email.getText(), degree.getText()} : null;
    }

    public String[] showLecturerDialog(String title, String[] data) {
        JTextField id = new JTextField(data != null ? data[0] : "");
        if (data != null) id.setEditable(false);
        JTextField name = new JTextField(data != null ? data[1] : "");
        JTextField email = new JTextField(data != null ? data[2] : "");
        JTextField dept = new JTextField(data != null ? data[3] : "");
        Object[] fields = {"ID:", id, "Name:", name, "Email:", email, "Dept:", dept};
        int res = JOptionPane.showConfirmDialog(this, fields, title, JOptionPane.OK_CANCEL_OPTION);
        return res == JOptionPane.OK_OPTION ? new String[]{id.getText(), name.getText(), email.getText(), dept.getText()} : null;
    }

    public String[] showCourseDialog(String title, String[] data) {
        JTextField id = new JTextField(data != null ? data[0] : "");
        if (data != null) id.setEditable(false);
        JTextField name = new JTextField(data != null ? data[1] : "");
        JTextField credits = new JTextField(data != null ? data[2] : "");
        JTextField lecId = new JTextField(data != null ? data[3] : "");
        Object[] fields = {"Course ID:", id, "Name:", name, "Credits:", credits, "Lecturer ID:", lecId};
        int res = JOptionPane.showConfirmDialog(this, fields, title, JOptionPane.OK_CANCEL_OPTION);
        return res == JOptionPane.OK_OPTION ? new String[]{id.getText(), name.getText(), credits.getText(), lecId.getText()} : null;
    }

    public String[] showDeptDialog(String title, String[] data) {
        JTextField id = new JTextField(data != null ? data[0] : "");
        if (data != null) id.setEditable(false);
        JTextField name = new JTextField(data != null ? data[1] : "");
        JTextField hod = new JTextField(data != null ? data[2] : "");
        JTextField staff = new JTextField(data != null ? data[3] : "");
        Object[] fields = {"Dept ID:", id, "Name:", name, "HOD:", hod, "Staff:", staff};
        int res = JOptionPane.showConfirmDialog(this, fields, title, JOptionPane.OK_CANCEL_OPTION);
        return res == JOptionPane.OK_OPTION ? new String[]{id.getText(), name.getText(), hod.getText(), staff.getText()} : null;
    }

    public String[] showDegreeDialog(String title, String[] data) {
        JTextField id = new JTextField(data != null ? data[0] : "");
        if (data != null) id.setEditable(false);
        JTextField name = new JTextField(data != null ? data[1] : "");
        JTextField deptId = new JTextField(data != null ? data[2] : "");
        Object[] fields = {"Degree ID:", id, "Name:", name, "Dept ID:", deptId};
        int res = JOptionPane.showConfirmDialog(this, fields, title, JOptionPane.OK_CANCEL_OPTION);
        return res == JOptionPane.OK_OPTION ? new String[]{id.getText(), name.getText(), deptId.getText()} : null;
    }

    public JButton getBtnAddStudent() { return btnAddStudent; }
    public JButton getBtnUpdateStudent() { return btnUpdateStudent; }
    public JButton getBtnDeleteStudent() { return btnDeleteStudent; }
    public JTable getStudentTable() { return studentTable; }
    public DefaultTableModel getStudentModel() { return studentModel; }
    public JButton getBtnAddLecturer() { return btnAddLecturer; }
    public JButton getBtnUpdateLecturer() { return btnUpdateLecturer; }
    public JButton getBtnDeleteLecturer() { return btnDeleteLecturer; }
    public JTable getLecturerTable() { return lecturerTable; }
    public DefaultTableModel getLecturerModel() { return lecturerModel; }
    public JButton getBtnAddCourse() { return btnAddCourse; }
    public JButton getBtnUpdateCourse() { return btnUpdateCourse; }
    public JButton getBtnDeleteCourse() { return btnDeleteCourse; }
    public JTable getCourseTable() { return courseTable; }
    public DefaultTableModel getCourseModel() { return courseModel; }
    public JButton getBtnAddDept() { return btnAddDept; }
    public JButton getBtnUpdateDept() { return btnUpdateDept; }
    public JButton getBtnDeleteDept() { return btnDeleteDept; }
    public JTable getDeptTable() { return deptTable; }
    public DefaultTableModel getDeptModel() { return deptModel; }
    public JButton getBtnAddDegree() { return btnAddDegree; }
    public JButton getBtnUpdateDegree() { return btnUpdateDegree; }
    public JButton getBtnDeleteDegree() { return btnDeleteDegree; }
    public JTable getDegreeTable() { return degreeTable; }
    public DefaultTableModel getDegreeModel() { return degreeModel; }
}