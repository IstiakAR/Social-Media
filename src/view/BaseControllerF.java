package view;

import database.DatabaseGetter;
import database.DatabaseInsert;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import main.MainStorage;
import model.User;

public abstract class BaseControllerF {
     @FXML
    protected ScrollBar ScrollBar;
    @FXML
    protected ScrollPane ScrollPane;
 @FXML
protected VBox friendListContainer;

// @SuppressWarnings("unused")

public void initialize() {
    ScrollBar.valueProperty().bindBidirectional(ScrollPane.vvalueProperty());
    ScrollBar.maxProperty().bind(ScrollPane.vmaxProperty());
    ScrollBar.visibleAmountProperty().bind(ScrollPane.heightProperty().divide(friendListContainer.heightProperty()));
    ScrollBar.valueProperty().bindBidirectional(ScrollPane.vvalueProperty());
    friendListContainer.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
    ScrollPane.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
    updateScrollBarVisibility();

    
    displayFriendList();// Display the friend list
}

public abstract void displayFriendList();

private void updateScrollBarVisibility() {
    boolean shouldShowScrollBar =  friendListContainer.getHeight() > ScrollPane.getHeight();
    ScrollBar.setVisible(shouldShowScrollBar);
}




@SuppressWarnings("unused")
public VBox createFriendBox(User friend) { // Replace Friend with your actual friend object class
    VBox friendBox = new VBox();
    friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
    friendBox.setPrefWidth(400);

    Label friendName = new Label(friend.getName()); // Example getter for friend's name
    friendName.setStyle("-fx-font-size: 22px; -fx-text-fill: #ffffff;");

    Label friendStatus = new Label(friend.getName()); // Example getter for friend's status
    friendStatus.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");

     Button addFriendButton = new Button("Add Friend");
    addFriendButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");
    addFriendButton.setOnAction(event -> {
        int userId = friend.getUserID(); // Get logged-in user's ID
        int friendId = friend.getFriendCount();
        if (!DatabaseGetter.isFriend(userId, friendId)) {
            DatabaseInsert.addFriend(userId, friendId);
            friendStatus.setText("Friend Request Sent");
            addFriendButton.setDisable(true); // Disable button after sending request
        }
    });
    Button rejectButton = new Button("Reject");
    Button acceptButton = new Button("Accept");
    acceptButton.setOnAction(event -> {
        DatabaseGetter.updateFriendStatus(friend.getUserID(), friend.getFriendCount(), "Accepted");
        friendStatus.setText("Friend");
        acceptButton.setVisible(false);
      
        rejectButton.setVisible(false);
    });

    
    rejectButton.setOnAction(event -> {
        DatabaseGetter.updateFriendStatus(friend.getUserID(), friend.getFriendCount(), "Rejected");
        friendStatus.setText("Request Rejected");
        acceptButton.setVisible(false);
        rejectButton.setVisible(false);
    });
    friendBox.getChildren().addAll(friendName, friendStatus,addFriendButton);
    friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
    friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));
     
    return friendBox;
}


}
 