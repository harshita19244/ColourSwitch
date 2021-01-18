package sample;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class CircleObs extends Obstacle {
    private transient Arc[] arcs;

    public CircleObs() throws FileNotFoundException {
        arcs = new Arc[4];
        setMyPosition(290.0f, 430.0f);
        makeArc(false);
    }

    public CircleObs(float y) throws FileNotFoundException {
        arcs = new Arc[4];
        setMyPosition(290.0f, -50.0f);
        makeArc(true);
    }

    public CircleObs(float x, float y) throws FileNotFoundException {
        arcs = new Arc[4];
        setMyPosition(x, y);
        makeArc(false);
    }

    private void makeArc(boolean flag) throws FileNotFoundException {
        double r = 80.0f;
        double ang = 90.0f;
        grp.setLayoutY(100);
        for (int i = 0; i < 4; i++) {
            arcs[i] = new Arc();
            arcs[i].setCenterX(getMyPosition().getX());
            arcs[i].setCenterY(getMyPosition().getY());
            arcs[i].setType(ArcType.OPEN);
            arcs[i].setRadiusX(r);
            arcs[i].setRadiusY(r);
            arcs[i].setLength(90.0f);
            arcs[i].setStartAngle(ang * (i));
            arcs[i].setStroke(colourMap.get(shuffler.get(i)));
            arcs[i].setStrokeWidth(10);
            arcs[i].setFill(Color.TRANSPARENT);
        }
        if (flag) {
            s = new Star(getMyPosition().getY(), flag);
        } else {
            s = new Star(getMyPosition().getY(), flag);
        }
        grp.getChildren().addAll(arcs[0], arcs[1], arcs[2], arcs[3]);
        grp1.getChildren().addAll(grp, s.retGrp());
        s.retGrp().toBack();
        Game.getInstance().getMyStars().add(s);
        RotateTransition rotate = new RotateTransition(Duration.millis(getRotateSpeed()), grp);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.play();
    }

    @Override
    public Group retGrp() {
        return grp1;
    }

    public double pos() {
        return grp.getTranslateY();
    }

    @Override
    public Shape[] getShapes() {
        return arcs;
    }

    @Override
    public void checkCollision(Ball ball){
        for (int i = 0; i < 4; i++) {
            Shape intersect2 = Shape.intersect(Ball.ball, arcs[i]);
            if (intersect2.getBoundsInLocal().getWidth() != -1) {
                if(!(this).arcs[i].getStroke().equals(ball.getMyColour())){
                    if (intersect2.getBoundsInLocal().getWidth() != -1) {
                        if ((this).getShapes()[i].getFill() != ball.getMyColour()) {
                            Game.getInstance().setOver(true);
                        }
                        break;
                    }
                }
            }
        }
    }
}