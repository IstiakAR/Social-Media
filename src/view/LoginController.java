
package view;

import main.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
  public void initialize() {
    errorMessage.setVisible(false);
  }

  public void handleForgotPassword(MouseEvent event) {
    System.out.println("Forgot Password clicked!");
    String username = loginUsername.getText();
    if (Main.users.containsKey(username)) {
      passwordClue.setText("Your Clue: " + Main.users.get(username).clue);
    } else {
      passwordClue.setText("Username not found.");
    }
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
