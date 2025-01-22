package view;

import database.DatabaseGetter;
import database.DatabaseInsert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    public VBox createFriendBox(User friend) {
        VBox friendBox = new VBox();
        friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        friendBox.setPrefWidth(400);

        Label friendName = new Label(friend.getName());
        friendName.setStyle("-fx-font-size: 22px; -fx-text-fill:rgb(173, 28, 28);");

        Label friendStatus = new Label(friend.getName());
        friendStatus.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");

        Button rejectButton = new Button("Cancel");
        Button acceptButton = new Button("Confirm");

        int userId = LoginController.userID; 
        int friendId = friend.getUserID();
        if (DatabaseGetter.isConfirm( friendId,userId)){
            friendStatus.setText("Friend");
            acceptButton.setVisible(false);
            rejectButton.setVisible(false);
        }

        acceptButton.setOnAction(event -> {
            //DatabaseGetter.updateFriendStatus(friend.getUserID(), friend.getFriendCount(), "Accepted");
           
            try {
                if (!DatabaseGetter.isConfirm( friendId,userId)){
                DatabaseInsert.addFriend( friendId,userId);
                friendStatus.setText("Friend");
                acceptButton.setVisible(false);
                rejectButton.setVisible(false);
                }
            } catch (Exception e) {
                System.out.println("Error adding friend: " + e.getMessage());
            }
            
        });
        
        rejectButton.setOnAction(event -> {
            DatabaseGetter.updateFriendStatus(friend.getUserID(), friend.getFriendCount(), "Rejected");
            friendStatus.setText("Request Rejected");
            acceptButton.setVisible(false);
            rejectButton.setVisible(false);
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(acceptButton, rejectButton);

        friendBox.getChildren().addAll(friendName, friendStatus,buttons);
        friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
        friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));
        
        return friendBox;
    }
}
