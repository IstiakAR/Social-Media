package view;

import java.io.ByteArrayInputStream;

import database.DatabaseGetter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.MainStorage;
import model.User;

public class AllFriendController extends FriendBaseController  {
    
    public void displayFriendList() {
        var requests = DatabaseGetter.getAllfriend(LoginController.userID);
        friendListContainer.getChildren().clear();
        friendListContainer.getChildren().clear();

        for (var request : requests) {
            VBox requestBox = createFriendBox(request);
            friendListContainer.getChildren().add(requestBox);
        }
        for (var request : requests) {
            VBox requestBox = createFriendBox(request);
            friendListContainer.getChildren().add(requestBox);
        }
    }
    @SuppressWarnings("unused")
    public VBox createFriendBox(User friend) {
        VBox friendBox = new VBox();
        friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        friendBox.setPrefWidth(400);

        User user = MainStorage.getUsersIMap().get(LoginController.userID);
        Image profileImage = null;
        if (user != null) {
            byte[] profilePicture = user.getProfilePicture();
            
            if (profilePicture != null && profilePicture.length > 0) {
                try {
                    profileImage = new Image(new ByteArrayInputStream(profilePicture));
                } catch (Exception e) {
                    System.out.println("Error loading profile picture: " + e.getMessage());
                    profileImage = null;
                }
            }
        }

        Circle profileImageView = new Circle(25);
        if (profileImage != null) {
            profileImageView.setFill(new ImagePattern(profileImage));
        } else {
            profileImageView.setFill(Color.GRAY);
        }

        profileImageView.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/friendsProfile.fxml"));
                Parent root = loader.load();
        
                FriendsProfileController controller = loader.getController();
                if (controller == null) {
                    System.out.println("Error: Controller is null!");
                    return;
                }
        
                if (friend != null) {
                    controller.setfrienddata(friend);
                    System.out.println("Friend data passed successfully.");
                } else {
                    System.out.println("Warning: Friend object is null!");
                }
        
                Stage stage = (Stage) profileImageView.getScene().getWindow();
                if (stage == null) {
                    System.out.println("Error: Stage is null!");
                    return;
                }
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading FXML: " + e.getMessage());
            }
        });
        
        
        Label friendName = new Label(friend.getName());
        friendName.setStyle("-fx-font-size: 22px; -fx-text-fill: rgb(153, 153, 153);");

        Label friendStatus = new Label(" ");
        friendStatus.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");

        int userId = LoginController.userID; 
        int friendId = friend.getUserID();
        if (DatabaseGetter.isConfirm(friendId, userId)) {
            friendStatus.setText("Friend");
        }

        HBox profileBox = new HBox(profileImageView, friendName);
        profileBox.setSpacing(10);
        profileBox.setStyle("-fx-alignment: CENTER_LEFT;");

        friendBox.getChildren().addAll(profileBox, friendStatus);
        friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
        friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));

        return friendBox;
    }
}
