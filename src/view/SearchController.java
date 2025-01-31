package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import main.MainStorage;
import model.Post;
import model.User;

import java.util.List;

public class SearchController extends HomeController {
    @FXML
    private VBox mainContainer;
    @FXML
    private Button showPeople;
    @FXML
    private Button showPosts;
    @FXML
    protected TextField searchText;
    @FXML
    private Circle userImage;

    private boolean searchPosts = false;
    private String search;
    public void initialize(){
        super.initialize();
        searchText.setText(search);
        Image im = loadProfilePicture(MainStorage.getUsersIMap().get(LoginController.getUserID()).getProfilePicture(), LoginController.getUserID());
        userImage.setFill(new ImagePattern(im));
    }
    @FXML
    public void handleShowPosts(ActionEvent e){
        // showPosts.setOnMouseClicked(event -> {
        searchPosts = true;
        showPosts.setStyle("-fx-background-color: #181c1f; -fx-text-fill: #ffffff;");
        showPeople.setStyle("-fx-background-color: #0e1113; -fx-text-fill: #999999;");
        // });
        search(search);
    }
    @FXML
    public void handleShowPeople(ActionEvent e){
        // showPeople.setOnMouseClicked(event -> {
        searchPosts = false;
        showPeople.setStyle("-fx-background-color: #181c1f; -fx-text-fill: #ffffff;");
        showPosts.setStyle("-fx-background-color: #0e1113; -fx-text-fill: #999999;");
        // });
        search(search);
    }

    public void search(String s) {
        this.search = s;
        searchText.setText(search);
        mainContainer.getChildren().clear();
        if (searchPosts) {
            List<Post> posts = MainStorage.getAllPosts().values().stream()
                    .filter(post -> post.getPostContent().contains(search))
                    .sorted((u1, u2) -> u1.getPostContent().compareToIgnoreCase(u2.getPostContent()))
                    .toList();

            for (Post post : posts) {
                VBox postBox = createPostBox(post, post.getPostID());
                mainContainer.getChildren().add(postBox);
            }
        } else {
            List<User> users = MainStorage.getUsersSMap().values().stream()
                    .filter(user -> user.getName().toLowerCase().contains(search.toLowerCase()))
                    .sorted((u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName()))
                    .toList();
            for (User user : users) {
                if(user.getUserID() == LoginController.getUserID()) return;
                RecommandFriends recommandFriends = new RecommandFriends();
                VBox userBox = recommandFriends.createFriendBox(user);
                mainContainer.getChildren().add(userBox);
            }
        }
    }

    @Override
    protected void displayPostsLatest() {
        return;
    }
}
