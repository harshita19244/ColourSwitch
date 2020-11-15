package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PausePgController {

    @FXML
    private AnchorPane PausePg;

    @FXML
    void goToMainMenu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FrontPg.fxml"));
        PausePg.getChildren().setAll(pane);
    }

    @FXML
    void saveGame() {
        System.out.println("Saving Game");
    }

    @FXML
    void resumeButton() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("PlayGame.fxml"));
        PausePg.getChildren().setAll(pane);
    }

    @FXML
    void restartGame() {
        System.out.println("Restarting Game");
    }

    @FXML
    void endMenuActions(){
        System.out.println("End Menu Actions Come here");
    }

}
