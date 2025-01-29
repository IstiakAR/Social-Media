package view;

import java.io.ByteArrayInputStream;

import javafx.scene.control.Label;

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
        if (friend == null) {
            System.out.println("Error: friend object is null!");
            return;
        }    
        System.out.println("Initialize called for friend's profile page: " + friend.getName());
    
        profilename.setText(friend.getName()); 
        // String Username = friend.getUsername();
        // String Workplace = friend.getWorkplace();
        // String Education = friend.getEducation();
        // String Email = friend.getEmail();
        // String Bio = friend.getBio();
        // profilename.setText(Username);
        // workplace.setText(Workplace);
        // education.setText(Education);
        // email.setText(Email);
        // bio.setText(Bio);
        @SuppressWarnings("unused")
        Image profileImage = null;
        if (friend != null) {
            byte[] profilePicture = friend.getProfilePicture();
            
            if (profilePicture != null && profilePicture.length > 0) {
                try {
                    profileImage = new Image(new ByteArrayInputStream(profilePicture));
                    profileImageview.setRadius(50);
                    profileImageview.setFill(new ImagePattern(profileImage));
                } catch (Exception e) {
                    System.out.println("Error loading profile picture: " + e.getMessage());
                    profileImage = null;
                    profileImageview.setRadius(50);
                    profileImageview.setFill(Color.GRAY);
                }
            }
            else {
                System.out.println("profilepicture = null");
            }
        }

    }
    
}
