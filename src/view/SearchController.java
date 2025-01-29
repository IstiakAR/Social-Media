package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.MainStorage;
import model.Post;
import model.User;

import java.util.List;

public class SearchController extends HomeController {
    @FXML
    private VBox postsContainer;
    @FXML
    private Button showPeople;
    @FXML
    private Button showPosts;
    @FXML
    protected TextField searchText;

    private boolean searchPosts = false;
    private String search;
    public void initialize(){
        super.initialize();
        searchText.setText(search);
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
        postsContainer.getChildren().clear();
        if (searchPosts) {
            List<Post> posts = MainStorage.getAllPosts().values().stream()
                    .filter(post -> post.getPostContent().contains(search))
                    .sorted((u1, u2) -> u1.getPostContent().compareToIgnoreCase(u2.getPostContent()))
                    .toList();

            for (Post post : posts) {
                VBox postBox = createPostBox(post, post.getPostID());
                postsContainer.getChildren().add(postBox);
            }
        } else {
            List<User> users = MainStorage.getUsersSMap().values().stream()
                    .filter(user -> user.getName().toLowerCase().contains(search.toLowerCase()))
                    .sorted((u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName()))
                    .toList();
            for (User user : users) {
                VBox userBox = createUserBox(user);
                postsContainer.getChildren().add(userBox);
            }
        }
    }

    public VBox createUserBox(User user) {
        VBox userBox = new VBox();
        userBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        userBox.setPrefWidth(400);

        Label userName = new Label(user.getName());
        userName.setStyle("-fx-font-size: 18px; -fx-text-fill:rgb(169, 22, 22);");

        userBox.getChildren().addAll(userName);
        userBox.setOnMouseEntered(event -> userBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
        userBox.setOnMouseExited(event -> userBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));
        
        return userBox;
    }
    @Override
    protected void displayPostsLatest() {
        return;
    }
}