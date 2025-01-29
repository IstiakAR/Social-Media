package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.MainController;
import model.Post;
import database.DatabaseGetter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeController extends BaseController {

    @Override
    protected void displayPostsLatest() {
        Map<Integer, Post> posts = DatabaseGetter.getAllPosts();
        List<Post> listPosts = posts.values().stream()
                .sorted((p1, p2) -> {
                    int compareDate = p2.getCreationTime().compareTo(p1.getCreationTime());
                    if (compareDate != 0) {
                        return compareDate;
                    }
                    int compareInteractions = Integer.compare(p2.getTotalReaction() + p2.getCommentCount(), p1.getTotalReaction() + p1.getCommentCount());
                    return compareInteractions;
                })
                .collect(Collectors.toList());

        for (Post post : listPosts) {
            VBox postBox = createPostBox(post, post.getPostID());
            postsContainer.getChildren().add(postBox);
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

    public void handleFriends(ActionEvent event) {
        System.out.println("Friends Button clicked!");
        try {
            MainController.gotoFriends();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleMassenger(ActionEvent event) {
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
    public void handleProfile2(MouseEvent event) {
        System.out.println("Profile Button clicked!");
        try {
            MainController.gotoProfile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}