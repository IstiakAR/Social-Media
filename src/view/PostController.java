package view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Comment;
import model.Post;

public class PostController {
    @FXML
    private Label postTitle;
    @FXML
    private Label postContent;
    @FXML
    private Label postInfo;
    @FXML
    private VBox commentsContainer;
    public void initialize(){
        // displayPost(post);
    }
    public void displayPost(Post post){
        postTitle.setText("Title");
        postContent.setText(post.getPostContent());
        postInfo.setText(post.getUserID()+post.getCreationTime().toString());
        List<Comment> comments = post.getComments();
        for (Comment comment : comments) {
            Text commentText = new Text(comment.getCommentText());
            commentsContainer.getChildren().add(commentText);
        }
    }
}
