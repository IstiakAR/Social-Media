package view;

import java.io.ByteArrayInputStream;

import database.DatabaseGetter;
import database.DatabaseInsert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import main.*;
import model.*;

public abstract class BaseController{
    @FXML
    protected ScrollBar ScrollBar;
    @FXML
    protected ScrollPane ScrollPane;
    @FXML
    protected VBox postsContainer;
    @FXML
    protected TextField searchText;
    @FXML
    protected Circle userImage;

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
        
        Image profileImage = loadProfilePicture(MainStorage.getUsersIMap().get(LoginController.userID).getProfilePicture(), LoginController.userID);
        userImage.setFill(new ImagePattern(profileImage));

        // displayPostsLatest();
    }

    protected abstract void displayPostsLatest();

    private void updateScrollBarVisibility() {
        boolean shouldShowScrollBar = postsContainer.getHeight() > ScrollPane.getHeight();
        ScrollBar.setVisible(shouldShowScrollBar);
    }

    protected Image loadProfilePicture(byte[] pictureData, int userId) {
        if (pictureData != null && pictureData.length > 0) {
            try {
                System.out.println("Profile picture found for user ID " + userId + ", size: " + pictureData.length);
                return new Image(new ByteArrayInputStream(pictureData));
            } catch (Exception e) {
                System.out.println("Error loading profile picture for user ID " + userId + ": " + e.getMessage());
            }
        } else {
            System.out.println("Error: Profile picture is null or empty for user ID: " + userId);
        }
        return null;
    }
    protected HBox getVoteBox(Post post){
        HBox voteTempBox = new HBox();
        Button upvoteButton = new Button("▲");

        String buttonStyle = "-fx-background-color: #0e1113; -fx-text-fill: #ffffff; -fx-padding: 5; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;";
        String buttonClickStyle = "-fx-background-color: blue; -fx-text-fill: white; -fx-padding: 5; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;";

        Label voteCount = new Label(String.valueOf(DatabaseGetter.getTotalVotes(post.getPostID())));
        voteCount.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff;");

        upvoteButton.setStyle(buttonStyle);
        upvoteButton.setPrefWidth(30);

        boolean hasVoted = DatabaseInsert.voteExists(post.getPostID(), LoginController.userID);
        upvoteButton.setStyle(hasVoted ? buttonClickStyle : buttonStyle);

        upvoteButton.setOnMouseClicked(e -> {
            boolean newState = DatabaseInsert.toggleVote(post.getPostID(), LoginController.userID);
            upvoteButton.setStyle(newState ? buttonClickStyle : buttonStyle);
            voteCount.setText(String.valueOf(DatabaseGetter.getTotalVotes(post.getPostID())));
        });
        
        voteTempBox.getChildren().addAll(upvoteButton, voteCount);
        voteTempBox.setStyle("-fx-background-color: #0e1113; -fx-background-radius: 10; -fx-alignment:CENTER;");

        return voteTempBox;
    }
    @SuppressWarnings("unused")
    protected VBox createPostBox(Post post, int postID) {
        post.setPostID(postID);
        VBox postBox = new VBox();
        HBox voteBox = new HBox();
        HBox voteTempBox = new HBox();
        Button upvoteButton = new Button("▲");
        postBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        postBox.prefWidthProperty().bind(ScrollPane.widthProperty().subtract(20));

        String buttonStyle = "-fx-background-color: #0e1113; -fx-text-fill: #ffffff; -fx-padding: 5; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;";
        String buttonClickStyle = "-fx-background-color: blue; -fx-text-fill: white; -fx-padding: 5; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;";

        Label voteCount = new Label(String.valueOf(DatabaseGetter.getTotalVotes(post.getPostID())));
        voteCount.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff;");

        upvoteButton.setStyle(buttonStyle);
        upvoteButton.setPrefWidth(30);

        boolean hasVoted = DatabaseInsert.voteExists(post.getPostID(), LoginController.userID);
        upvoteButton.setStyle(hasVoted ? buttonClickStyle : buttonStyle);

        upvoteButton.setOnMouseClicked(e -> {
            boolean newState = DatabaseInsert.toggleVote(post.getPostID(), LoginController.userID);
            upvoteButton.setStyle(newState ? buttonClickStyle : buttonStyle);
            voteCount.setText(String.valueOf(DatabaseGetter.getTotalVotes(post.getPostID())));
        });
        
        voteTempBox.getChildren().addAll(upvoteButton, voteCount);
        voteTempBox.setStyle("-fx-background-color: #0e1113; -fx-background-radius: 10; -fx-alignment:CENTER;");

        voteBox.getChildren().add(voteTempBox);


        postBox.setOnMouseEntered(event -> {
            postBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        });
        postBox.setOnMouseExited(event -> {
            postBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
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
        
        
        Circle postImage = new Circle(15);
        Image postPicture = loadProfilePicture(MainStorage.getUsersIMap().get(post.getUserID()).getProfilePicture(), post.getUserID());
        postImage.setFill(new ImagePattern(postPicture));

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

    public void handleSearch(MouseEvent event) {
        String query = searchText.getText().trim();
        if (!query.isEmpty()) {
            try {
                MainController.gotoSearch(query, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void handleLogOut(MouseEvent event) {
        try {
            MainController.gotoLoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
