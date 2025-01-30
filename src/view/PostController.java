package view;

import java.util.List;

import database.DatabaseInsert;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
    private VBox commentsContainer;
    @FXML
    private ScrollBar ScrollBar;
    @FXML
    private ScrollPane ScrollPane;
    @FXML
    private VBox fullContainer;
    @FXML
    private TextArea newCommentField;
    @FXML
    private ImageView addCommentButton;
    @FXML
    private Circle userImage;

    @SuppressWarnings("unused")
    @FXML
    public void initialize() {
        ScrollPane.setContent(fullContainer);
        ScrollPane.setFitToWidth(true);
        ScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        ScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    
        ScrollBar.valueProperty().bindBidirectional(ScrollPane.vvalueProperty());
        ScrollBar.maxProperty().bind(ScrollPane.vmaxProperty());
        ScrollBar.visibleAmountProperty().bind(ScrollPane.heightProperty().divide(commentsContainer.heightProperty()));
        ScrollBar.valueProperty().bindBidirectional(ScrollPane.vvalueProperty());
    
        commentsContainer.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        ScrollPane.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        updateScrollBarVisibility();
        // displayPost(post, post.getPostID());
    }

    private void updateScrollBarVisibility() {
        boolean shouldShowScrollBar = commentsContainer.getHeight() > ScrollPane.getHeight();
        ScrollBar.setVisible(shouldShowScrollBar);
    }

    public VBox createCommentBox(Comment comment) {
        int userID = comment.getUserID();
        String userName = MainStorage.getUsersIMap().get(userID).getName();

        HBox commentInfo = new HBox();
        commentInfo.getChildren().addAll(new Label(userName));
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
        
        // Height is dynamically computed based on content
        commentBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        commentBox.setMinHeight(Region.USE_PREF_SIZE);
        commentBox.setMaxHeight(Double.MAX_VALUE);

        return commentBox;
    }

    public void displayPost(Post post, int postID) {
        this.post = post;
        post.setPostID(postID);

        int userID = LoginController.userID;

        BaseController baseController = new BaseController() {
            @Override
            protected void displayPostsLatest() {
                
            }
        };

        User user = MainStorage.getUsersIMap().get(userID);
        byte[] profilePicture = user.getProfilePicture();
        int userId = post.getUserID();
        User postUser = MainStorage.getUsersIMap().get(userId);
        byte[] postPicture = postUser.getProfilePicture();
    
        Image profileImage = baseController.loadProfilePicture(profilePicture, userId);
        Image postImage = baseController.loadProfilePicture(postPicture, userId);
    
        Circle postCircle = new Circle(18);
        if (profileImage != null) {
            postCircle.setFill(new ImagePattern(postImage));
            userImage.setFill(new ImagePattern(profileImage));
        } else {
            postCircle.setFill(Color.GRAY);
            userImage.setFill(Color.GRAY);
        }

        HBox voteBox = baseController.getVoteBox(post);

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
            commentsContainer.getChildren().add(commentBox);
        }
    }
    public void handleAddComment(MouseEvent event) {
        int userID = LoginController.userID;
        String text = newCommentField.getText();
        if (text != null && !text.isBlank()) {
            Comment comment = new Comment(text, post.getPostID(), userID);
            DatabaseInsert.insertComment(comment);
            MainStorage.addComment(comment);
            VBox commentBox = createCommentBox(comment);
            commentsContainer.getChildren().add(0, commentBox);
            newCommentField.clear();
        }
    }

    @Override
    protected void displayPostsLatest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayPostsLatest'");
    }
}
