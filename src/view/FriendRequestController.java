package view;

import java.io.ByteArrayInputStream;

import database.DatabaseGetter;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.User;

public class FriendRequestController extends FriendBaseController  {
    
    
    public void displayFriendList() {
        var requests = DatabaseGetter.getIncomingRequests(LoginController.userID);
    friendListContainer.getChildren().clear();

    for (var request : requests) {
        VBox requestBox = createFriendBox(request);
        friendListContainer.getChildren().add(requestBox);
    }
    }
    @SuppressWarnings("unused")

// public VBox createFriendBox(User friend) {
//     VBox friendBox = new VBox();
//     friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
//     friendBox.setPrefWidth(400);

//     // Profile Picture
//     byte[] profilePicture = friend.getProfilePicture();
//     Image profileImage = null;
//     if (profilePicture != null && profilePicture.length > 0) {
//         try {
//             profileImage = new Image(new ByteArrayInputStream(profilePicture));
//         } catch (Exception e) {
//             System.out.println("Error loading profile picture: " + e.getMessage());
//             profileImage = null;
//         }
//     }

//     Circle profileImageView = new Circle(25); // Circle for profile image, radius 25
//     if (profileImage != null) {
//         profileImageView.setFill(new ImagePattern(profileImage));
//     } else {
//         profileImageView.setFill(Color.GRAY); // Fallback color
//     }

//     // Friend Name
//     Label friendName = new Label(friend.getName());
//     friendName.setStyle("-fx-font-size: 22px; -fx-text-fill: rgb(153, 153, 153);");

//     // Friend Status
//     Label friendStatus = new Label("Friend");
//     friendStatus.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");

//     // Buttons
//     Button rejectButton = new Button("Cancel");
//     Button acceptButton = new Button("Confirm");

//     int userId = LoginController.userID; 
//     int friendId = friend.getUserID();
//     if (DatabaseGetter.isConfirm(friendId, userId)) {
//         friendStatus.setText("Friend");
//         acceptButton.setVisible(false);
//         rejectButton.setVisible(false);
//     }

//     acceptButton.setOnAction(event -> {
//         try {
//             if (!DatabaseGetter.isConfirm(friendId, userId)) {
//                 DatabaseInsert.addFriend(friendId, userId);
//                 DatabaseInsert.addFriend(userId, friendId);
//                 friendStatus.setText(" ");
//                 acceptButton.setVisible(false);
//                 rejectButton.setVisible(false);
//             }
//         } catch (Exception e) {
//             System.out.println("Error adding friend: " + e.getMessage());
//         }
//     });

//     rejectButton.setOnAction(event -> {
//         DatabaseGetter.updateFriendStatus(friend.getUserID(), friend.getFriendCount(), "Rejected");
//         friendStatus.setText("Request Rejected");
//         acceptButton.setVisible(false);
//         rejectButton.setVisible(false);
//     });

//     HBox buttons = new HBox();
//     buttons.getChildren().addAll(acceptButton, rejectButton);

//     // HBox for profile picture and name
//     HBox profileBox = new HBox(profileImageView, friendName);
//     profileBox.setSpacing(10);
//     profileBox.setStyle("-fx-alignment: CENTER_LEFT;");

//     friendBox.getChildren().addAll(profileBox, friendStatus, buttons);
//     friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
//     friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));

//     return friendBox;
// }
public VBox createFriendBox(User friend) {
    VBox friendBox = new VBox();
    friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
    friendBox.setPrefWidth(400);

    User user = DatabaseGetter.getUserByID(LoginController.userID);
    Image profileImage = null;
    if (user != null) {
        // Set the profile name        
        // Get the profile picture byte array
        byte[] profilePicture = user.getProfilePicture();
        
        if (profilePicture != null && profilePicture.length > 0) {
            try {
                profileImage = new Image(new ByteArrayInputStream(profilePicture));
            } catch (Exception e) {
                System.out.println("Error loading profile picture: " + e.getMessage());
                profileImage = null;
            }
        }
        else {
            System.out.println("profilepicture = null");
        }
    } else {
        System.out.println("User not found!");
    }


    
    Circle profileImageView = new Circle(25); // Circle for profile image, radius 25
    if (profileImage != null) {
        profileImageView.setFill(new ImagePattern(profileImage));
    } else {
        profileImageView.setFill(Color.GRAY); // Fallback color
    }


    // Friend Name
    Label friendName = new Label(friend.getName());
    friendName.setStyle("-fx-font-size: 22px; -fx-text-fill: rgb(153, 153, 153);");

    // Friend Status
    Label friendStatus = new Label("Friend");
    friendStatus.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");

    HBox profileBox = new HBox(profileImageView, friendName);
    profileBox.setSpacing(10);
    profileBox.setStyle("-fx-alignment: CENTER_LEFT;");

    friendBox.getChildren().addAll(profileBox, friendStatus);
    return friendBox;
}


}
