package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NewGamePgController {

    private String name;

    @FXML
    private TextField textfield;

    @FXML
    private AnchorPane NewGamePg;


    public NewGamePgController() {
        name = "Guest";
    }

    @FXML
    public void goToMainMenu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FrontPg.fxml"));
        NewGamePg.getChildren().setAll(pane);
    }

    @FXML
    public void load() throws IOException {
        Game.getInstance().setOver(false);
        if (name == null || name.length() == 0) {
            name = "Guest";
        }
        Game.getInstance().setGame(name);
        Game.getInstance().setCurrPlayer(new Player());
        AnchorPane pane = FXMLLoader.load(getClass().getResource("PlayGame.fxml"));
        NewGamePg.getChildren().setAll(pane);
    }

    @FXML
    public void getText() {
        name = textfield.getText();
    }

}
