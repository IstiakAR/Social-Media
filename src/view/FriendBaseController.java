package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import main.MainController;
import model.User;

public abstract class FriendBaseController {
    @FXML
    protected ScrollBar ScrollBar;
    @FXML
    protected ScrollPane ScrollPane;
    @FXML
    protected VBox friendListContainer;

    @SuppressWarnings("unused")

    public void initialize() {
        ScrollBar.valueProperty().bindBidirectional(ScrollPane.vvalueProperty());
        ScrollBar.maxProperty().bind(ScrollPane.vmaxProperty());
        ScrollBar.visibleAmountProperty().bind(ScrollPane.heightProperty().divide(friendListContainer.heightProperty()));
        ScrollBar.valueProperty().bindBidirectional(ScrollPane.vvalueProperty());
        friendListContainer.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        ScrollPane.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        updateScrollBarVisibility();
        
        System.out.println("initialize called");
    	displayFriendList();
    }
    public void updateScrollBarVisibility() {
        boolean shouldShowScrollBar =  friendListContainer.getHeight() > ScrollPane.getHeight();
        ScrollBar.setVisible(shouldShowScrollBar);
    }

    public abstract void displayFriendList();
    public abstract VBox createFriendBox(User friend);


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
 
