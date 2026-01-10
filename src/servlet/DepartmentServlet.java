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

        BufferedReader reader = request.getReader();
        Department dept = gson.fromJson(reader, Department.class);
        
        boolean success = departmentDAO.addDepartment(dept.getId(), dept.getName(), dept.getHod(), dept.getStaffCount());

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Department added successfully" : "Failed to add department")));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        BufferedReader reader = request.getReader();
        Department dept = gson.fromJson(reader, Department.class);
        
        boolean success = departmentDAO.updateDepartment(dept.getId(), dept.getName(), dept.getHod(), dept.getStaffCount());

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Department updated successfully" : "Failed to update department")));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

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
