package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.MainStorage;
import model.Post;
import model.User;

import java.util.function.Consumer;

import database.DatabaseInsert;

public class AddPostController {
    @FXML
    private TextArea postContent;

    private Consumer<Void> onCloseCallback;

    public void setOnCloseCallback(Consumer<Void> onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }
    @FXML
    public void handleSubmit(ActionEvent event) {
        String content = postContent.getText();
        if (content != null && !content.trim().isEmpty()) {
            User user = MainStorage.getUsersIMap().get(LoginController.getUserID());
            if (user != null) {
                Post newPost = new Post(content, user.getUserID());
                DatabaseInsert.insertPost(newPost);
                MainStorage.addPost(newPost);
                closeDialog();
            }
        }
    }

    @FXML
    public void handleCancel(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) postContent.getScene().getWindow();
        stage.close();
        if (onCloseCallback != null) {
            onCloseCallback.accept(null);
        }
    }
}