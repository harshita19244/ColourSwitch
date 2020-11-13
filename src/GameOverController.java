package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.IOException;


public class GameOverController {
    @FXML
    private AnchorPane GameOver;

    @FXML
    void goToMainMenu() throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("new.fxml"));
        GameOver.getChildren().setAll(pane);
    }

    @FXML
    void UseStars() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("popup.fxml"));
        Stage x=new Stage();
        x.setTitle("Use Stars?");
        x.setScene(new Scene(root, 300, 200));
        x.show();
    }

    @FXML
    void demo() throws IOException {
        VBox v=new VBox();
        Stage x=new Stage();
        x.setTitle("Game demo");
        FileInputStream input = new FileInputStream("C:/Users/USer/IdeaProjects/colourswitch/src/assets/demo.gif");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(348.0);
        imageView.setFitHeight(600.0);
        v.getChildren().addAll(imageView);
        Scene scene1=new Scene(v,348,600);
        x.setScene(scene1);
        x.show();
    }

    @FXML
    void ResumeYes() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Stage x=new Stage();
        x.setTitle("Resume game");
        x.setScene(new Scene(root, 300, 200));
        x.show();
    }

    @FXML
    void ResumeNo() throws IOException{

    }

    @FXML
    void leaderboard() throws IOException{
        VBox v=new VBox();
        Stage x=new Stage();
        Label l=new Label("Top Scores");
        l.setLayoutY(20);
        v.getChildren().addAll(l);
        Scene s1=new Scene(v,348,600,Color.BLACK);
        x.setScene(s1);
        x.setTitle("Leaderboard");
        x.show();
    }
}
