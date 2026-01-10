package servlet;

import dao.UserDAO;
import model.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SignupServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();

        try {
            // Read JSON from request body
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Parse JSON
            Map<String, String> userData = gson.fromJson(sb.toString(), Map.class);
            String username = userData.get("username");
            String password = userData.get("password");
            String role = userData.get("role");

            // Validate input
            if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                role == null || role.trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "All fields are required");
                out.print(gson.toJson(result));
                return;
            }

            // Validate username length
            if (username.length() < 3) {
                result.put("success", false);
                result.put("message", "Username must be at least 3 characters long");
                out.print(gson.toJson(result));
                return;
            }

            // Validate password length
            if (password.length() < 6) {
                result.put("success", false);
                result.put("message", "Password must be at least 6 characters long");
                out.print(gson.toJson(result));
                return;
            }

            // Validate role
            if (!role.equals("admin") && !role.equals("lecturer") && !role.equals("student")) {
                result.put("success", false);
                result.put("message", "Invalid role selected");
                out.print(gson.toJson(result));
                return;
            }

            // Check if username already exists
            if (userDAO.usernameExists(username)) {
                result.put("success", false);
                result.put("message", "Username already exists. Please choose another.");
                out.print(gson.toJson(result));
                return;
            }

            // Create new user
            User newUser = new User(username, password, role);
            boolean added = userDAO.addUser(newUser);

            if (added) {
                result.put("success", true);
                result.put("message", "Account created successfully! Redirecting to login...");
            } else {
                result.put("success", false);
                result.put("message", "Failed to create account. Please try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "Server error: " + e.getMessage());
        }

        out.print(gson.toJson(result));
        out.flush();
    }
}
