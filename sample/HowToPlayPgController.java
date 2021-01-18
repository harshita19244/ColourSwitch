package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class HowToPlayPgController {

    @FXML
    private AnchorPane HowToPlayPg;

    @FXML
    void goToMainMenu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FrontPg.fxml"));
        HowToPlayPg.getChildren().setAll(pane);
    }

}
