package view;

import database.DatabaseGetter;
import database.DatabaseInsert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Message;

import java.util.List;

public class Messenger extends VBox {
    private final int userId;
    private final int friendId;
    private final VBox messageContainer = new VBox();

    public Messenger(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;

        setStyle("-fx-padding: 10; -fx-background-color:rgb(245, 14, 14);");
        setSpacing(10);

        // Message container
        messageContainer.setSpacing(5);
        messageContainer.setStyle("-fx-padding: 10; -fx-background-color:rgb(232, 232, 237);");

        // Text input for sending messages
        TextField messageInput = new TextField();
        messageInput.setPromptText("Type a message...");
        messageInput.setStyle("-fx-background-color: #ffffff; -fx-text-fill:rgb(245, 240, 240);");

        // Send button
        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");

        sendButton.setOnAction(event -> {
            String content = messageInput.getText().trim();
            if (!content.isEmpty()) {
                DatabaseInsert.sendMessage(userId, friendId, content);
                messageInput.clear();
                refreshMessages();
            }
        });

        refreshMessages();

        getChildren().addAll(messageContainer, messageInput, sendButton);
    }

    private void refreshMessages() {
        List<Message> messages = DatabaseGetter.getMessages(friendId);
        messageContainer.getChildren().clear();
        for (Message message : messages) {
            TextArea messageBubble = new TextArea(message.getContent());
            messageBubble.setEditable(false);
            messageBubble.setWrapText(true);
            messageBubble.setStyle("-fx-background-color: " +
                    (message.getSenderId() == userId ? "#007BFF;" : "#4CAF50;") +
                    " -fx-text-fill: white; -fx-padding: 5;");
            messageContainer.getChildren().add(messageBubble);
        }
    }
}