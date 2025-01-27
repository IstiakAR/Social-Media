package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import controller.LoginController;

public class LoginView {

    @FXML
    private VBox loginContainer;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private TextField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Label errorMessage;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (LoginController.validateCredentials(username, password)) {
            // Navigate to messenger interface
            LoginController.gotoMessenger();
        } else {
            errorMessage.setText("Invalid username or password.");
        }
    }
}