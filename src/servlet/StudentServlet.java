package servlet;

import dao.StudentDAO;
import model.Student;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import com.google.gson.Gson;

public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        List<Student> students = studentDAO.getAllStudents();
        out.print(gson.toJson(students));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        BufferedReader reader = request.getReader();
        Student student = gson.fromJson(reader, Student.class);
        
        boolean success = studentDAO.addStudent(student.getId(), student.getName(), student.getEmail(), student.getDegree());

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Student added successfully" : "Failed to add student")));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        BufferedReader reader = request.getReader();
        Student student = gson.fromJson(reader, Student.class);
        
        boolean success = studentDAO.updateStudent(student.getId(), student.getName(), student.getEmail(), student.getDegree());

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Student updated successfully" : "Failed to update student")));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String studentId = request.getParameter("id");
        boolean success = studentDAO.deleteStudent(studentId);

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Student deleted successfully" : "Failed to delete student")));
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
