
package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginScreen extends Application {
  public void start(Stage stage) {
    Label label = new Label("Hello, JavaFX!");
    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(label);

    Scene scene = new Scene(stackPane, 400, 200);
    stage.setScene(scene);
    stage.setTitle("JavaFX Test");
    stage.setFullScreen(true);
    stage.show();
  }
}
