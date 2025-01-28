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
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {
    @FXML
    private TextField fullnameField, usernameField, emailField;
    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void signup(ActionEvent event) throws IOException {
        String fullname = fullnameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        /*
        To be added with database and backend.

        if(email != valid)
            emailField.setPromptText("Please enter a valid email.");
            emailField.setStyle("-fx-border-color: #5c155e; -fx-prompt-text-fill: #5c155e; -fx-font-style: italic; -fx-border-width: 3px;");

        if(password != valid)
            passwordField.setPromptText("Please enter a valid password.");
            passwordField.setStyle("-fx-border-color: #5c155e; -fx-prompt-text-fill: #5c155e; -fx-font-style: italic; -fx-border-width: 3px;");
         */

        if(!fullname.isEmpty() && !username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            if(fullname.isEmpty()) {
                fullnameField.setPromptText("Field required!");
                fullnameField.setStyle("-fx-border-color: #5c155e; -fx-prompt-text-fill: #5c155e; -fx-font-style: italic; -fx-border-width: 3px;");
            }
            if(username.isEmpty()) {
                usernameField.setPromptText("Field required!");
                usernameField.setStyle("-fx-border-color: #5c155e; -fx-prompt-text-fill: #5c155e; -fx-font-style: italic; -fx-border-width: 3px;");
            }
            if(email.isEmpty()) {
                emailField.setPromptText("Field required!");
                emailField.setStyle("-fx-border-color: #5c155e; -fx-prompt-text-fill: #5c155e; -fx-font-style: italic; -fx-border-width: 3px;");
            }
            if(password.isEmpty()) {
                passwordField.setPromptText("Field required!");
                passwordField.setStyle("-fx-border-color: #5c155e; -fx-prompt-text-fill: #5c155e; -fx-font-style: italic; -fx-border-width: 3px;");
            }
        }
    }

    @FXML
    public void initialize() {
        addTextChangeListener(fullnameField);
        addTextChangeListener(usernameField);
        addTextChangeListener(emailField);
        addTextChangeListener(passwordField);


    }

    private void addTextChangeListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setStyle("-fx-border-color: #5c155e; -fx-border-width: 1px;");
        });
    }
}
