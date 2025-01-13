
package view;

import main.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {
  @FXML
  private TextField loginUsername;
  @FXML
  private PasswordField loginPassword;

  public void handleForgotPassword(ActionEvent event) {
    System.out.println("Forgot Password clicked!");
    // Logic for forgot password goes here
  }

  public void handleSignUp(ActionEvent event) {
    System.out.println("Sign Up clicked!");
    // Logic for sign-up goes here
  }

  public void handleLogin(ActionEvent event) {
    String username = loginUsername.getText();
    String password = loginPassword.getText();
    System.out.println("Login clicked!");
    if ((Main.users.containsKey(username)){
      if(Main.users.get(username).password.equals(password)) {
	System.out.println("Login Matched!");
    	try {
          MainController.gotoHomepage();
    	} catch (Exception e) {
	  e.printStackTrace();
      	}
      }
      else{
	System.out.println("Login Failed!");
      }
    }
  }
}
