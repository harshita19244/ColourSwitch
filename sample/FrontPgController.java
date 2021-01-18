package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class FrontPgController {

    @FXML
    private AnchorPane FrontPg;

    @FXML
    void openNewGame() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("NewGamePg.fxml"));
        FrontPg.getChildren().setAll(pane);
    }

    @FXML
    void loadGame() throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("LoadGamePg.fxml"));
        FrontPg.getChildren().setAll(pane);
    }

    @FXML
    void howToPlay() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("HowToPlayPg.fxml"));
        FrontPg.getChildren().setAll(pane);
        System.out.println("Open How To Play");
    }

    @FXML
    void exitMain() {
        System.exit(0);
    }

}
