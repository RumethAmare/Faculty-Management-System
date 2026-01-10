package dao;

import util.DBConnection;
import model.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    public boolean addDepartment(String id, String name, String hod, int count) {
        String sql = "INSERT INTO departments (dept_id, dept_name, hod_name, staff_count) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("Inserting: " + id + ", " + name + ", " + hod + ", " + count);
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, hod);
            pstmt.setInt(4, count);
            int result = pstmt.executeUpdate();
            System.out.println("Rows affected: " + result);
            return result > 0;
        } catch (SQLException e) { 
            System.err.println("SQL Error in addDepartment: " + e.getMessage());
            e.printStackTrace(); 
            return false; 
        }
    }

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Department(rs.getString("dept_id"), rs.getString("dept_name"), rs.getString("hod_name"), rs.getInt("staff_count")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateDepartment(String id, String name, String hod, int count) {
        String sql = "UPDATE departments SET dept_name=?, hod_name=?, staff_count=? WHERE dept_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, hod);
            pstmt.setInt(3, count);
            pstmt.setString(4, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteDepartment(String id) {
        String sql = "DELETE FROM departments WHERE dept_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}