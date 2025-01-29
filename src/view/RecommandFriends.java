package view;

import java.io.ByteArrayInputStream;

import database.DatabaseGetter;
import database.DatabaseInsert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.User;

public class RecommandFriends extends FriendBaseController {

    public void displayFriendList() {
        var friends =DatabaseGetter.getUsers();
        friendListContainer.getChildren().clear();
        for (var friend : friends) {
            if (friend.getUserID() != LoginController.userID) {
                VBox friendBox =createFriendBox(friend);
                friendListContainer.getChildren().add(friendBox);
            }
        }
    }

    @SuppressWarnings("unused")
    // public VBox createFriendBox(User friend) {
    //     VBox friendBox = new VBox();
    //     friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
    //     friendBox.setPrefWidth(400);

    //     Label friendName = new Label(friend.getName());
    //     friendName.setStyle("-fx-font-size: 22px; -fx-text-fill: #ffffff;");

    //     Label friendStatus = new Label(friend.getName());
    //     friendStatus.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");

    //     Button addFriendButton = new Button("Add Friend");
    //     addFriendButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");
    //     int userId = LoginController.userID; 
    //     int friendId = friend.getUserID();
    //     if (DatabaseGetter.isFriend(userId, friendId) ){
    //         friendStatus.setText("Friend Request Sent");
    //         addFriendButton.setDisable(true);
    //     }
    //     if ( DatabaseGetter.isConfirm( friendId,userId) ){
    //         friendStatus.setText("Already Friend");
    //         addFriendButton.setDisable(true);
    //     }
       
    //     addFriendButton.setOnAction(event -> {
            
           
    //         if (!DatabaseGetter.isFriend(userId, friendId)) {
    //             DatabaseInsert.sendFriendRequest(userId, friendId);
    //             friendStatus.setText("Friend Request Sent");
    //             addFriendButton.setDisable(true);
    //         }
    //     });
    //     friendBox.getChildren().addAll(friendName, friendStatus,addFriendButton);
    //     friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
    //     friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));
        
    //     return friendBox;
    // }

    public VBox createFriendBox(User friend) {
    VBox friendBox = new VBox();
    friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
    friendBox.setPrefWidth(400);

    // Profile Picture
    byte[] profilePicture = friend.getProfilePicture();
    Image profileImage = null;
    if (profilePicture != null && profilePicture.length > 0) {
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhh");
        try {
            profileImage = new Image(new ByteArrayInputStream(profilePicture));
        } catch (Exception e) {
            System.out.println("Error loading profile picture: " + e.getMessage());
            profileImage = null;
        }
    }
    
    Circle profileImageView = new Circle(25); // Circle for profile image, radius 25
    if (profileImage != null) {
        profileImageView.setFill(new ImagePattern(profileImage));
    } else {
        profileImageView.setFill(Color.GRAY); // Fallback color
    }

    // Friend Name
    Label friendName = new Label(friend.getName());
    friendName.setStyle("-fx-font-size: 22px; -fx-text-fill: #ffffff;");

    // Friend Status
    Label friendStatus = new Label(" ");
    friendStatus.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");

    Button addFriendButton = new Button("Add Friend");
    addFriendButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");

    int userId = LoginController.userID; 
    int friendId = friend.getUserID();
    if (DatabaseGetter.isFriend(userId, friendId)) {
        friendStatus.setText("Friend Request Sent");
        addFriendButton.setDisable(true);
    }
    if (DatabaseGetter.isConfirm(friendId, userId)) {
        friendStatus.setText("Already Friend");
        addFriendButton.setDisable(true);
    }

    addFriendButton.setOnAction(event -> {
        if (!DatabaseGetter.isFriend(userId, friendId)) {
            DatabaseInsert.sendFriendRequest(userId, friendId);
            friendStatus.setText("Friend Request Sent");
            addFriendButton.setDisable(true);
        }
    });

    // Create an HBox for the profile picture and friend name
    HBox profileBox = new HBox(profileImageView, friendName);
    profileBox.setSpacing(10);
    profileBox.setStyle("-fx-alignment: CENTER_LEFT;");

    friendBox.getChildren().addAll(profileBox, friendStatus, addFriendButton);
    friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
    friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));

    return friendBox;
}


}
