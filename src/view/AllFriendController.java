package view;

import database.DatabaseGetter;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.User;

public class AllFriendController extends FriendBaseController  {
    
    public void displayFriendList() {
        var requests = DatabaseGetter.getAllfriend(LoginController.userID);
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
        friendName.setStyle("-fx-font-size: 22px; -fx-text-fill:rgb(169, 22, 22);");
        
        friendBox.getChildren().addAll(friendName);
        friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
        friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));
        
        return friendBox;
    }
}
