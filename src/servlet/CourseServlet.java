package servlet;

import dao.CourseDAO;
import model.Course;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import com.google.gson.Gson;

public class CourseServlet extends HttpServlet {
    private CourseDAO courseDAO = new CourseDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        List<Course> courses = courseDAO.getAllCourses();
        out.print(gson.toJson(courses));
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
            out.print(gson.toJson(new Response(false, "Access denied. Only admin can create courses.")));
            return;
        }

        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            System.out.println("[CourseServlet] Received JSON: " + sb.toString());
            
            Course course = gson.fromJson(sb.toString(), Course.class);
            System.out.println("[CourseServlet] Parsed Course: id=" + course.getCourseId() + ", name=" + course.getCourseName());
            
            boolean success = courseDAO.addCourse(
                course.getCourseId(), 
                course.getCourseName(), 
                course.getCredits(), 
                course.getLecturerId()
            );
            System.out.println("[CourseServlet] Insert result: " + success);

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new Response(success, success ? "Course added successfully" : "Failed to add course")));
        } catch (Exception e) {
            System.err.println("[CourseServlet] Error: " + e.getMessage());
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
            out.print(gson.toJson(new Response(false, "Access denied. Only admin can update courses.")));
            return;
        }

        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            System.out.println("[CourseServlet] PUT - Received JSON: " + sb.toString());
            
            Course course = gson.fromJson(sb.toString(), Course.class);
            System.out.println("[CourseServlet] PUT - Parsed Course: id=" + course.getCourseId() + ", name=" + course.getCourseName());
            
            boolean success = courseDAO.updateCourse(
                course.getCourseId(), 
                course.getCourseName(), 
                course.getCredits(), 
                course.getLecturerId()
            );
            System.out.println("[CourseServlet] PUT - Update result: " + success);

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new Response(success, success ? "Course updated successfully" : "Failed to update course")));
        } catch (Exception e) {
            System.err.println("[CourseServlet] PUT Error: " + e.getMessage());
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
            out.print(gson.toJson(new Response(false, "Access denied. Only admin can delete courses.")));
            return;
        }

        String courseId = request.getParameter("id");
        boolean success = courseDAO.deleteCourse(courseId);

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Course deleted successfully" : "Failed to delete course")));
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
