package view;

import javafx.event.ActionEvent;
import main.MainController;

public class friendsController {
    
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
}
