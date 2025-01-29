package view;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import database.DatabaseGetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import main.MainController;
import model.Post;


public class myPostController extends BaseController{
    @FXML
    private ScrollBar ScrollBar;
    @FXML
    private ScrollPane ScrollPane;
    @FXML
    private VBox postsContainer;
    @FXML
    private Circle userImage;

    public void initialize() {
        super.initialize();
        displayPostsLatest();
    }

    public void handleBack(ActionEvent event) {
        try {
            MainController.gotoProfile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void displayPostsLatest() {
        Map<Integer, Post> posts = DatabaseGetter.getUserPosts(LoginController.userID);
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
        postsContainer.setSpacing(5);
    }
}
