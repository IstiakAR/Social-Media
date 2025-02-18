package view;

import main.*;
import model.*;
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

  @FXML
  public void initialize() {
    errorMessage.setVisible(false);
    Platform.runLater(() -> rootPane.requestFocus());
  }
  @FXML
  public void handleLogin(ActionEvent event) {
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
      User user = new User(username, password, fullName, passwordClue);
      int userID = user.getUserID();
      LoginController.setUserID(userID);
      DatabaseInsert.insertUser(user);
      MainStorage.getUsersSMap().put(username, user);
      MainStorage.getUsersIMap().put(userID, user);
      try {
        MainController.gotoHomepage();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}