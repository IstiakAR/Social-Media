package view;
import main.MainController;
import main.MainStorage;
import model.User;
import database.DatabaseUpdate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AboutController {
    // Link to the TextField in the FXML file
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
    
    public void initialize() {
        rootPane.requestFocus();
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

        User user = MainStorage.getUsersIMap().get(LoginController.userID);

        user.setEducation(education);
        user.setWorkplace(workplace);
        user.setEmail(email);
        user.setBio(bio);
        System.out.println("Bio" + user.getBio() + ' ' + user.getEducation() + ' ' + user.getWorkplace() + ' ' + user.getEmail());
        DatabaseUpdate.updateUserDetails(user);
    }
}
