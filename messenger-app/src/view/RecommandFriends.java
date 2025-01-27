package view;

import database.DatabaseGetter;
import database.DatabaseInsert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.User;

public class RecommandFriends {
    public VBox createFriendBox(User friend) {
        VBox friendBox = new VBox();
        friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        friendBox.setPrefWidth(400);

        Label friendName = new Label(friend.getName());
        friendName.setStyle("-fx-font-size: 22px; -fx-text-fill: #ffffff;");

        Label friendStatus = new Label(friend.getName());
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
        
        friendBox.getChildren().addAll(friendName, friendStatus, addFriendButton);
        friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
        friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));
        
        return friendBox;
    }
}