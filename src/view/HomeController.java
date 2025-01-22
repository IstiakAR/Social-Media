package view;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import main.MainController;
import model.Post;
import database.DatabaseGetter;

import java.util.List;
import java.util.stream.Collectors;

public class HomeController extends BaseController {

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
            VBox postBox = createPostBox(post);
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
}