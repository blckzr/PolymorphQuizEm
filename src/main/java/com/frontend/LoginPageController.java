package com.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginPageController {
    @FXML
    private Label forgotPasswordLbl;
    @FXML
    private Button createBtn, loginBtn;
    @FXML
    private TextField loginAccount, loginPassword;

    @FXML
    private void forgotPassword(MouseEvent event) {
        System.out.println("Forgot Password");
    }
    
    @FXML
    private void verifyAccount() {
        // database and backend here
    }

    @FXML
    private void createAccount() {

    }
}
