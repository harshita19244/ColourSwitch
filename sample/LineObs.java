package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class LineObs extends Obstacle{
    private transient HBox v;
    private transient HBox v2;
    private transient Rectangle[] rects;
    private transient Rectangle[] rects2;
    public LineObs(){
        v = new HBox();
        v2 = new HBox();
        setMyPosition(150.0f, 70.0f);
        createObsRect();
    }

    public LineObs(float x, float y){
        v = new HBox();
        v2 = new HBox();
        setMyPosition(x,y);
        createObsRect();
    }

    public LineObs(float y){
        setMyPosition(150.0f, 100.0f);
        createObsRect();
    }

    private void createObsRect(){
        rects = new Rectangle[4];
        rects2= new Rectangle[4];
        for(int i=0;i<4;i++){
            rects[i]=new Rectangle();
            rects[i].setHeight(20.0);
            rects[i].setWidth(150.0);
            rects[i].setFill(colourMap.get(i+1));
            rects[i].setLayoutX(getMyPosition().getX()*i);
            rects[i].setLayoutY(getMyPosition().getY()*i);
            rects2[i] = new Rectangle();
            rects2[i].setHeight(20.0);
            rects2[i].setWidth(150.0);
            rects2[i].setFill(colourMap.get(i+1));
            rects2[i].setLayoutX(getMyPosition().getX()*i);
            rects2[i].setLayoutY(getMyPosition().getY()*i);
        }
        v.getChildren().addAll(rects[0],rects[1],rects[2],rects[3]);
        v2.getChildren().addAll(rects2[0],rects2[1],rects2[2],rects2[3]);
        v2.setLayoutY(70);
        translateRight(v2);
        translateLeft(v);
    }

    public void translateLeft(HBox v){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(5000),v);
        translateTransition.setFromX(600);
        translateTransition.setToX(-600);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.play();
    }

    private void translateRight(HBox v){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(10000),v);
        translateTransition.setFromX(-600);
        translateTransition.setToX(600);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.setCycleCount(Animation.INDEFINITE);
        translateTransition.play();
    }

    public double pos(){
        return v.getTranslateY();
    }

    @Override
    public Node retGrp(){
        return v;
    }

    public Node retGrp2(){return v2;}

    @Override
    public Shape[] getShapes(){
        return rects;
    }

    @Override
    public void checkCollision(Ball ball) {
        for (int i = 0; i < 4; i++) {
            Shape intersect = Shape.intersect(ball.ball, rects[i]);
            Shape intersect2 = Shape.intersect(ball.ball, rects2[i]);
            if (intersect.getBoundsInLocal().getWidth() != -1) {
                if(!rects[i].getFill().equals(ball.getMyColour())){
                    Game.getInstance().setOver(true);
                }
                break;
            }
            if (intersect2.getBoundsInLocal().getWidth() != -1) {
                if(!rects2[i].getFill().equals(ball.getMyColour())){
                    Game.getInstance().setOver(true);
                }
                break;
            }
        }
    }

}
