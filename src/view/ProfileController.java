package view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import database.DatabaseGetter;
import database.DatabaseUpdate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import main.MainController;
import model.User;

public class ProfileController {
    @FXML
    private Text profilename;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button changePicture;

    private User user;


    @FXML
    public void initialize() {
        user = DatabaseGetter.getUserByID(LoginController.userID);
        if (user != null) {
            profilename.setText(user.getName());

            byte[] profilePicture = user.getProfilePicture();
            System.out.println("Image found");
            if (profilePicture != null) {
                Image profileNewImage = new Image(new ByteArrayInputStream(profilePicture));
                profileImage.setImage(profileNewImage);
            }
        }
    }

    @FXML
    public void handleChangeProfilePicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(changePicture.getScene().getWindow());

        if (selectedFile != null) {
            try (InputStream inputStream = new FileInputStream(selectedFile)) {
                byte[] profilePicture = inputStream.readAllBytes();

                // Update the profile picture in the database
                user.setProfilePicture(profilePicture);
                DatabaseUpdate.updateUserProfilePicture(user.getUserID(), profilePicture);

                // Update the profile image view
                Image profileNewImage = new Image(new ByteArrayInputStream(profilePicture));
                profileImage.setImage(profileNewImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleHome(ActionEvent event) {
        System.out.println("Home clicked");
        try {
            MainController.gotoHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleMyPost(ActionEvent event){
        System.out.println("Posts clicked");
        try{
            MainController.gotoMyPost();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void handleSaved(ActionEvent event){
        System.out.println("Saved clicked");
        try{
            MainController.gotoSaved();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

