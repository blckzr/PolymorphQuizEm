package com.frontend;

import database.JavaDatabase;
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
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void forgotPassword(MouseEvent event) {
        // backend here
    }

    @FXML
    public void verifyAccount(ActionEvent event) {
        // Logic for verifying account (login logic here)
    }


    /*
        To be added with database and backend.

        if(email != valid || password != valid) {
            emailField.setPromptText("Email or password is invalid.");
            emailField.setStyle("-fx-border-color: #5c155e; -fx-prompt-text-fill: #5c155e; -fx-font-style: italic; -fx-border-width: 3px;");

            passwordField.setPromptText("Email or password is invalid.");
            passwordField.setStyle("-fx-border-color: #5c155e; -fx-prompt-text-fill: #5c155e; -fx-font-style: italic; -fx-border-width: 3px;");
         }
         */

    public void login(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(!username.isEmpty() && !password.isEmpty()) {
            // Call the method to sign up the user
            JavaDatabase.logInUser(event, username, password);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SidebarTemplate.fxml"));
            root = loader.load();

            SidebarController sidebarController = loader.getController();
            sidebarController.setUsername(username);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else {
            if(username.isEmpty())  {
                usernameField.setPromptText("Field required!");
                usernameField.setStyle("-fx-border-color: #5c155e; -fx-prompt-text-fill: #5c155e; -fx-font-style: italic; -fx-border-width: 3px;");
            }
            if(password.isEmpty())  {
                passwordField.setPromptText("Field required!");
                passwordField.setStyle("-fx-border-color: #5c155e; -fx-prompt-text-fill: #5c155e; -fx-font-style: italic; -fx-border-width: 3px;");
            }
        }
    }

    public void createAccount(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        addTextChangeListener(usernameField);
        addTextChangeListener(passwordField);
    }

    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setStyle("-fx-border-color: #5c155e; -fx-border-width: 1px;");
        });
    }
}
