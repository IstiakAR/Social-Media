package view;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import database.DatabaseGetter;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.MainController;
import model.User;

public class MessengerController extends FriendBaseController {

    public void displayFriendList() {
        var requests = DatabaseGetter.getAllfriend(LoginController.userID);
        friendListContainer.getChildren().clear();

        for (var request : requests) {
            VBox requestBox = createFriendBox(request);
            friendListContainer.getChildren().add(requestBox);
        }
    }

    public VBox createFriendBox(User friend) {
        VBox friendBox = new VBox();
        friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        friendBox.setPrefWidth(400);

        Label friendName = new Label(friend.getName());
        friendName.setStyle("-fx-font-size: 22px; -fx-text-fill:rgb(169, 22, 22);");

        Label friendStatus = new Label(friend.getName());
        friendStatus.setStyle("-fx-font-size: 18px; -fx-text-fill: #999999;");

        Button messageButton = new Button("Message");
        messageButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white;");

        messageButton.setOnAction(event -> {
    
            Messenger messenger = new Messenger(friend.getUserID(), LoginController.userID);
            Stage messengerStage = new Stage();
            messengerStage.setTitle("Chat with " + friend.getName());
            messengerStage.setScene(new Scene(messenger, 400, 600)); 
            messageButton.setDisable(true);

           
            messengerStage.setOnCloseRequest(e -> messageButton.setDisable(false));
            messengerStage.show();
        });

        friendBox.getChildren().addAll(friendName, friendStatus, messageButton);
        friendBox.setOnMouseEntered(event -> friendBox.setStyle("-fx-background-color: #181c1f; -fx-padding: 10; -fx-border-color: #0e1113;"));
        friendBox.setOnMouseExited(event -> friendBox.setStyle("-fx-background-color: #0e1113; -fx-padding: 10; -fx-border-color: #0e1113;"));

        return friendBox;
    }

    public void handleHome(MouseEvent event) {
        System.out.println("Home Button clicked!");
        try {
            MainController.gotoHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
