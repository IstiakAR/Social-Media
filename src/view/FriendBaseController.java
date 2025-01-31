package view;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import model.User;

public abstract class FriendBaseController extends SearchController {
    @FXML
    protected VBox mainContainer;

    @SuppressWarnings("unused")

    public void initialize() {
        super.initialize();
    	displayFriendList();
    }

    public abstract void displayFriendList();
    public abstract VBox createFriendBox(User friend);
 
}
 
