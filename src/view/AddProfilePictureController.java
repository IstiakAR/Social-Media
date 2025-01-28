package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.function.Consumer;

import database.DatabaseInsert;

public class AddProfilePictureController {
    
    private Consumer<Void> onCloseCallback;
    private static File selectedFile;

    @FXML
    private ImageView profilepic;
    
    @FXML
    public void handleCancel(ActionEvent event) {
        closeDialog();
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
    
    public void handlesetprofilepic(ActionEvent e){
        DatabaseInsert.addProfilePicture(LoginController.userID, selectedFile);
    }

    public void setOnCloseCallback(Consumer<Void> onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }
    
    private void closeDialog() {
        Stage stage = (Stage) profilepic.getScene().getWindow();
        stage.close();
        if (onCloseCallback != null) {
            onCloseCallback.accept(null);
        }
    }
}
