package controller;

import database.DatabaseInsert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.MainController;
import model.User;

public class SignUpController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField nameField;

    @FXML
    public void handleSignUp(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();

        if (validateInput(username, password, name)) {
            User newUser = new User(username, password, name);
            DatabaseInsert.insertUser(newUser);
            try {
                MainController.gotoLogin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInput(String username, String password, String name) {
        return !username.isEmpty() && !password.isEmpty() && !name.isEmpty();
    }
}