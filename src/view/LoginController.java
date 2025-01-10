
package view;

import main.*;
import javafx.event.ActionEvent;

public class LoginController {

  public void gotoHomepage(ActionEvent event) {
    try {
      MainController.gotoHomepage(); // Navigate to the home page
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void handleForgotPassword(ActionEvent event) {
    System.out.println("Forgot Password clicked!");
    // Logic for forgot password goes here
  }

  public void handleSignUp(ActionEvent event) {
    System.out.println("Sign Up clicked!");
    // Logic for sign-up goes here
  }
}
