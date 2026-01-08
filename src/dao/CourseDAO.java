package dao;

import util.DBConnection;
import model.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public boolean addCourse(String id, String name, int credits, String lecId) {
        String sql = "INSERT INTO courses (course_id, course_name, credits, lecturer_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, credits);
            pstmt.setString(4, lecId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Course(rs.getString("course_id"), rs.getString("course_name"), rs.getInt("credits"), rs.getString("lecturer_id")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateCourse(String id, String name, int credits, String lecId) {
        String sql = "UPDATE courses SET course_name=?, credits=?, lecturer_id=? WHERE course_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, credits);
            pstmt.setString(3, lecId);
            pstmt.setString(4, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteCourse(String id) {
        String sql = "DELETE FROM courses WHERE course_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}