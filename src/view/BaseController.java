package view;

import java.io.ByteArrayInputStream;

import database.DatabaseGetter;
import database.DatabaseUpdate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import main.*;
import model.*;

public abstract class BaseController {
    @FXML
    protected ScrollBar ScrollBar;
    @FXML
    protected ScrollPane ScrollPane;
    @FXML
    protected VBox postsContainer;

    @SuppressWarnings("unused")
    @FXML
    public void initialize() {
        ScrollBar.valueProperty().bindBidirectional(ScrollPane.vvalueProperty());
        ScrollBar.maxProperty().bind(ScrollPane.vmaxProperty());
        ScrollBar.visibleAmountProperty().bind(ScrollPane.heightProperty().divide(postsContainer.heightProperty()));
        ScrollBar.valueProperty().bindBidirectional(ScrollPane.vvalueProperty());
        postsContainer.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        ScrollPane.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        updateScrollBarVisibility();
        
        displayPostsLatest();
    }

    protected abstract void displayPostsLatest();

    private void updateScrollBarVisibility() {
        boolean shouldShowScrollBar = postsContainer.getHeight() > ScrollPane.getHeight();
        ScrollBar.setVisible(shouldShowScrollBar);
    }

    @SuppressWarnings("unused")
    protected VBox createPostBox(Post post, int postID) {
        post.setPostID(postID);
        VBox postBox = new VBox();
        HBox voteBox = new HBox();
        HBox voteTempBox = new HBox();
        Button upvoteButton = new Button("▲");
        Button downvoteButton = new Button("▼");
        Button saveButton = new Button("Save");
        postBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        postBox.prefWidthProperty().bind(ScrollPane.widthProperty().subtract(20));

        String buttonStyle = "-fx-background-color: #0e1113; -fx-text-fill: #ffffff; -fx-padding: 5; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;";
        String buttonHoverStyle = "-fx-background-color: #181c1f; -fx-text-fill: #ffffff; -fx-padding: 5; -fx-border-color: #181c1f; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;";
        String buttonClickStyle = "-fx-background-color: blue; -fx-text-fill: white; -fx-padding: 5; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;";

        Label voteCount = new Label(String.valueOf(DatabaseGetter.getTotalVotes(post.getPostID())));
        voteCount.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff;");

        upvoteButton.setStyle(buttonStyle);
        downvoteButton.setStyle(buttonStyle);
        upvoteButton.setPrefWidth(30);
        downvoteButton.setPrefWidth(30);

        upvoteButton.setOnMouseClicked(e -> {
            int current = post.getReaction(LoginController.userID);
            System.out.println("current" + ' ' + current);
            System.out.println("postID" + ' ' + post.getPostID());
            if (current == 1) {
                upvoteButton.setStyle(buttonStyle);
                DatabaseUpdate.updateVote(0, postID, LoginController.userID);
                post.setTotalReaction(post.getTotalReaction() - 1);
            } else {
                upvoteButton.setStyle(buttonClickStyle);
                downvoteButton.setStyle(buttonStyle);
                DatabaseUpdate.updateVote(1, postID, LoginController.userID);
                if (current == -1) {
                    post.setTotalReaction(post.getTotalReaction() + 2);
                } else {
                    post.setTotalReaction(post.getTotalReaction() + 1);
                }
            }
            voteCount.setText(String.valueOf(DatabaseGetter.getTotalVotes(post.getPostID())));
            System.out.println("total" + ' ' + post.getTotalReaction());
        });
        
        downvoteButton.setOnMouseClicked(e -> {
            int current = post.getReaction(LoginController.userID);
            System.out.println("current" + ' ' + current);
            System.out.println("postID" + ' ' + post.getPostID());
            if (current == -1) {
                downvoteButton.setStyle(buttonStyle);
                DatabaseUpdate.updateVote(0, postID, LoginController.userID);
                post.setTotalReaction(post.getTotalReaction() + 1);
            } else {
                downvoteButton.setStyle(buttonClickStyle);
                upvoteButton.setStyle(buttonStyle);
                DatabaseUpdate.updateVote(-1, postID, LoginController.userID);
                if (current == 1) {
                    post.setTotalReaction(post.getTotalReaction() - 2);
                } else {
                    post.setTotalReaction(post.getTotalReaction() - 1);
                }
            }
            voteCount.setText(String.valueOf(DatabaseGetter.getTotalVotes(post.getPostID())));
            System.out.println("total" + ' ' + post.getTotalReaction());
        });

        voteTempBox.getChildren().addAll(upvoteButton, voteCount, downvoteButton);
        voteTempBox.setStyle("-fx-background-color: #2a3236; -fx-background-radius: 10; -fx-alignment:CENTER;");

        voteBox.getChildren().add(voteTempBox);

        postBox.setOnMouseEntered(event -> {
            postBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
            // upvoteButton.setStyle(buttonHoverStyle);
            // downvoteButton.setStyle(buttonHoverStyle);
        });
        postBox.setOnMouseExited(event -> {
            postBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
            // upvoteButton.setStyle(buttonStyle);
            // downvoteButton.setStyle(buttonStyle);
        });
        
        postBox.setOnMouseClicked(event -> {
            try {
                MainController.gotoPost(post,postID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox contentBox = new VBox();
        Label postContent = new Label(post.getPostContent());
        postContent.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff;");
        postContent.setWrapText(true);
        User user = MainStorage.getUsersIMap().get(post.getUserID());
        if (user == null) {
            System.out.println("Error: User not found for ID: " + post.getUserID());
        }
        
        byte[] profilePicture = user.getProfilePicture();
        Image profileImage = null;
        if (profilePicture != null && profilePicture.length > 0) {
            try {
                System.out.println("Profile picture found, size: " + profilePicture.length);
                profileImage = new Image(new ByteArrayInputStream(profilePicture));
            } catch (Exception e) {
                System.out.println("Error loading profile picture: " + e.getMessage());
                profileImage = null;
            }
        } else {
            System.out.println("Error: Profile picture is null or empty for user ID: " + post.getUserID());
            profileImage = null;
        }
        
        Circle postImage = new Circle(15); // Set radius to ensure the circle is visible
        if (profileImage != null) {
            postImage.setFill(new ImagePattern(profileImage));
        } else {
            // Fallback color (e.g., gray)
            postImage.setFill(Color.GRAY);
        }
        
        HBox authorDateBox = new HBox();
        Label postAuthor = new Label(MainStorage.getUsersIMap().get(post.getUserID()).getName());
        postAuthor.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");
        Label postDate = new Label(post.getCreationTime());
        postDate.setStyle("-fx-font-size: 12px; -fx-text-fill: #999999;");
        authorDateBox.getChildren().addAll(postImage, postAuthor, new Label(" | "), postDate);
        authorDateBox.setStyle("-fx-spacing: 5;");

        contentBox.getChildren().addAll(authorDateBox,postContent, voteBox);
        contentBox.setStyle("-fx-padding: 5;");

        postBox.getChildren().add(contentBox);

        return postBox;
    }
}