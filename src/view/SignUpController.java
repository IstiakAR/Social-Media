package view;

import main.*;
import model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

public class SignUpController {
  @FXML
  private TextField signupFullName;
  @FXML
  private TextField signupUsername;
  @FXML
  private PasswordField signupPassword;
  @FXML
  private PasswordField signupRePassword;
  @FXML
  private TextField signupPasswordClue;
  @FXML
  private Label errorMessage;
  @FXML
  public void initialize() {
    errorMessage.setVisible(false);
  }
  @FXML
  public void handleLogin(ActionEvent event) {
    System.out.println("Sign In in sign up page clicked!");
    try {
      MainController.gotoLoginPage();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @FXML
  public void handleSignUp(ActionEvent event) {
    String username = signupUsername.getText();
    String password = signupPassword.getText();
    String fullName = signupFullName.getText();
    String retypePassword = signupRePassword.getText();
    String passwordClue = signupPasswordClue.getText();
    System.out.println("Sign Up clicked!");
    if(username.isEmpty() || password.isEmpty() || fullName.isEmpty() || retypePassword.isEmpty() || passwordClue.isEmpty()) {
      errorMessage.setText("Please fill all fields.");
      errorMessage.setVisible(true);
    } else if (!password.equals(retypePassword)) {
      errorMessage.setText("Password does not match.");
      errorMessage.setVisible(true);
    }
    else if(Main.users.containsKey(username)){
      errorMessage.setText("Username already exists.");
      errorMessage.setVisible(true);
    } else {
      Main.users.put(username, new User(username, password, fullName, passwordClue, Main.users.size()));
      try {
        MainController.gotoHomepage();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}