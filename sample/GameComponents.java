package sample;


import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.io.Serializable;



abstract class GameComponents implements Serializable {

    private Position myPosition;

    private transient Timeline timeline;

    public GameComponents(){
        timeline = new Timeline();
    }

    public Position getMyPosition() {
        return myPosition;
    }

    public void setMyPosition(float x, float y) {
        this.myPosition = new Position(x, y);
    }

    public void translate2(Node g ){
        KeyFrame end = new KeyFrame(Duration.seconds(32), new KeyValue(g.translateYProperty(), 770, Interpolator.LINEAR));
        timeline.getKeyFrames().add(end);
        timeline.setCycleCount(1);
        timeline.play();
    }

    public double pos(){
        return 0.0;
    }

    public boolean equals(Object o){
        return true;
    }

    public Timeline getTimeline(){
     return timeline;
    }

    public static GameComponents createComp(int option) throws FileNotFoundException {
       if (option == 1) {
           return new RectangleObs();
       }
        else if (option == 2) {
            return new CircleObs(0);
        }
         else if (option == 3) {
            return new LineObs();
        }
         else if (option == 4) {
            return new TriangleObs();
        }
         else if (option == 5) {
            return new CrossObs(1);
        }
        else if(option == 6){
            return new SuperStar(0,true);
        }
        else if(option == 7){
           return new CrossObs(0);
       }
        else {
           return new RectangleObs();
       }
    }

    public abstract Node retGrp();

    public Node retGrp2(){
        return null;
    }

    public abstract Object rg();
}
