package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.MainStorage;
import model.Post;

public abstract class BaseController {
    @FXML
    protected ScrollBar homeScrollBar;
    @FXML
    protected ScrollPane homeScrollPane;
    @FXML
    protected VBox postsContainer;

    @FXML
    public void initialize() {
        homeScrollBar.valueProperty().bindBidirectional(homeScrollPane.vvalueProperty());
        homeScrollBar.maxProperty().bind(homeScrollPane.vmaxProperty());
        homeScrollBar.visibleAmountProperty().bind(homeScrollPane.heightProperty().divide(postsContainer.heightProperty()));
        homeScrollBar.valueProperty().bindBidirectional(homeScrollPane.vvalueProperty());
        postsContainer.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        homeScrollPane.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        updateScrollBarVisibility();

        displayPostsLatest();
    }

    protected abstract void displayPostsLatest();

    private void updateScrollBarVisibility() {
        boolean shouldShowScrollBar = postsContainer.getHeight() > homeScrollPane.getHeight();
        homeScrollBar.setVisible(shouldShowScrollBar);
    }
        protected VBox createPostBox(Post post) {
        VBox postBox = new VBox();
        HBox voteBox = new HBox();
        HBox voteTempBox = new HBox();
        Button upvoteButton = new Button("▲");
        Button downvoteButton = new Button("▼");
        Button commentButton = new Button("󰍨");
        postBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");

        postBox.prefWidthProperty().bind(homeScrollPane.widthProperty().subtract(20));


        String buttonStyle = "-fx-background-color: #0e1113; -fx-text-fill: #ffffff; -fx-padding: 5; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5;";
        String buttonHoverStyle = "-fx-background-color: #181c1f; -fx-text-fill: #ffffff; -fx-padding: 5; -fx-border-color: #181c1f; -fx-border-width: 1; -fx-border-radius: 5;";

        upvoteButton.setStyle(buttonStyle);
        downvoteButton.setStyle(buttonStyle);
        commentButton.setStyle(buttonStyle);

        Label voteCount = new Label(String.valueOf(post.getTotalReaction()));
        voteCount.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff;");
        voteTempBox.getChildren().addAll(upvoteButton, voteCount, downvoteButton, commentButton);
        voteTempBox.setPrefWidth(85);
        voteTempBox.setStyle("-fx-alignment:center;");
        voteBox.getChildren().add(voteTempBox);
        voteBox.setStyle("-fx-alignment:left;");

        postBox.setOnMouseEntered(event -> {
            postBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
            upvoteButton.setStyle(buttonHoverStyle);
            downvoteButton.setStyle(buttonHoverStyle);
            commentButton.setStyle(buttonHoverStyle);
        });
        postBox.setOnMouseExited(event -> {
            postBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
            upvoteButton.setStyle(buttonStyle);
            downvoteButton.setStyle(buttonStyle);
            commentButton.setStyle(buttonStyle);
        });

        VBox contentBox = new VBox();
        Label postTitle = new Label("Post Title");
        postTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
        Label postContent = new Label(post.getPostContent());
        postContent.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffffff;");
        postContent.setWrapText(true);

        HBox authorDateBox = new HBox();
        Label postAuthor = new Label(MainStorage.getUsersIMap().get(post.getUserID()).getName());
        postAuthor.setStyle("-fx-font-size: 12px; -fx-text-fill: #999999;");
        Label postDate = new Label(post.getCreationTime());
        postDate.setStyle("-fx-font-size: 12px; -fx-text-fill: #999999;");
        authorDateBox.getChildren().addAll(postAuthor, new Label(" | "), postDate);
        authorDateBox.setStyle("-fx-spacing: 5;");

        contentBox.getChildren().addAll(postTitle, authorDateBox,postContent, voteBox);
        contentBox.setStyle("-fx-padding: 5;");

        postBox.getChildren().add(contentBox);

        return postBox;
    }
}