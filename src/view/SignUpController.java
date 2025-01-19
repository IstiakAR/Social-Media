package view;

import main.*;
import model.*;
import database.Database;
import database.DatabaseInsert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
  private AnchorPane rootPane;
  public static String name;
  @FXML
  public void initialize() {
    errorMessage.setVisible(false);
    Platform.runLater(() -> rootPane.requestFocus());
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
    name = username;
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
    else if(MainStorage.getUsersSMap().containsKey(username)){
      errorMessage.setText("Username already exists.");
      errorMessage.setVisible(true);
    } 
    else {
      DatabaseInsert.insertUser(new User(username, password, fullName, passwordClue));
      try {
        MainController.gotoHomepage();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}