package com.frontend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController {
    @FXML
    private TextField firstNameField, lastNameField, emailField, contactField, bioField;

    @FXML
    private PasswordField currPassField, newPassField;

    public void cancelFields(ActionEvent event) {
        firstNameField.setText(null);
        lastNameField.setText(null);
        emailField.setText(null);
        contactField.setText(null);
        bioField.setText(null);
        currPassField.setText(null);
        newPassField.setText(null);
    }

    public void saveChanges(ActionEvent event) {
        // Database changes here
    }

}
