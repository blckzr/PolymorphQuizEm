package com.frontend;

import database.JavaDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordController {
    @FXML
    private TextField emailField;
    @FXML
    private Label messageLabel;
    @FXML
    private Button resetButton;

    @FXML
    private void login(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleResetPassword() {
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            messageLabel.setText("Please enter your email.");
            return;
        }

        boolean emailExists = JavaDatabase.checkEmailExists(email);
        if (emailExists) {
            boolean resetSuccessful = JavaDatabase.resetPassword(email);
            if (resetSuccessful) {
                messageLabel.setStyle("-fx-text-fill: green;");
                messageLabel.setText("A reset link has been sent to your email.");
            } else {
                messageLabel.setText("Error resetting password. Try again.");
            }
        } else {
            messageLabel.setText("Email not found.");
        }
    }
}
