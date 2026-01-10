package util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class TestDBConnection {
    public static void main(String[] args) {
        System.out.println("Testing Database Connection...");
        System.out.println("=====================================");
        
        Connection conn = DBConnection.getConnection();
        
        if (conn != null) {
            try {
                System.out.println("✓ Connection successful!");
                
                DatabaseMetaData metaData = conn.getMetaData();
                System.out.println("\nDatabase Information:");
                System.out.println("- Database Product: " + metaData.getDatabaseProductName());
                System.out.println("- Database Version: " + metaData.getDatabaseProductVersion());
                System.out.println("- Driver Name: " + metaData.getDriverName());
                System.out.println("- Driver Version: " + metaData.getDriverVersion());
                System.out.println("- URL: " + metaData.getURL());
                System.out.println("- Username: " + metaData.getUserName());
                
                // Check if database exists and list tables
                System.out.println("\nTables in faculty_db:");
                ResultSet tables = metaData.getTables("faculty_db", null, "%", new String[]{"TABLE"});
                boolean hasTable = false;
                while (tables.next()) {
                    hasTable = true;
                    System.out.println("  - " + tables.getString("TABLE_NAME"));
                }
                
                if (!hasTable) {
                    System.out.println("  (No tables found - database may be empty)");
                }
                
                conn.close();
                System.out.println("\n✓ Connection closed successfully");
                
            } catch (Exception e) {
                System.err.println("✗ Error while testing connection:");
                e.printStackTrace();
            }
        } else {
            System.err.println("✗ Connection failed!");
            System.err.println("\nPossible issues:");
            System.err.println("1. MySQL server is not running (Start XAMPP MySQL service)");
            System.err.println("2. Database 'faculty_db' does not exist");
            System.err.println("3. Username/password is incorrect");
            System.err.println("4. MySQL JDBC driver is not in classpath");
        }
        
        System.out.println("=====================================");
    }
}
