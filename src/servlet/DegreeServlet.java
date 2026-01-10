package servlet;

import dao.DegreeDAO;
import model.Degree;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import com.google.gson.Gson;

public class DegreeServlet extends HttpServlet {
    private DegreeDAO degreeDAO = new DegreeDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        List<Degree> degrees = degreeDAO.getAllDegrees();
        out.print(gson.toJson(degrees));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            System.out.println("[DegreeServlet] Received JSON: " + sb.toString());
            
            Degree degree = gson.fromJson(sb.toString(), Degree.class);
            System.out.println("[DegreeServlet] Parsed Degree: id=" + degree.getId() + ", name=" + degree.getName());
            
            boolean success = degreeDAO.addDegree(degree.getId(), degree.getName(), degree.getDeptId());
            System.out.println("[DegreeServlet] Insert result: " + success);

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(new Response(success, success ? "Degree added successfully" : "Failed to add degree")));
        } catch (Exception e) {
            System.err.println("[DegreeServlet] Error: " + e.getMessage());
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

        BufferedReader reader = request.getReader();
        Degree degree = gson.fromJson(reader, Degree.class);
        
        boolean success = degreeDAO.updateDegree(degree.getId(), degree.getName(), degree.getDeptId());

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Degree updated successfully" : "Failed to update degree")));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String degreeId = request.getParameter("id");
        boolean success = degreeDAO.deleteDegree(degreeId);

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(new Response(success, success ? "Degree deleted successfully" : "Failed to delete degree")));
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
