package database;

import javafx.scene.control.Alert;
import java.sql.*;

public class JavaDatabase {
    private static final String db_url = "jdbc:mysql://127.0.0.1:3306/polymorphquizem_db";
    private static final String db_username = "root";
    private static final String db_password = "password";

    // Signup Function
    public static void logInUser(String username, String email, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            // Connect to the database
            connection = DriverManager.getConnection(db_url, db_username, db_password);

            // Check if the username already exists
            psCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Username already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username is taken");
                alert.show();
            } else {
                // Insert new user into the database
                psInsert = connection.prepareStatement("INSERT INTO user (username, email_account, password) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, email);
                psInsert.setString(3, password);
                psInsert.executeUpdate();

                // Inform user about successful sign-up
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User successfully signed up!");
                alert.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("An error occurred while signing up. Please try again.");
            alert.show();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (psCheckUserExists != null) psCheckUserExists.close();
                if (psInsert != null) psInsert.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Login Function
    public static boolean logInUser(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Connect to the database
            connection = DriverManager.getConnection(db_url, db_username, db_password);

            // Check if user has record
            preparedStatement = connection.prepareStatement("SELECT password FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");  // Correct column name here
                    if (retrievedPassword.equals(password)) {
                        return true;
                    } else {
                        // Password did not match
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect Password");
                        alert.show();
                        return false;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Settings Function
//    public static void setInfo(String username){
//        Connection connection = null;
//        PreparedStatement psInsert = null;
//        PreparedStatement psCheckUserExists = null;
//        ResultSet resultSet = null;
//
//        try {
//            // Connect to the database
//            connection = DriverManager.getConnection(db_url, db_username, db_password);
//
//            // Check if the username already exists
//            psCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
//            psCheckUserExists.setString(1, username);
//            resultSet = psCheckUserExists.executeQuery();
//
//            if (resultSet.isBeforeFirst()) {
//                psInsert = connection.prepareStatement("UPDATE INTO user (first_name, last_name, password) VALUES (?, ?, ?)");
//                psInsert.setString(1, username);
//                psInsert.setString(2, email);
//                psInsert.setString(3, password);
//                psInsert.executeUpdate();
//            } else {
//
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("An error occurred while signing up. Please try again.");
//            alert.show();
//        } finally {
//            try {
//                if (resultSet != null) resultSet.close();
//                if (psCheckUserExists != null) psCheckUserExists.close();
//                if (psInsert != null) psInsert.close();
//                if (connection != null) connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

