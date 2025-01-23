package view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.MainStorage;
import model.Comment;
import model.Post;

public class PostController {
    @FXML
    private ImageView userAvatar;
    @FXML
    private HBox postInfo;
    @FXML
    private Label postContent;
    @FXML
    private VBox commentsContainer;

    public void initialize() {
        // displayPost(post);
    }

    public void displayPost(Post post, int postID) {
        post.setPostID(postID);
        System.out.println(post.getPostID());
        String userName = MainStorage.getUsersIMap().get(post.getUserID()).getName();
        postInfo.getChildren().addAll(new Label(userName), new Label(post.getCreationTime().toString()));
        postContent.setText(post.getPostContent());

        List<Comment> comments = post.getComments();
        System.out.println("Number of comments: " + comments.size());
        for (Comment comment : comments) {
            Text commentText = new Text(comment.getCommentText());
            commentsContainer.getChildren().add(commentText);
        }
    }
}
