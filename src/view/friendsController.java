package view;

import java.util.List;
import java.util.stream.Collectors;

import database.DatabaseGetter;
import database.DatabaseInsert;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import main.MainController;
import model.Post;
import model.User;

public class friendsController extends BaseControllerF  {
    
     public void handleHome(ActionEvent event) {
        System.out.println("Home Button clicked!");
        try {
            MainController.gotoHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleFriends(ActionEvent event) {
        System.out.println("Add Friends Button clicked!");
        try {
            MainController.gotoFriends();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleFriendRequest(ActionEvent event) {
        System.out.println("Friend Request Button clicked!");
        try {
            MainController.gotoFriendRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleAllFriend(ActionEvent event) {
        System.out.println("All Friend Button clicked!");
        try {
            MainController.gotoAllFriends();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void displayFriendList() {
        // Fetch friends from MainStorage or another source
        var friends =DatabaseGetter.getUsers(); // Example method, replace with actual implementation
    
        friendListContainer.getChildren().clear(); // Clear any existing items
    
        for (var friend : friends) {
            if (friend.getUserID() != LoginController.userID) {
                VBox friendBox =createFriendBox(friend);
                friendListContainer.getChildren().add(friendBox);
            }
        }
    }

}
