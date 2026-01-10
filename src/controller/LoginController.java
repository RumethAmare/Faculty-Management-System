package controller;

import view.LoginView;
import view.AdminDashboardView;
import view.StudentDashboardView;
import view.LecturerDashboardView;
import controller.AdminController;
import dao.UserDAO;
import javax.swing.JOptionPane;

public class LoginController {
    private LoginView view;
    private UserDAO userDAO;

    public LoginController(LoginView view) {
        this.view = view;
        this.userDAO = new UserDAO();
        this.view.addLoginListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = view.getUsername();
        String password = view.getPassword();

        String role = userDAO.validateLogin(username, password);

        if (role != null) {
            view.dispose();

            switch (role) {
                case "Admin":
                    AdminDashboardView adminView = new AdminDashboardView();
                    new AdminController(adminView);
                    adminView.setVisible(true);
                    break;

                case "Student":
                    StudentDashboardView studentView = new StudentDashboardView();
                    new StudentController(studentView);
                    studentView.setVisible(true);
                    break;

                case "Lecturer":
                    new LecturerDashboardView().setVisible(true);
                    break;

                default:
                    JOptionPane.showMessageDialog(view, "Undefined Role assigned to this user.");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Invalid Username or Password!");
        }
    }
}