package view;

import database.DatabaseGetter;
import database.DatabaseInsert;
import database.DatabaseUpdate;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;

public class RecommandFriends extends FriendBaseController {

    public void displayFriendList() {
        var friends =DatabaseGetter.getUsers();
        friendListContainer.getChildren().clear();
        for (var friend : friends) {
            if (friend.getUserID() != LoginController.userID) {
                VBox friendBox =createFriendBox(friend);
                if(createFriendBox(friend) != null)
                    friendListContainer.getChildren().add(friendBox);
            }
        }
    }

    @SuppressWarnings("unused")
    public VBox createFriendBox(User friend) {
        VBox friendBox = new VBox();
        friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        friendBox.setPrefWidth(400);

        Label friendName = new Label(friend.getName());
        friendName.setStyle("-fx-font-size: 21px; -fx-text-fill: #ffffff;");

        Label friendStatus = new Label();
        friendStatus.setStyle("-fx-font-size: 15px; -fx-text-fill: #999999;");

        Button addFriendButton = new Button("Add Friend");
        addFriendButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");
        cancelButton.setVisible(false);

        int userId = LoginController.userID; 
        int friendId = friend.getUserID();
        if (DatabaseGetter.isFriend(userId, friendId) ){
            friendStatus.setText("Friend Request Sent");
            addFriendButton.setDisable(true);
            cancelButton.setVisible(true);
        }
        if ( DatabaseGetter.isConfirm( friendId,userId) ){
            return null;
        }
       
        addFriendButton.setOnAction(event -> {
            if (!DatabaseGetter.isFriend(userId, friendId)) {
                DatabaseInsert.sendFriendRequest(userId, friendId);
                friendStatus.setText("Friend Request Sent");
                addFriendButton.setDisable(true);
                cancelButton.setVisible(true);
            }
        });

        cancelButton.setOnAction(event -> {
            if (DatabaseGetter.isFriend(userId, friendId)) {
                DatabaseUpdate.cancelFriendRequest(userId, friendId);
                friendStatus.setText("Friend Request Canceled");
                addFriendButton.setDisable(false);
                cancelButton.setVisible(false);
            }
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(addFriendButton, cancelButton);
        buttons.setStyle("-fx-spacing: 10;");

        friendBox.getChildren().addAll(friendName, friendStatus,buttons);
        friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
        friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));
        
        return friendBox;
    }
}
