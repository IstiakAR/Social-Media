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

public class FriendRequestController extends FriendBaseController {

    public void displayFriendList() {
        var requests = DatabaseGetter.getIncomingRequests(LoginController.userID);
        friendListContainer.getChildren().clear();

        for (var request : requests) {
            VBox requestBox = createFriendBox(request);
            if (requestBox != null)
                friendListContainer.getChildren().add(requestBox);
        }
    }

    @SuppressWarnings("unused")
    public VBox createFriendBox(User friend) {
        VBox friendBox = new VBox();
        friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        friendBox.setPrefWidth(400);

        // Friend Name
        Label friendName = new Label(friend.getName());
        friendName.setStyle("-fx-font-size: 22px; -fx-text-fill: rgb(153, 153, 153);");

        Label friendStatus = new Label("Pending Request");
        friendStatus.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");

        // Profile Image
        User user = DatabaseGetter.getUserByID(LoginController.userID);
        Image profileImage = null;
        if (friend != null) {
            byte[] profilePicture = friend.getProfilePicture();
            if (profilePicture != null && profilePicture.length > 0) {
                try {
                    profileImage = new Image(new ByteArrayInputStream(profilePicture));
                } catch (Exception e) {
                    System.out.println("Error loading profile picture: " + e.getMessage());
                }
            }
        }

        Circle profileImageView = new Circle(25);
        if (profileImage != null) {
            profileImageView.setFill(new ImagePattern(profileImage));
        } else {
            profileImageView.setFill(Color.GRAY);
        }

        HBox profileBox = new HBox(profileImageView, friendName);
        profileBox.setSpacing(10);
        profileBox.setStyle("-fx-alignment: CENTER_LEFT;");

        Button rejectButton = new Button("Cancel");
        Button acceptButton = new Button("Confirm");

        int userId = LoginController.userID;
        int friendId = friend.getUserID();
        if (DatabaseGetter.isConfirm(friendId, userId)) {
            return null;
        }

        acceptButton.setOnAction(event -> {
            try {
                if (!DatabaseGetter.isConfirm(friendId, userId)) {
                    DatabaseInsert.addFriend(friendId, userId);
                    DatabaseInsert.addFriend(userId, friendId);
                    friendStatus.setText("Friend");
                    acceptButton.setVisible(false);
                    rejectButton.setVisible(false);
                    DatabaseUpdate.cancelFriendRequest(userId, friendId);
                    friendListContainer.getChildren().remove(friendBox);
                }
            } catch (Exception e) {
                System.out.println("Error adding friend: " + e.getMessage());
            }
        });

        rejectButton.setOnAction(event -> {
            DatabaseUpdate.updateFriendStatus(friend.getUserID(), friend.getFriendCount(), "Rejected");
            friendStatus.setText("Request Rejected");
            acceptButton.setVisible(false);
            rejectButton.setVisible(false);
            DatabaseUpdate.cancelFriendRequest(userId, friendId);
            friendListContainer.getChildren().remove(friendBox);
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(acceptButton, rejectButton);
        buttons.setSpacing(10);

        friendBox.getChildren().addAll(profileBox, friendStatus, buttons);
        friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
        friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));

        return friendBox;
    }
}
