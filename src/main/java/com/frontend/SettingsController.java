package com.frontend;

import database.JavaDatabase;
import database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController {
    @FXML
    private TextField firstNameField, lastNameField, emailField, contactField, bioField;

    @FXML
    private PasswordField currPassField, newPassField;

    @FXML
    private Label profileName;

    private String username;

    public void setUsername(String username) {
        this.username = username;
        profileName.setText(username);
        loadUserData();
    }

    private void loadUserData() {
        User user = JavaDatabase.getUserInfo(username);

        if (user != null) {
            firstNameField.setText(user.getFirstName() != null ? user.getFirstName() : "");
            lastNameField.setText(user.getLastName() != null ? user.getLastName() : "");
            emailField.setText(user.getEmail() != null ? user.getEmail() : "");
            contactField.setText(user.getContact() != null ? user.getContact() : "");
            bioField.setText(user.getBio() != null ? user.getBio() : "");
        } else {
            System.out.println("User not found in the database.");
        }
    }

    @FXML
    public void saveChanges(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String contact = contactField.getText();
        String bio = bioField.getText();
        String currentPassword = currPassField.getText();
        String newPassword = newPassField.getText();

        // Validate contact number contains only digits (if necessary)
        if (!contact.matches("\\d*")) {
            showAlert("Error", "Contact number must contain only digits.", Alert.AlertType.ERROR);
            return;
        }

        // Check required fields
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            showAlert("Error", "First Name, Last Name, and Email are required fields.", Alert.AlertType.ERROR);
            return;
        }

        // Save to database, passing contact as a string
        boolean isUpdated = JavaDatabase.updateUserInfo(username, firstName, lastName, email, contact, bio);

        if (isUpdated) {
            showAlert("Success", "Your information has been updated successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update your information. Please try again.", Alert.AlertType.ERROR);
        }

        // Validate that the new password is not empty
        if (newPassword.isEmpty()) {
            showAlert("Error", "New password cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        // Call the changePassword method to verify the current password and update it
        boolean isPasswordChanged = changePassword(currentPassword, newPassword);

        if (isPasswordChanged) {
            showAlert("Success", "Your password has been updated successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Current password is incorrect. Please try again.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    private boolean changePassword(String currentPassword, String newPassword) {
        // Fetch the current password from the database for the logged-in user
        String storedPassword = JavaDatabase.getUserPassword(username);

        // Check if the current password entered by the user matches the stored password
        if (storedPassword != null && storedPassword.equals(currentPassword)) {
            // If the password is correct, update it with the new password
            return JavaDatabase.updateUserPassword(username, newPassword);
        }
        return false;
    }

    public void cancelFields(ActionEvent event) {
        firstNameField.setText(null);
        lastNameField.setText(null);
        emailField.setText(null);
        contactField.setText(null);
        bioField.setText(null);
        currPassField.setText(null);
        newPassField.setText(null);
    }
}
