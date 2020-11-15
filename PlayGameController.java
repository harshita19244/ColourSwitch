package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.io.IOException;

public class PlayGameController {

    @FXML
    private AnchorPane PlayGamePg;

    @FXML
    void pauseGame() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("PausePg.fxml"));
        PlayGamePg.getChildren().setAll(pane);
    }

    public static void makeBall(AnchorPane canvas) {
        Circle ball = new Circle(10, Color.RED);
        ball.relocate(301, 616);
        Path path = new Path();
        MoveTo moveTo = new MoveTo(301, 616);
        LineTo line1 = new LineTo(301, ball.getCenterY()+20);
        LineTo line2 = new LineTo(301, ball.getCenterY()-20);
        path.getElements().add(moveTo);
        path.getElements().addAll(line1, line2);
        //Creating the path transition
        PathTransition pathTransition = new PathTransition();

        //Setting the duration of the transition
        pathTransition.setDuration(Duration.millis(1000));

        //Setting the node for the transition
        pathTransition.setNode(ball);

        //Setting the path for the transition
        pathTransition.setPath(path);

        //Setting the orientation of the path
//        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        //Setting the cycle count for the transition
        pathTransition.setCycleCount(50);

        //Setting auto reverse value to true
        pathTransition.setAutoReverse(false);
        canvas.getChildren().add(ball);
        pathTransition.play();
    }

}
