package controller;

import view.AdminDashboardView;
import dao.*;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AdminController {
    private AdminDashboardView view;
    private StudentDAO studentDAO;
    private LecturerDAO lecturerDAO;
    private CourseDAO courseDAO;
    private DepartmentDAO deptDAO;
    private DegreeDAO degreeDAO;

    public AdminController(AdminDashboardView view) {
        this.view = view;
        this.studentDAO = new StudentDAO();
        this.lecturerDAO = new LecturerDAO();
        this.courseDAO = new CourseDAO();
        this.deptDAO = new DepartmentDAO();
        this.degreeDAO = new DegreeDAO();
        initController();
        refreshAllTables();
    }

    private void initController() {
        view.getBtnAddStudent().addActionListener(e -> addStudent());
        view.getBtnUpdateStudent().addActionListener(e -> updateStudent());
        view.getBtnDeleteStudent().addActionListener(e -> deleteStudent());

        view.getBtnAddLecturer().addActionListener(e -> addLecturer());
        view.getBtnUpdateLecturer().addActionListener(e -> updateLecturer());
        view.getBtnDeleteLecturer().addActionListener(e -> deleteLecturer());

        view.getBtnAddCourse().addActionListener(e -> addCourse());
        view.getBtnUpdateCourse().addActionListener(e -> updateCourse());
        view.getBtnDeleteCourse().addActionListener(e -> deleteCourse());

        view.getBtnAddDept().addActionListener(e -> addDepartment());
        view.getBtnUpdateDept().addActionListener(e -> updateDepartment());
        view.getBtnDeleteDept().addActionListener(e -> deleteDepartment());

        view.getBtnAddDegree().addActionListener(e -> addDegree());
        view.getBtnUpdateDegree().addActionListener(e -> updateDegree());
        view.getBtnDeleteDegree().addActionListener(e -> deleteDegree());
    }

    private void refreshAllTables() {
        refreshStudentTable();
        refreshLecturerTable();
        refreshCourseTable();
        refreshDeptTable();
        refreshDegreeTable();
    }

    private void addStudent() {
        String[] data = view.showStudentDialog("Add Student", null);
        if (data != null && studentDAO.addStudent(data[0], data[1], data[2], data[3])) {
            JOptionPane.showMessageDialog(view, "Student Added!");
            refreshStudentTable();
        }
    }

    private void updateStudent() {
        int row = view.getStudentTable().getSelectedRow();
        if (row != -1) {
            String[] current = {
                    view.getStudentModel().getValueAt(row, 0).toString(),
                    view.getStudentModel().getValueAt(row, 1).toString(),
                    view.getStudentModel().getValueAt(row, 2).toString(),
                    view.getStudentModel().getValueAt(row, 3).toString()
            };
            String[] data = view.showStudentDialog("Update Student", current);
            if (data != null && studentDAO.updateStudent(data[0], data[1], data[2], data[3])) {
                JOptionPane.showMessageDialog(view, "Student Updated!");
                refreshStudentTable();
            }
        }
    }

    private void deleteStudent() {
        int row = view.getStudentTable().getSelectedRow();
        if (row != -1) {
            String id = view.getStudentModel().getValueAt(row, 0).toString();
            if (studentDAO.deleteStudent(id)) {
                JOptionPane.showMessageDialog(view, "Deleted!");
                refreshStudentTable();
            }
        }
    }

    private void addLecturer() {
        String[] data = view.showLecturerDialog("Add Lecturer", null);
        if (data != null && lecturerDAO.addLecturer(data[0], data[1], data[2], data[3])) {
            JOptionPane.showMessageDialog(view, "Lecturer Added!");
            refreshLecturerTable();
        }
    }

    private void updateLecturer() {
        int row = view.getLecturerTable().getSelectedRow();
        if (row != -1) {
            String[] current = {
                    view.getLecturerModel().getValueAt(row, 0).toString(),
                    view.getLecturerModel().getValueAt(row, 1).toString(),
                    view.getLecturerModel().getValueAt(row, 2).toString(),
                    view.getLecturerModel().getValueAt(row, 3).toString()
            };
            String[] data = view.showLecturerDialog("Update Lecturer", current);
            if (data != null && lecturerDAO.updateLecturer(data[0], data[1], data[2], data[3])) {
                JOptionPane.showMessageDialog(view, "Lecturer Updated!");
                refreshLecturerTable();
            }
        }
    }

    private void deleteLecturer() {
        int row = view.getLecturerTable().getSelectedRow();
        if (row != -1) {
            String id = view.getLecturerModel().getValueAt(row, 0).toString();
            if (lecturerDAO.deleteLecturer(id)) {
                JOptionPane.showMessageDialog(view, "Deleted!");
                refreshLecturerTable();
            }
        }
    }

    private void addCourse() {
        String[] data = view.showCourseDialog("Add Course", null);
        if (data != null) {
            try {
                if (courseDAO.addCourse(data[0], data[1], Integer.parseInt(data[2]), data[3])) {
                    JOptionPane.showMessageDialog(view, "Course Added!");
                    refreshCourseTable();
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Invalid Credits!"); }
        }
    }

    private void updateCourse() {
        int row = view.getCourseTable().getSelectedRow();
        if (row != -1) {
            String[] current = {
                    view.getCourseModel().getValueAt(row, 0).toString(),
                    view.getCourseModel().getValueAt(row, 1).toString(),
                    view.getCourseModel().getValueAt(row, 2).toString(),
                    view.getCourseModel().getValueAt(row, 3).toString()
            };
            String[] data = view.showCourseDialog("Update Course", current);
            if (data != null && courseDAO.updateCourse(data[0], data[1], Integer.parseInt(data[2]), data[3])) {
                JOptionPane.showMessageDialog(view, "Course Updated!");
                refreshCourseTable();
            }
        }
    }

    private void deleteCourse() {
        int row = view.getCourseTable().getSelectedRow();
        if (row != -1) {
            String id = view.getCourseModel().getValueAt(row, 0).toString();
            if (courseDAO.deleteCourse(id)) {
                JOptionPane.showMessageDialog(view, "Deleted!");
                refreshCourseTable();
            }
        }
    }

    private void addDepartment() {
        String[] data = view.showDeptDialog("Add Department", null);
        if (data != null) {
            try {
                if (deptDAO.addDepartment(data[0], data[1], data[2], Integer.parseInt(data[3]))) {
                    JOptionPane.showMessageDialog(view, "Dept Added!");
                    refreshDeptTable();
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Invalid Staff Count!"); }
        }
    }

    private void updateDepartment() {
        int row = view.getDeptTable().getSelectedRow();
        if (row != -1) {
            String[] current = {
                    view.getDeptModel().getValueAt(row, 0).toString(),
                    view.getDeptModel().getValueAt(row, 1).toString(),
                    view.getDeptModel().getValueAt(row, 2).toString(),
                    view.getDeptModel().getValueAt(row, 3).toString()
            };
            String[] data = view.showDeptDialog("Update Department", current);
            if (data != null && deptDAO.updateDepartment(data[0], data[1], data[2], Integer.parseInt(data[3]))) {
                JOptionPane.showMessageDialog(view, "Dept Updated!");
                refreshDeptTable();
            }
        }
    }

    private void deleteDepartment() {
        int row = view.getDeptTable().getSelectedRow();
        if (row != -1) {
            String id = view.getDeptModel().getValueAt(row, 0).toString();
            if (deptDAO.deleteDepartment(id)) {
                JOptionPane.showMessageDialog(view, "Deleted!");
                refreshDeptTable();
            }
        }
    }

    private void addDegree() {
        String[] data = view.showDegreeDialog("Add Degree", null);
        if (data != null && degreeDAO.addDegree(data[0], data[1], data[2])) {
            JOptionPane.showMessageDialog(view, "Degree Added!");
            refreshDegreeTable();
        }
    }

    private void updateDegree() {
        int row = view.getDegreeTable().getSelectedRow();
        if (row != -1) {
            String[] current = {
                    view.getDegreeModel().getValueAt(row, 0).toString(),
                    view.getDegreeModel().getValueAt(row, 1).toString(),
                    view.getDegreeModel().getValueAt(row, 2).toString()
            };
            String[] data = view.showDegreeDialog("Update Degree", current);
            if (data != null && degreeDAO.updateDegree(data[0], data[1], data[2])) {
                JOptionPane.showMessageDialog(view, "Degree Updated!");
                refreshDegreeTable();
            }
        }
    }

    private void deleteDegree() {
        int row = view.getDegreeTable().getSelectedRow();
        if (row != -1) {
            String id = view.getDegreeModel().getValueAt(row, 0).toString();
            if (degreeDAO.deleteDegree(id)) {
                JOptionPane.showMessageDialog(view, "Deleted!");
                refreshDegreeTable();
            }
        }
    }

    public void refreshStudentTable() {
        DefaultTableModel model = view.getStudentModel();
        model.setRowCount(0);
        List<Student> list = studentDAO.getAllStudents();
        for (Student s : list) model.addRow(new Object[]{s.getId(), s.getName(), s.getEmail(), s.getDegree()});
    }

    public void refreshLecturerTable() {
        DefaultTableModel model = view.getLecturerModel();
        model.setRowCount(0);
        List<Lecturer> list = lecturerDAO.getAllLecturers();
        for (Lecturer l : list) model.addRow(new Object[]{l.getId(), l.getName(), l.getEmail(), l.getDepartment()});
    }

    public void refreshCourseTable() {
        DefaultTableModel model = view.getCourseModel();
        model.setRowCount(0);
        List<Course> list = courseDAO.getAllCourses();
        for (Course c : list) model.addRow(new Object[]{c.getCourseId(), c.getCourseName(), c.getCredits(), c.getLecturerId()});
    }

    public void refreshDeptTable() {
        DefaultTableModel model = view.getDeptModel();
        model.setRowCount(0);
        List<Department> list = deptDAO.getAllDepartments();
        for (Department d : list) model.addRow(new Object[]{d.getId(), d.getName(), d.getHod(), d.getStaffCount()});
    }

    public void refreshDegreeTable() {
        DefaultTableModel model = view.getDegreeModel();
        model.setRowCount(0);
        List<Degree> list = degreeDAO.getAllDegrees();
        for (Degree d : list) model.addRow(new Object[]{d.getId(), d.getName(), d.getDeptId()});
    }
}