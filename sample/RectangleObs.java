package sample;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import java.io.FileNotFoundException;

public class RectangleObs extends Obstacle {
    transient private Rectangle rect[];

    public RectangleObs() throws FileNotFoundException {
        rect = new Rectangle[4];
        setMyPosition(300.0f, -70.0f);
        makeRectangle();
    }

    public RectangleObs(float x,float y) throws FileNotFoundException{
        rect = new Rectangle[4];
        setMyPosition(x,y);
        makeRectangle();
    }

    private void makeRectangle() throws FileNotFoundException {
        double ht, wt;
        ht = 150;
        wt = 10;
        for(int i = 0; i<4; i++){
            rect[i] = new Rectangle();
            rect[i].setArcWidth(10);
            rect[i].setArcHeight(5);
            rect[i].setHeight(ht);
            rect[i].setWidth(wt);
            if(i%2 == 0) {
                rect[i].setRotate(90.0f);
                rect[i].setLayoutX(getMyPosition().getX());
            }
            else{
                rect[i].setLayoutY(getMyPosition().getY()+ht/2+wt/2);
            }
            rect[i].setFill(colourMap.get(i+1));
        }
        s=new Star(getMyPosition().getY(),true);
        s.retGrp().setLayoutY(-20);
        s.retGrp().toBack();
        rect[0].setLayoutY(getMyPosition().getY()+wt/2);
        rect[1].setLayoutX(getMyPosition().getX()+ht/2);
        rect[2].setLayoutY(getMyPosition().getY()+ht);
        rect[3].setLayoutX(getMyPosition().getX()-ht/2);
        grp.getChildren().addAll(rect[0], rect[1], rect[2], rect[3]);
        grp1.getChildren().addAll(grp, s.retGrp());
        Game.getInstance().getMyStars().add(s);
        RotateTransition rotate = new RotateTransition(Duration.millis(getRotateSpeed()), grp);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.play();
    }

    @Override
    public String toString(){
        return String.format("Coloured %s at x %f and y %f\n", rect[0].getFill(), rect[0].getX(), rect[0].getY());
    }

    @Override
    public Group retGrp(){
        return grp1;
    }

    public double pos(){
        return grp.getTranslateY();
    }

    @Override
    public Shape[] getShapes(){
        return rect;
    }


}
