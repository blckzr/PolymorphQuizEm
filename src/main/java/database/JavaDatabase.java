package database;

import com.frontend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

public class JavaDatabase {
    private static final String db_url = "jdbc:mysql://127.00.1:3006/polymorphquizem_db";
    private static final String db_username = "root";
    private static final String db_password = "password";

    public static void changeScene(ActionEvent event, String fxmlFile){
        Parent root = null;

        try{
            FXMLLoader loader = new FXMLLoader(JavaDatabase.class.getResource(fxmlFile));
            root = loader.load();
            SignupController signupController = loader.getController();
            // set information
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void signUpUser (ActionEvent event, String fullname, String username, String email, String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection(db_url, db_username, db_password);
            psCheckUserExists = connection.prepareStatement("SELECT * FROM user WHERE username == ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("Username already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username is taken");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO user (fullname, username, email, password) VALUES (?, ?, ?, ?)");
                psInsert.setString(1, fullname);
                psInsert.setString(2, username);
                psInsert.setString(3, email);
                psInsert.setString(4, password);
                psInsert.executeUpdate();


            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
