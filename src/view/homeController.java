package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import main.MainController;

public class homeController {
    @FXML
    private ScrollBar homeScrollBar;
    @FXML
    private ScrollPane homeScrollPane;
    @FXML
    private VBox contentVBox;
    @FXML
    public void initialize(){
        homeScrollBar.valueProperty().bindBidirectional(homeScrollPane.vvalueProperty());
        homeScrollBar.maxProperty().bind(homeScrollPane.vmaxProperty());
        homeScrollBar.visibleAmountProperty().bind(homeScrollPane.heightProperty().divide(contentVBox.heightProperty()));
        homeScrollBar.valueProperty().bindBidirectional(homeScrollPane.vvalueProperty());
        contentVBox.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        homeScrollPane.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        updateScrollBarVisibility();
    }

    private void updateScrollBarVisibility() {
        boolean shouldShowScrollBar = contentVBox.getHeight() > homeScrollPane.getHeight();
        homeScrollBar.setVisible(shouldShowScrollBar);
    }
    public void handleProfile(ActionEvent event) {
        System.out.println("Profile clicked");
        try {
            MainController.gotoProfile();
        }
        catch(Exception e){
           e.printStackTrace();
        }
    }
}
