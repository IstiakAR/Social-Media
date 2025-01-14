
package view;

import main.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

public class LoginController {
  @FXML
  private TextField loginUsername;
  @FXML
  private PasswordField loginPassword;
  @FXML
  private Label errorMessage;
  @FXML
  private Label passwordClue;
  @FXML
  private AnchorPane rootPane;

  @FXML
  public void initialize() {
    errorMessage.setVisible(false);
    passwordClue.setVisible(false);
    Platform.runLater(() -> rootPane.requestFocus());
  }

  @FXML
  public void handleForgotPassword(ActionEvent event) {
    errorMessage.setVisible(false);
    System.out.println("Forgot Password clicked!");
    String username = loginUsername.getText();
    if(username.isEmpty()){
      passwordClue.setText("Please enter username.");
      passwordClue.setVisible(true);
      return;
    }
    else if (Main.users.containsKey(username)) {
      passwordClue.setVisible(true);
      passwordClue.setText("Your Clue: " + Main.users.get(username).clue);
    } else {
      passwordClue.setText("Username not found.");
    }
    passwordClue.setVisible(true);
  }

  public void handleSignUp(ActionEvent event) {
    System.out.println("Sign Up in sign in page clicked!");
    try {
      MainController.gotoSignup();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void handleLogin(ActionEvent event) {
    passwordClue.setVisible(false);
    String username = loginUsername.getText();
    String password = loginPassword.getText();
    System.out.println("Login clicked!");
    if (Main.users.containsKey(username)) {
      if (Main.users.get(username).password.equals(password)) {
        System.out.println("Login Matched!");
        errorMessage.setVisible(false);
        try {
          MainController.gotoHomepage();
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Login Failed!");
        errorMessage.setText("Incorrect password. Please try again.");
        errorMessage.setVisible(true);
      }
    } else {
      errorMessage.setText("Username not found. Please try again.");
      errorMessage.setVisible(true);
    }
  }
}
