package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JButton btnLogin = new JButton("Login");

    public LoginView() {
        setTitle("Faculty Management System - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel("  Username:"));
        add(txtUser);
        add(new JLabel("  Password:"));
        add(txtPass);
        add(new JLabel(""));
        add(btnLogin);
    }

    public String getUsername() { return txtUser.getText(); }
    public String getPassword() { return new String(txtPass.getPassword()); }

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }
}