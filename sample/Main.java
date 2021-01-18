package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //playMusic();
        Game.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("FrontPg.fxml"));
        primaryStage.setTitle("Colour Switch");
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.show();
    }

    public void playMusic(){
        Media media = new Media(getClass().getResource("/assets/music.mp3").toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(200));
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
