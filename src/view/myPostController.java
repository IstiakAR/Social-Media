package view;

import java.util.List;
import java.util.stream.Collectors;

import database.DatabaseGetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import main.MainController;
import model.Post;


public class myPostController extends BaseController {
    @FXML
    private ScrollBar ScrollBar;
    @FXML
    private ScrollPane ScrollPane;
    @FXML
    private VBox postsContainer;

    public void handleHome(ActionEvent event) {
        System.out.println("Home clicked");
        try {
            MainController.gotoHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void displayPostsLatest() {
        List<Post> posts = DatabaseGetter.getPosts();
        posts = posts.stream()
                .sorted((p1, p2) -> {
                    int compareDate = p2.getCreationTime().compareTo(p1.getCreationTime());
                    if (compareDate != 0) {
                        return compareDate;
                    }
                    int compareInteractions = Integer.compare(p2.getTotalReaction() + p2.getCommentCount(), p1.getTotalReaction() + p1.getCommentCount());
                    return compareInteractions;
                })
                .collect(Collectors.toList());

        for (Post post : posts) {
            if(post.getUserID()==LoginController.userID){   
                VBox postBox = createPostBox(post);
                postsContainer.getChildren().add(postBox);
            }
        }
    }
}

