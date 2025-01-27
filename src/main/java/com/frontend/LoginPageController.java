package com.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {
    @FXML
    private Label forgotPasswordLbl, errorLbl1, errorLbl2;
    @FXML
    private Button createBtn, loginBtn;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void forgotPassword(MouseEvent event) {
        System.out.println("Forgot Password");
    }
    
    @FXML
    private void verifyAccount() {
        // database and backend here
    }

    private String username = usernameField.getText();
    private String password = passwordField.getText();

    public void login(ActionEvent event) throws IOException {
        if(!username.isEmpty() && !password.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.changeUsername(username);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else {
            if(username.isEmpty())  errorLbl1.setVisible(true);
            if(password.isEmpty())  errorLbl2.setVisible(true);
        }
    }

    @FXML
    private void createAccount() {
        // database and backend here
    }
}
