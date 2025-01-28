package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    public static void main(String[] args) {
        // MySQL Database connection details
        String url = "jdbc:mysql://127.0.0.1:3306/polymorphquizem_db";  // Replace with your database URL and name
        String username = "root";  // Replace with your MySQL username
        String password = "password";  // Replace with your MySQL password

        // Attempt to connect to the database
        try {
            // Load and register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // If the connection is successful
            System.out.println("Connection successful!");

            // Close the connection
            connection.close();
        } catch (SQLException e) {
            // Handle any SQL errors (e.g., incorrect credentials, database not running)
            System.err.println("Connection failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            // Handle error for missing JDBC driver
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }
    }
}
