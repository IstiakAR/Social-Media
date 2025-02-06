
package view;

import database.DatabaseUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.*;
import model.User;

public class SettingsController {
    User user;
    @FXML 
    private TextField newNameField;
    @FXML 
    private PasswordField currentPasswordField;
    @FXML 
    private PasswordField newPasswordField;
    @FXML 
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorLabel;

    public void initialize() {
        user = MainStorage.getUsersIMap().get(LoginController.getUserID());
        errorLabel.setText("");
        errorLabel.setVisible(false);
        errorLabel.setStyle("-fx-text-fill: red;");
    }

    @FXML
    private void handleSaveName() { 
        errorLabel.setVisible(false);
        String newName = newNameField.getText();
        if (newName != null && !newName.trim().isEmpty()) {
            DatabaseUpdate.updateName(user.getUserID(), newName);
            user.setName(newName);
        }
    }

    @FXML
    public void handleHome() {
        System.out.println("Home clicked");
        try {
            MainController.gotoHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleSavePassword() {
        errorLabel.setVisible(false);
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        if (currentPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank()) {
            errorLabel.setVisible(true);
            errorLabel.setText("All fields must be filled.");
            return;
        }
        
        if (user.getPassword().equals(currentPassword)) {
            if (newPassword.equals(confirmPassword)) {
                try {
                    DatabaseUpdate.updatePassword(LoginController.getUserID(), newPassword);
                    user.setPassword(newPassword);
                } catch (Exception e) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Failed to update the password. Please try again.");
                }
            } else {
                errorLabel.setVisible(true);
                errorLabel.setText("New passwords do not match.");
            }
        } else {
            errorLabel.setVisible(true);
            errorLabel.setText("Current password is incorrect.");
        }
        
    }
}