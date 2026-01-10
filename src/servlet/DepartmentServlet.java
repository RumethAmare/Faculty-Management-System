package servlet;

import dao.DepartmentDAO;
import model.Department;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import com.google.gson.Gson;

public class DepartmentServlet extends HttpServlet {
    private DepartmentDAO departmentDAO = new DepartmentDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        List<Department> departments = departmentDAO.getAllDepartments();
        out.print(gson.toJson(departments));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Check authorization - only admin can create
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new Response(false, "Access denied. Only admin can create departments.")));
            return;
        }

        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            System.out.println("Received JSON: " + sb.toString());
            
            Department dept = gson.fromJson(sb.toString(), Department.class);
            System.out.println("Parsed Department: id=" + dept.getId() + ", name=" + dept.getName());
            
            boolean success = departmentDAO.addDepartment(dept.getId(), dept.getName(), dept.getHod(), dept.getStaffCount());
            System.out.println("Insert result: " + success);

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new Response(success, success ? "Department added successfully" : "Failed to add department")));
        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new Response(false, "Error: " + e.getMessage())));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Check authorization - only admin can update
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new Response(false, "Access denied. Only admin can update departments.")));
            return;
        }

        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            System.out.println("[DepartmentServlet] PUT - Received JSON: " + sb.toString());
            
            Department dept = gson.fromJson(sb.toString(), Department.class);
            System.out.println("[DepartmentServlet] PUT - Parsed Department: id=" + dept.getId() + ", name=" + dept.getName());
            
            boolean success = departmentDAO.updateDepartment(dept.getId(), dept.getName(), dept.getHod(), dept.getStaffCount());
            System.out.println("[DepartmentServlet] PUT - Update result: " + success);

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new Response(success, success ? "Department updated successfully" : "Failed to update department")));
        } catch (Exception e) {
            System.err.println("[DepartmentServlet] PUT Error: " + e.getMessage());
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new Response(false, "Error: " + e.getMessage())));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Check authorization - only admin can delete
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new Response(false, "Access denied. Only admin can delete departments.")));
            return;
        }

        String deptId = request.getParameter("id");
        boolean success = departmentDAO.deleteDepartment(deptId);

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Department deleted successfully" : "Failed to delete department")));
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    static class Response {
        boolean success;
        String message;
        Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
