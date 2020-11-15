package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoadGamePgController {
    @FXML
    private AnchorPane loadGamePg;

    @FXML
    void goToMainMenu() throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("FrontPg.fxml"));
        loadGamePg.getChildren().setAll(pane);
    }
}
