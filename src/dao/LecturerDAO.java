package dao;

import util.DBConnection;
import model.Lecturer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerDAO {
    public boolean addLecturer(String id, String name, String email, String dept) {
        String sql = "INSERT INTO lecturers (id, name, email, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, dept);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Lecturer> getAllLecturers() {
        List<Lecturer> list = new ArrayList<>();
        String sql = "SELECT * FROM lecturers";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Lecturer(rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("department")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateLecturer(String id, String name, String email, String dept) {
        String sql = "UPDATE lecturers SET name=?, email=?, department=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, dept);
            pstmt.setString(4, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteLecturer(String id) {
        String sql = "DELETE FROM lecturers WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}