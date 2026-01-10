package controller;

import view.StudentDashboardView;
import dao.StudentDAO;
import javax.swing.JOptionPane;

public class StudentController {
    private StudentDashboardView view;
    private StudentDAO studentDAO;

    public StudentController(StudentDashboardView view) {
        this.view = view;
        this.studentDAO = new StudentDAO();
        this.view.addEditProfileListener(e -> handleEditProfile());
    }

    private void handleEditProfile() {
        String[] data = view.showEditProfileDialog();
        if (data != null) {
            String name = data[0];
            String email = data[1];
            String mobile = data[2];
            String studentId = "stu02";
            if (studentDAO.updateStudent(studentId, name, email, "BSc in Information Technology")) {
                view.updateProfileDisplay(name, email, mobile);
                JOptionPane.showMessageDialog(view, "Profile Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(view, "Update Failed!");
            }
        }
    }
}