package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.MainController;
public class profileController {
    private String username;
    @FXML
    private Text profilename;


    public void initialize() {
        if (LoginController.name == null) {
            profilename.setText(SignUpController.name);
        } else {
            profilename.setText(LoginController.name);
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
}

