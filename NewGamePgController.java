package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NewGamePgController {
    @FXML
    private AnchorPane NewGamePg;

    @FXML
    private TextField textfield;

    @FXML
    void goToMainMenu() throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("FrontPg.fxml"));
        NewGamePg.getChildren().setAll(pane);
    }

    @FXML
    void getText(){
        textfield.getText();
        System.out.println("You entered " + textfield.getText());
    }

    @FXML
    void playGameDemo() throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("PlayGame.fxml"));
        PlayGameController.makeBall(pane);
        NewGamePg.getChildren().setAll(pane);
    }
}
