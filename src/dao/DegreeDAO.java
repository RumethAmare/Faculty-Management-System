package dao;

import util.DBConnection;
import model.Degree;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DegreeDAO {
    public boolean addDegree(String id, String name, String deptId) {
        String sql = "INSERT INTO degrees (degree_id, degree_name, dept_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, deptId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Degree> getAllDegrees() {
        List<Degree> list = new ArrayList<>();
        String sql = "SELECT * FROM degrees";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Degree(rs.getString("degree_id"), rs.getString("degree_name"), rs.getString("dept_id")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateDegree(String id, String name, String deptId) {
        String sql = "UPDATE degrees SET degree_name=?, dept_id=? WHERE degree_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, deptId);
            pstmt.setString(3, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteDegree(String id) {
        String sql = "DELETE FROM degrees WHERE degree_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}