package view;

import java.io.ByteArrayInputStream;

import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.User;

public class FriendsProfileController {

    @FXML
    private Label profilename;
    @FXML
    private Circle profileImageview;
    @FXML
    private Text workplace;
    @FXML
    private Text education;
    @FXML
    private Text email;
    @FXML
    private Text bio;

    public void setfrienddata(User friend) {
        profilename.setText(friend.getName());
        workplace.setText(friend.getWorkplace());
        education.setText(friend.getEducation());
        email.setText(friend.getEmail());
        bio.setText(friend.getBio());

        Platform.runLater(() -> {
            profilename.setText(friend.getName());
            workplace.setText(friend.getWorkplace());
            education.setText(friend.getEducation());
            email.setText(friend.getEmail());
            bio.setText(friend.getBio());

            byte[] profilePicture = friend.getProfilePicture();
            if (profilePicture != null && profilePicture.length > 0) {
                try {
                    Image profileImage = new Image(new ByteArrayInputStream(profilePicture));
                    profileImageview.setFill(new ImagePattern(profileImage));
                } catch (Exception e) {
                    System.out.println("Error loading profile picture: " + e.getMessage());
                    profileImageview.setFill(Color.GRAY);
                }
            } else {
                profileImageview.setFill(Color.GRAY);
            }
        });
    }
}
