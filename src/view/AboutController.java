package view;
import main.MainController;
import main.MainStorage;
import model.User;
import database.DatabaseUpdate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class AboutController {
    @FXML
    private TextField Education;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField Workplace;
    @FXML
    private TextField Email;
    @FXML
    private TextArea Bio;
    @FXML
    private Circle userImage;
    User user;
    
    public void initialize() {
        rootPane.requestFocus();
        rootPane.setStyle("-fx-background-color:  #0e1113");
        user = MainStorage.getUsersIMap().get(LoginController.getUserID());
        Workplace.setText(user.getWorkplace());
        Bio.setText(user.getBio());
        Email.setText(user.getEmail());
        Education.setText(user.getEducation());
        
        Image profileImage = BaseController.loadProfilePicture(MainStorage.getUsersIMap().get(LoginController.getUserID()).getProfilePicture(), LoginController.getUserID());
        if(profileImage!=null) userImage.setFill(new ImagePattern(profileImage));
    }

    @FXML
    public void handleBack(){
        System.out.println("Back clicked");
        try {
            MainController.gotoProfile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSave(ActionEvent event) {
        String education = Education.getText();
        String workplace  = Workplace.getText();
        String email = Email.getText();
        String bio = Bio.getText();

        User user = MainStorage.getUsersIMap().get(LoginController.getUserID());

        user.setEducation(education);
        user.setWorkplace(workplace);
        user.setEmail(email);
        user.setBio(bio);
        System.out.println("Bio" + user.getBio() + ' ' + user.getEducation() + ' ' + user.getWorkplace() + ' ' + user.getEmail());
        DatabaseUpdate.updateUserDetails(user);
        try{
            MainController.gotoProfile();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
