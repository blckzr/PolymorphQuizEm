package database;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import javafx.scene.control.Alert;

public class JavaDatabase {
    private static final String db_url = "jdbc:mysql://127.0.0.1:3306/polymorphquizem_db";
    private static final String db_username = "root";
    private static final String db_password = "password";

    // Database Connection Method (Previously Missing)
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(db_url, db_username, db_password);
    }

    // Signup Function
    public static void signUpUser(String username, String email, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = connect();

            psCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username is taken");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO user (username, email_account, password) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, email);
                psInsert.setString(3, hashPassword(password)); // Hashing password before storing
                psInsert.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User successfully signed up!");
                alert.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(connection, psInsert, psCheckUserExists, resultSet);
        }
    }

    // Login Function
    public static boolean logInUser(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connect();

            preparedStatement = connection.prepareStatement("SELECT password FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User not found!");
                alert.show();
                return false;
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(hashPassword(password))) { // Compare hashed passwords
                        return true;
                    } else {
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

    // Check if Email Exists
    public static boolean checkEmailExists(String email) {
        String query = "SELECT * FROM user WHERE email_account = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Forgot Password - Reset Function
    public static boolean resetPassword(String email) {
        String tempPassword = generateTemporaryPassword();
        String query = "UPDATE user SET password = ? WHERE email_account = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hashPassword(tempPassword));
            stmt.setString(2, email);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                sendResetEmail(email, tempPassword);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Generate a Temporary Password
    public static String generateTemporaryPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&!";
        SecureRandom random = new SecureRandom();
        StringBuilder tempPassword = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            tempPassword.append(characters.charAt(random.nextInt(characters.length())));
        }
        return tempPassword.toString();
    }

    // Hash Password (Previously Missing)
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Send Reset Email (Previously Missing)
    public static void sendResetEmail(String email, String tempPassword) {
        final String senderEmail = "yourEmail@gmail.com";
        final String senderPassword = "yourEmailPassword";  // Use an App Password

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = jakarta.mail.Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Password Reset");
            message.setText("Your new temporary password is: " + tempPassword);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // ✅ Helper Method to Close Resources
    private static void closeResources(Connection connection, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void closeResources(Connection connection, Statement stmt1, Statement stmt2, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt1 != null) stmt1.close();
            if (stmt2 != null) stmt2.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
