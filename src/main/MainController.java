package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Post;
import view.AddPostController;
import view.PostController;
import view.SearchController;
import view.AddProfilePictureController;

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
  public static void gotoSearch(String search, boolean searchPosts) throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/searchPage.fxml"));
    Parent root = loader.load();

    SearchController controller = loader.getController();
    controller.search(search);

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }

  public static void gotoProfile() throws Exception {
    System.out.println("go to profile called");
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

  public static void gotoSaved() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/savedPost.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }
  public static void gotoFriends() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/friendsPage.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }
  public static void gotoFriendRequest() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/friendRequest.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }
  public static void gotoAllFriends() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/allFriendsPage.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }
  public static void gotoAbout() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/aboutPage.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }
  public static void gotoMessenger() throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/messengerPage.fxml"));
    System.out.println("HII");
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }
  public static void gotoPost(Post post, int postID) throws Exception {
    System.out.println("HII");
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/postPage.fxml"));
    Parent root = loader.load();
    PostController controller = loader.getController();
    controller.displayPost(post, postID);

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
  }
  @SuppressWarnings("unused")
  public static void showAddPostDialog(Runnable onCloseCallback) throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/addPostDialog.fxml"));
    Parent root = loader.load();
    AddPostController controller = loader.getController();
    controller.setOnCloseCallback(v -> onCloseCallback.run());
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Add New Post");
    dialogStage.initModality(Modality.WINDOW_MODAL);
    dialogStage.initOwner(primaryStage);
    Scene scene = new Scene(root);
    dialogStage.setScene(scene);
    dialogStage.showAndWait();
  }

  @SuppressWarnings("unused")
  public static void showAddProfilePictureDialog(Runnable onCloseCallback) throws Exception {
    FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/addProfilePicturedialog.fxml"));
    Parent root = loader.load();
    AddProfilePictureController controller = loader.getController();
    controller.setOnCloseCallback(v -> onCloseCallback.run());
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Update Profile Picture");
    dialogStage.initModality(Modality.WINDOW_MODAL);
    dialogStage.initOwner(primaryStage);
    Scene scene = new Scene(root);
    dialogStage.setScene(scene);
    dialogStage.showAndWait();
  }
}
