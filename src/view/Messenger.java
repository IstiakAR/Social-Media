package view;

import database.DatabaseGetter;
import database.DatabaseInsert;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Message;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Messenger extends VBox {
    private final int userId;
    private final int friendId;
    private final VBox messageContainer = new VBox();
    private final ScrollPane scrollPane = new ScrollPane();

    public Messenger(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;

        setStyle("-fx-padding: 10; -fx-background-color: rgb(240, 240, 240);");
        setSpacing(10);

        // Style for the message container
        messageContainer.setSpacing(10);
        messageContainer.setPadding(new Insets(10));
        messageContainer.setStyle("-fx-background-color: rgb(255, 255, 255);"
                + "-fx-border-color: rgb(200, 200, 200); -fx-border-radius: 5; -fx-background-radius: 5;");
        
        // Wrap messageContainer inside a ScrollPane
        scrollPane.setContent(messageContainer);
        scrollPane.setFitToWidth(true); // Makes the scrollPane adapt to the width of the message container
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        scrollPane.setPrefHeight(400);

        // Input box for sending messages
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        TextField messageInput = new TextField();
        messageInput.setPromptText("Type a message...");
        messageInput.setPrefWidth(500);
        messageInput.setStyle("-fx-background-color: #ffffff; -fx-border-color: rgb(200, 200, 200); -fx-padding: 5;");

        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-padding: 5;");
        sendButton.setOnAction(event -> {
            String content = messageInput.getText().trim();
            if (!content.isEmpty()) {
                DatabaseInsert.sendMessage(userId, friendId, content);
                messageInput.clear();
                refreshMessages();
            }
        });

        inputBox.getChildren().addAll(messageInput, sendButton);

        // Automatically refresh messages every 2 seconds
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(Messenger.this::refreshMessages);
            }
        }, 0, 1000);

        // Add components to the main layout
        getChildren().addAll(scrollPane, inputBox);
    }

    private void refreshMessages() {
        List<Message> messages = DatabaseGetter.getSentMessages(userId, friendId);
        messageContainer.getChildren().clear();
        for (Message message : messages) {
            HBox messageBox = new HBox();
            messageBox.setPadding(new Insets(5));
            messageBox.setSpacing(10);
            Text messageText = new Text(message.getContent());
            // double textWidth = messageText.getLayoutBounds().getWidth() + 20; // Adding padding
            // double textHeight = messageText.getLayoutBounds().getHeight();
    
            // // Adjust the message box width based on the text width
            // messageBox.setMinWidth(textWidth);
            // messageBox.setMaxWidth(textWidth);

            if (message.getSenderId() == userId && message.getReceiverId() == friendId) {
                // Sent message
                messageBox.setAlignment(Pos.BOTTOM_RIGHT);
                messageBox.setStyle("-fx-background-color:rgb(0, 123, 255); -fx-border-radius: 10; -fx-background-radius: 10;");
                HBox.setHgrow(messageBox, Priority.ALWAYS);
            } else if (message.getSenderId() == friendId && message.getReceiverId() == userId) {
                // Received message
                messageBox.setAlignment(Pos.CENTER_LEFT);
                messageBox.setStyle("-fx-background-color:rgb(76, 175, 79); -fx-border-radius: 10; -fx-background-radius: 10;");
                
            } else {
                System.out.println("Message does not belong to this conversation");
            }

           
            messageText.setStyle("-fx-fill: white; -fx-padding: 10;");
            messageText.wrappingWidthProperty().set(250);

            messageBox.getChildren().add(messageText);
            messageContainer.getChildren().add(messageBox);
        }

        // Scroll to the bottom of the messages
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }
}
