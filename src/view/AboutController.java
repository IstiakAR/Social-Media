package view;
import main.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AboutController {
    // Link to the TextField in the FXML file
    @FXML
    private TextField College;
    @FXML
    private AnchorPane rootPane;
    // Initialize method runs automatically after FXML loading
    public void initialize() {
        // Example: Set a default value in the College TextField
        //College.setText("Enter your college name here...");
        rootPane.requestFocus();
    }
    public void goback(){
        System.out.println("Done clicked");
        try {
            MainController.gotoProfile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleHome(){
        System.out.println("Back clicked");
        try {
            MainController.gotoHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add other methods as needed, such as event handlers
}
