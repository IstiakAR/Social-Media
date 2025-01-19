
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController extends Application {
  private static Stage primaryStage;

  @Override
  public void start(Stage stage) throws Exception {
    primaryStage = stage;
    primaryStage.setTitle("Social Media");
    gotoLoginPage();
    primaryStage.show();
  }

  public static void gotoLoginPage() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/loginPage.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }

  public static void gotoHomepage() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/homePage.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }

  public static void gotoSignup() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/signupPage.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }
  public static void gotoProfile() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/profilePage.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }

  public static void gotoMyPost() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/myPost.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }
}
