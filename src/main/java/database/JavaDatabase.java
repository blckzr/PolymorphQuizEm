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
            closeResources(connection, preparedStatement, resultSet);
        }

        return false;
    }

    // Get User Information
    public static User getUserInfo(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(db_url, db_username, db_password);

            // Fetch user information
            preparedStatement = connection.prepareStatement("SELECT first_name, last_name, email_account, contact_number, bio FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        username,
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email_account"),
                        resultSet.getString("contact_number"),
                        resultSet.getString("bio")
                );
            } else {
                return null; // User not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(connection, preparedStatement, resultSet);
        }
    }

    // Update User Information in Settings
    public static boolean updateUserInfo(String username, String firstName, String lastName, String email, String contact, String bio) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(db_url, db_username, db_password);

            // Update the user record in the database
            String updateQuery = "UPDATE user SET first_name = ?, last_name = ?, email_account = ?, contact_number = ?, bio = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, contact);
            preparedStatement.setString(5, bio);
            preparedStatement.setString(6, username);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("User information updated successfully.");
                return true;
            } else {
                System.out.println("No user found with the given username.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, preparedStatement);
        }
    }

    // Get User's Password
    public static String getUserPassword(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(db_url, db_username, db_password);

            // Query to fetch the user's current password
            String query = "SELECT password FROM user WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("password");  // Return the password if found
            } else {
                return null;  // Return null if no user is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(connection, preparedStatement, resultSet);
        }
    }

    // Check if successfully changed user's password
    public static boolean updateUserPassword(String username, String newPassword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(db_url, db_username, db_password);

            // Update the user's password in the database
            String updateQuery = "UPDATE user SET password = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, newPassword);  // Set the new password
            preparedStatement.setString(2, username);    // Set the username for which the password will be updated

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully.");
                return true;
            } else {
                System.out.println("No user found with the given username.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, preparedStatement);
        }
    }

    // close all statement method
    private static void closeResources(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // close all statement method
    private static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveQuestionCategoryAndAnswersToDatabase(String question, String category,
                                                                   String[] answers, int correctIndex){
        try{
            // establish a database connection
            Connection connection = DriverManager.getConnection(
                    db_url, db_username, db_password
            );

            // insert category if it's new, otherwise retrieve it from the database
            Category categoryObj = getCategory(category);
            if(categoryObj == null){
                // insert new category to database
                categoryObj = insertCategory(category);
            }

            // insert question to database
            Question questionObj = insertQuestion(categoryObj, question);

            // insert answers to database
            return insertAnswers(questionObj, answers, correctIndex);
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}