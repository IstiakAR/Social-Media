package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.MainController;
import main.MainStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;

import database.DatabaseInsert;

public class AddProfilePictureController {
    
    private Consumer<Image> profileUpdateCallback;
    private static File selectedFile;

    @FXML
    private ImageView profilepic;
    
    @FXML
    public void handleCancel(ActionEvent event) {
        closeDialog();
        try{
            MainController.gotoProfile();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddPicture(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
        selectedFile = fileChooser.showOpenDialog(profilepic.getScene().getWindow());
        
        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
            Image image = new Image(selectedFile.toURI().toString());
            profilepic.setImage(image);
        } else {
            System.out.println("No file selected.");
        }
    }

    @FXML
    public void handlesetprofilepic(ActionEvent e) throws IOException{
        if (selectedFile != null) {
            byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
            DatabaseInsert.addProfilePicture(LoginController.userID, selectedFile);
            MainStorage.getUsersIMap().get(LoginController.userID).setProfilePicture(fileContent);
            closeDialog();
        }
    }
    public void setProfileUpdateCallback(Consumer<Image> callback) {
        this.profileUpdateCallback = callback;
    }
    
    private void closeDialog() {
        Stage stage = (Stage) profilepic.getScene().getWindow();
        stage.close();
        if (profileUpdateCallback != null) {
            profileUpdateCallback.accept(null);
        }
    }
}
