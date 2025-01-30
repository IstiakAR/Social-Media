package view;

import javafx.scene.layout.VBox;
import model.Post;
import database.DatabaseGetter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeController extends BaseController {
    public void initialize(){
        super.initialize();
        displayPostsLatest();
    }
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
}