package database;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class JavaDatabase {
    private static final String db_url = "jdbc:mysql://127.0.0.1:3306/polymorphquizem_db";
    private static final String db_username = "root";
    private static final String db_password = "password";

    public static void changeScene(ActionEvent event, String fxmlFile, boolean bool) {
        Parent root = null;

        try {
            if (bool) {
                FXMLLoader loader = new FXMLLoader(JavaDatabase.class.getResource(fxmlFile));
                root = loader.load();
            } else {
                root = FXMLLoader.load(JavaDatabase.class.getResource(fxmlFile));
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 679, 1336));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void signUpUser(ActionEvent event, String fullname, String username, String email, String password) {
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
                psInsert = connection.prepareStatement("INSERT INTO user (fullname, username, email_account, password) VALUES (?, ?, ?, ?)");
                psInsert.setString(1, fullname);
                psInsert.setString(2, username);
                psInsert.setString(3, email);
                psInsert.setString(4, password);
                psInsert.executeUpdate();

                // Inform user about successful sign-up
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User successfully signed up!");
                alert.show();

                // Optionally, navigate to another scene (Login page)
                changeScene(event, "LoginPage.fxml", true);
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
}
