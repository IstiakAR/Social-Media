package view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import database.DatabaseUpdate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import main.MainController;
import main.MainStorage;
import model.User;

public class ProfileController extends Handler{
    @FXML
    private Text profilename;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button changePicture;
    @FXML
    private Circle circlepic;
    @FXML
    private Text workplace;
    @FXML
    private Text education;
    @FXML
    private Text email;
    @FXML
    private Text bio;
    @FXML
    protected Circle userImage;

    private User user;

   public String validator(String s){
        if(s==null) return " ";
        else return s;
    } 

    @FXML
    public void initialize() {
        user = MainStorage.getUsersIMap().get(LoginController.getUserID());

        if (user != null) {
            String Username = user.getName();
            String Workplace = user.getWorkplace();
            String Education = user.getEducation();
            String Email = user.getEmail();
            String Bio = user.getBio();
            System.out.println(Username + ' ' + Workplace + ' ' + Education + ' ' + Email + ' '+ Bio);

            profilename.setText(Username);
            workplace.setText(Workplace);
            education.setText(Education);
            email.setText(Email);
            bio.setText(Bio);
                       
            VBox.setMargin(profilename, new Insets(10, 0, 0, 0));
            VBox.setMargin(workplace, new Insets(10, 0, 0, 0));
            VBox.setMargin(education, new Insets(10, 0, 0, 0));
            VBox.setMargin(email, new Insets(10, 0, 0, 0));
            VBox.setMargin(bio, new Insets(40, 0, 0, 0));
            
            profilename.setStyle("-fx-font-size: 24px;");
            workplace.setStyle("-fx-font-size: 18px;");
            education.setStyle("-fx-font-size: 18px;");
            email.setStyle("-fx-font-size: 14px;");
            bio.setStyle("-fx-font-size: 14px;");

            byte[] profilePicture = user.getProfilePicture();
            
            if (profilePicture != null && profilePicture.length > 0) {
                try {
                    Image profileImage = new Image(new ByteArrayInputStream(profilePicture));
                    circlepic.setRadius(50);
                    if(profileImage != null) circlepic.setFill(new ImagePattern(profileImage));
                } catch (Exception e) {
                    System.out.println("Error loading profile picture: " + e.getMessage());
                    circlepic.setRadius(50);
                    circlepic.setFill(Color.DODGERBLUE);
                }
            } else {
                circlepic.setRadius(50);
                circlepic.setFill(Color.DODGERBLUE);
            }
        } else {
            System.out.println("User not found!");
        }
        Image im = BaseController.loadProfilePicture(user.getProfilePicture(), user.getUserID());
        if(im!=null) userImage.setFill(new ImagePattern(im));
    }


    @FXML
    public void handleChangeProfilePicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(changePicture.getScene().getWindow());

        if (selectedFile != null) {
            try (InputStream inputStream = new FileInputStream(selectedFile)) {
                byte[] profilePicture = inputStream.readAllBytes();

                user.setProfilePicture(profilePicture);
                DatabaseUpdate.updateUserProfilePicture(user.getUserID(), profilePicture);

                Image profileNewImage = new Image(new ByteArrayInputStream(profilePicture));
                profileImage.setImage(profileNewImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void handleMyPost(ActionEvent event) {
        System.out.println("Posts clicked");
        try {
            MainController.gotoMyPost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAbout(ActionEvent event) {
        System.out.println("About clicked");
        try {
            MainController.gotoAbout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddProfilePicture(ActionEvent event) {
        System.out.println("handleAddProfilePicture called.");
        try {
            MainController.showAddProfilePictureDialog(() -> {
                try {
                    MainController.gotoHomepage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}