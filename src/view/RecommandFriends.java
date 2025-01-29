package view;

import java.io.ByteArrayInputStream;

import database.DatabaseGetter;
import database.DatabaseInsert;
import database.DatabaseUpdate;
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
        var friends = DatabaseGetter.getUsers();
        friendListContainer.getChildren().clear();
        for (var friend : friends) {
            if (friend.getUserID() != LoginController.userID) {
                VBox friendBox = createFriendBox(friend);
                if (friendBox != null) {
                    friendListContainer.getChildren().add(friendBox);
                }
            }
        }
    }

    public VBox createFriendBox(User friend) {
        VBox friendBox = new VBox();
        friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        friendBox.setPrefWidth(400);

        // Profile Picture
        byte[] profilePicture = friend.getProfilePicture();
        Image profileImage = null;
        if (profilePicture != null && profilePicture.length > 0) {
            try {
                profileImage = new Image(new ByteArrayInputStream(profilePicture));
            } catch (Exception e) {
                System.out.println("Error loading profile picture: " + e.getMessage());
            }
        }

        Circle profileImageView = new Circle(25);
        profileImageView.setFill(profileImage != null ? new ImagePattern(profileImage) : Color.GRAY);

        // Friend Name and Status
        Label friendName = new Label(friend.getName());
        friendName.setStyle("-fx-font-size: 22px; -fx-text-fill: #ffffff;");

        Label friendStatus = new Label();
        friendStatus.setStyle("-fx-font-size: 15px; -fx-text-fill: #999999;");

        Button addFriendButton = new Button("Add Friend");
        addFriendButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");
        cancelButton.setVisible(false);

        int userId = LoginController.userID;
        int friendId = friend.getUserID();

        // Check current friendship/request status
        if (DatabaseGetter.isFriend(userId, friendId)) {
            friendStatus.setText("Friend Request Sent");
            addFriendButton.setDisable(true);
        } else if (DatabaseGetter.isConfirm(friendId, userId)) {
            friendStatus.setText("Already Friend");
            addFriendButton.setDisable(true);
        }

        // Add Friend Button Logic
        addFriendButton.setOnAction(event -> {
            if (!DatabaseGetter.isFriend(userId, friendId)) {
                DatabaseInsert.sendFriendRequest(userId, friendId);
                friendStatus.setText("Friend Request Sent");
                addFriendButton.setDisable(true);
                cancelButton.setVisible(true);
            }
        });

        // Cancel Friend Request Button Logic
        cancelButton.setOnAction(event -> {
            if (DatabaseGetter.isFriend(userId, friendId)) {
                DatabaseUpdate.cancelFriendRequest(userId, friendId);
                friendStatus.setText("Friend Request Canceled");
                addFriendButton.setDisable(false);
                cancelButton.setVisible(false);
            }
        });

        // Layout for buttons
        HBox buttons = new HBox(addFriendButton, cancelButton);
        buttons.setSpacing(10);

        // Profile and Name Layout
        HBox profileBox = new HBox(profileImageView, friendName);
        profileBox.setSpacing(10);
        profileBox.setStyle("-fx-alignment: CENTER_LEFT;");

        friendBox.getChildren().addAll(profileBox, friendStatus, buttons);
        friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
        friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));

        return friendBox;
    }
}
