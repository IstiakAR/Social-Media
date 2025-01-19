package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.MainController;
import main.MainStorage;
import model.Post;
import database.DatabaseGetter;

import java.util.List;
import java.util.stream.Collectors;

public class homeController {
    @FXML
    private ScrollBar homeScrollBar;
    @FXML
    private ScrollPane homeScrollPane;
    @FXML
    private VBox postsContainer;

    @FXML
    public void initialize(){
        homeScrollBar.valueProperty().bindBidirectional(homeScrollPane.vvalueProperty());
        homeScrollBar.maxProperty().bind(homeScrollPane.vmaxProperty());
        homeScrollBar.visibleAmountProperty().bind(homeScrollPane.heightProperty().divide(postsContainer.heightProperty()));
        homeScrollBar.valueProperty().bindBidirectional(homeScrollPane.vvalueProperty());
        postsContainer.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        homeScrollPane.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        updateScrollBarVisibility();

        displayPostsLatest();
    }

    private void updateScrollBarVisibility() {
        boolean shouldShowScrollBar = postsContainer.getHeight() > homeScrollPane.getHeight();
        homeScrollBar.setVisible(shouldShowScrollBar);
    }
    public void handleProfile(ActionEvent event) {
        System.out.println("Profile clicked");
        try {
            MainController.gotoProfile();
        }
        catch(Exception e){
           e.printStackTrace();
        }
    }

    private void displayPostsLatest() {
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


    private VBox createPostBox(Post post) {
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