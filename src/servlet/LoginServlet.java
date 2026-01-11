package servlet;

import dao.UserDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        try {
            // Read request body
            BufferedReader reader = request.getReader();
            JsonObject loginData = gson.fromJson(reader, JsonObject.class);
            
            String username = loginData.get("username").getAsString();
            String password = loginData.get("password").getAsString();
            String expectedRole = loginData.get("role").getAsString();
            
            // Validate login
            String actualRole = userDAO.validateLogin(username, password);
            
            PrintWriter out = response.getWriter();
            
            if (actualRole != null && actualRole.equals(expectedRole)) {
                // Login successful - Create session
                HttpSession session = request.getSession(true);
                session.setAttribute("username", username);
                session.setAttribute("role", actualRole);
                session.setMaxInactiveInterval(30 * 60); // 30 minutes
                
                JsonObject responseData = new JsonObject();
                responseData.addProperty("success", true);
                responseData.addProperty("role", actualRole);
                responseData.addProperty("username", username);
                responseData.addProperty("sessionId", session.getId());
                responseData.addProperty("message", "Login successful");
                out.print(gson.toJson(responseData));
            } else {
                // Login failed
                JsonObject responseData = new JsonObject();
                responseData.addProperty("success", false);
                responseData.addProperty("message", "Invalid username, password, or role");
                out.print(gson.toJson(responseData));
            }
        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            JsonObject responseData = new JsonObject();
            responseData.addProperty("success", false);
            responseData.addProperty("message", "Server error: " + e.getMessage());
            out.print(gson.toJson(responseData));
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
