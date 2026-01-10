package servlet;

import dao.LecturerDAO;
import model.Lecturer;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import com.google.gson.Gson;

public class LecturerServlet extends HttpServlet {
    private LecturerDAO lecturerDAO = new LecturerDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        List<Lecturer> lecturers = lecturerDAO.getAllLecturers();
        out.print(gson.toJson(lecturers));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        BufferedReader reader = request.getReader();
        Lecturer lecturer = gson.fromJson(reader, Lecturer.class);
        
        boolean success = lecturerDAO.addLecturer(lecturer.getId(), lecturer.getName(), lecturer.getEmail(), lecturer.getDepartment());

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Lecturer added successfully" : "Failed to add lecturer")));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        BufferedReader reader = request.getReader();
        Lecturer lecturer = gson.fromJson(reader, Lecturer.class);
        
        boolean success = lecturerDAO.updateLecturer(lecturer.getId(), lecturer.getName(), lecturer.getEmail(), lecturer.getDepartment());

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Lecturer updated successfully" : "Failed to update lecturer")));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String lecturerId = request.getParameter("id");
        boolean success = lecturerDAO.deleteLecturer(lecturerId);

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Lecturer deleted successfully" : "Failed to delete lecturer")));
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
