package view;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class homeController {
    @FXML
    private ScrollBar homeScrollBar;
    @FXML
    private ScrollPane homeScrollPane;
    @FXML
    private VBox postsContainer;

    @FXML
    public void initialize(){
        homeScrollBar.valueProperty().bindBidirectional(homeScrollPane.vvalueProperty());
        homeScrollBar.maxProperty().bind(homeScrollPane.vmaxProperty());
        homeScrollBar.visibleAmountProperty().bind(homeScrollPane.heightProperty().divide(postsContainer.heightProperty()));
        homeScrollBar.valueProperty().bindBidirectional(homeScrollPane.vvalueProperty());
        postsContainer.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        homeScrollPane.heightProperty().addListener((obs, oldVal, newVal) -> updateScrollBarVisibility());
        updateScrollBarVisibility();
    }

    private void updateScrollBarVisibility() {
        boolean shouldShowScrollBar = postsContainer.getHeight() > homeScrollPane.getHeight();
        homeScrollBar.setVisible(shouldShowScrollBar);
    }
}
