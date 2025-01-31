package view;

import java.util.List;

import database.DatabaseInsert;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Region;

import main.*;
import model.*;

public class PostController extends BaseController {
    Post post;
    @FXML
    private HBox postInfo;
    @FXML
    private Label postContent;
    @FXML
    private VBox mainContainer;
    @FXML
    private VBox fullContainer;
    @FXML
    private TextArea newCommentField;
    @FXML
    private ImageView addCommentButton;
    @FXML
    private Circle userImage;

    public VBox createCommentBox(Comment comment) {
        int userID = comment.getUserID();
        String fullname = MainStorage.getUsersIMap().get(userID).getName();

        Circle commentImage = new Circle(15);
        Image commentPicture = loadProfilePicture(MainStorage.getUsersIMap().get(post.getUserID()).getProfilePicture(), post.getUserID());
        if(commentPicture == null) commentImage.setFill(Color.DODGERBLUE);
        else commentImage.setFill(new ImagePattern(commentPicture));

        HBox commentInfo = new HBox();
        commentInfo.getChildren().addAll(commentImage, new Label(fullname));
        commentInfo.setStyle("-fx-padding: 5; -fx-spacing: 10;");

        HBox commentContent = new HBox();
        Label commentText = new Label(comment.getCommentText());
        commentText.setWrapText(false);
        commentText.setPrefWidth(Region.USE_COMPUTED_SIZE);
        commentText.setMinWidth(0);
        commentText.setMaxWidth(Double.MAX_VALUE);
        commentContent.getChildren().add(commentText);
        commentContent.setStyle("-fx-padding: 5; -fx-spacing: 10;");

        VBox commentBox = new VBox(commentInfo, commentContent);
        commentBox.setStyle("-fx-padding: 10; -fx-spacing: 5; "
            + "-fx-background-color: #2a2a2a; -fx-border-color: #0e1113; "
            + "-fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        commentBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        commentBox.setMinHeight(Region.USE_PREF_SIZE);
        commentBox.setMaxHeight(Double.MAX_VALUE);

        return commentBox;
    }

    public void displayPost(Post post, int postID) {
        this.post = post;
        post.setPostID(postID);

        int userID = LoginController.getUserID();

        User user = MainStorage.getUsersIMap().get(userID);
        byte[] profilePicture = user.getProfilePicture();
        int userId = post.getUserID();
        User postUser = MainStorage.getUsersIMap().get(userId);
        byte[] postPicture = postUser.getProfilePicture();
    
        Image profileImage = loadProfilePicture(profilePicture, userId);
        Image postImage = loadProfilePicture(postPicture, userId);
    
        Circle postCircle = new Circle(18);
        if (profileImage != null) {
            postCircle.setFill(new ImagePattern(postImage));
            userImage.setFill(new ImagePattern(profileImage));
        } else {
            postCircle.setFill(Color.DODGERBLUE);
            userImage.setFill(Color.DODGERBLUE);
        }

        HBox voteBox = getVoteBox(post);

        String userName = postUser.getName();
        Label userNameLabel = new Label(userName);
        userNameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label postTimeLabel = new Label(post.getCreationTime().toString());
        VBox postDetails = new VBox(userNameLabel, postTimeLabel);
        postDetails.setSpacing(5);
        
        postInfo.getChildren().addAll(postCircle, postDetails, voteBox);
        postInfo.setSpacing(10);
        postContent.setText(post.getPostContent());

        List<Comment> comments = post.getComments();
        System.out.println("Number of comments: " + comments.size());
        comments.sort((c1, c2) -> c2.getCreationTime().compareTo(c1.getCreationTime()));
        for (Comment comment : comments) {
            VBox commentBox = createCommentBox(comment);
            mainContainer.getChildren().add(commentBox);
        }
    }
    public void handleAddComment(MouseEvent event) {
        int userID = LoginController.getUserID();
        String text = newCommentField.getText();
        if (text != null && !text.isBlank()) {
            Comment comment = new Comment(text, post.getPostID(), userID);
            DatabaseInsert.insertComment(comment);
            MainStorage.addComment(comment);
            VBox commentBox = createCommentBox(comment);
            mainContainer.getChildren().add(0, commentBox);
            newCommentField.clear();
        }
    }

    @Override
    protected void displayPostsLatest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayPostsLatest'");
    }
}
