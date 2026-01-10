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

        BufferedReader reader = request.getReader();
        Course course = gson.fromJson(reader, Course.class);
        
        boolean success = courseDAO.addCourse(
            course.getCourseId(), 
            course.getCourseName(), 
            course.getCredits(), 
            course.getLecturerId()
        );

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Course added successfully" : "Failed to add course")));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        BufferedReader reader = request.getReader();
        Course course = gson.fromJson(reader, Course.class);
        
        boolean success = courseDAO.updateCourse(
            course.getCourseId(), 
            course.getCourseName(), 
            course.getCredits(), 
            course.getLecturerId()
        );

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Course updated successfully" : "Failed to update course")));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

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
