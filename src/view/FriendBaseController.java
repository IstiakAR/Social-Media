package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import main.MainController;
import model.User;

public abstract class FriendBaseController extends SearchController {
    @FXML
    protected ScrollBar ScrollBar;
    @FXML
    protected ScrollPane ScrollPane;
    @FXML
    protected VBox mainContainer;
    @FXML
    protected Circle userImage;

    @SuppressWarnings("unused")

    public void initialize() {
        super.initialize();
    	displayFriendList();
    }
    public void updateScrollBarVisibility() {
        boolean shouldShowScrollBar =  mainContainer.getHeight() > ScrollPane.getHeight();
        ScrollBar.setVisible(shouldShowScrollBar);
    }

    public abstract void displayFriendList();
    public abstract VBox createFriendBox(User friend);

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
 
