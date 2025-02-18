package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.MainController;


public class Handler {
    @FXML
    protected TextField searchText;
    public void handleHome(MouseEvent event) {
        System.out.println("Home clicked");
        try {
            MainController.gotoHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleHome(ActionEvent event) {
        System.out.println("Home clicked");
        try {
            MainController.gotoHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleProfile(ActionEvent event) {
        System.out.println("Profile clicked");
        try {
            MainController.gotoProfile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleMessenger(ActionEvent event) {
        System.out.println("Messenger Button clicked!");
        try {
            MainController.gotoMessenger();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddPost(MouseEvent event) {
        try {
            MainController.showAddPostDialog(() -> {
                try {
                    MainController.gotoHomepage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleSettings(MouseEvent event) {
        System.out.println("Settings Button clicked!");
        try {
            MainController.gotoSettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleLogOut(MouseEvent event) {
        try {
            MainController.gotoLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleSearch(MouseEvent event) {
        String query = searchText.getText().trim();
        if (!query.isEmpty()) {
            try {
                MainController.gotoSearch(query, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
