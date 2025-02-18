package view;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import database.DatabaseGetter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.User;

public class MessengerController extends FriendBaseController {

    public void initialize() {
    	displayFriendList();
    }

    public void displayFriendList() {
        var requests = DatabaseGetter.getAllfriend(LoginController.getUserID());
        mainContainer.getChildren().clear();

        for (var request : requests) {
            VBox requestBox = createFriendBox(request);
            mainContainer.getChildren().add(requestBox);
        }
    }
    @FXML
    private VBox messengerContainer;

    @SuppressWarnings("unused")
    public VBox createFriendBox(User friend) {
        VBox friendBox = new VBox();
        friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        friendBox.setPrefWidth(400);

        User user = DatabaseGetter.getUserByID(LoginController.getUserID());
        Image profileImage = loadProfilePicture(friend.getProfilePicture(), friend.getUserID());

        Circle profileImageView = new Circle(18);
        if (profileImage != null) {
            profileImageView.setFill(new ImagePattern(profileImage));
        } else {
            profileImageView.setFill(Color.DODGERBLUE);
        }

        Label friendName = new Label(friend.getName());
        friendName.setStyle("-fx-font-size: 22px; -fx-text-fill:rgb(153, 153, 153);");

        Button messageButton = new Button("Message");
        messageButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");

        messageButton.setOnAction(event -> {
            messengerContainer.getChildren().clear();

            Label senderNameLabel = new Label("Message to " + friend.getName());
            senderNameLabel.setStyle("-fx-font-size: 18px; -fx-text-fill:rgb(234, 11, 11); -fx-padding: 10;");
            messengerContainer.getChildren().add(senderNameLabel);

            Messenger messenger = new Messenger(friend.getUserID(), LoginController.getUserID());
            messengerContainer.getChildren().add(messenger);
        });

        HBox friendInfo = new HBox(10, profileImageView, new VBox(friendName));
        VBox friendContainer = new VBox(10); 
        friendContainer.getChildren().addAll(friendInfo, messageButton);
        friendBox.getChildren().add(friendContainer);

        friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
        friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));

        return friendBox;
    }
    
    public class MessageReceiver implements Runnable {
        private Socket socket;

        public MessageReceiver(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (DataInputStream input = new DataInputStream(socket.getInputStream())) {
                while (true) {
                    String message = input.readUTF();
                    Platform.runLater(() -> {
                        System.out.println("Received message: " + message);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
