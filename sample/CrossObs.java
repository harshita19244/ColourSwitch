package sample;

import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class CrossObs extends Obstacle {
    private transient Rectangle rect[];

    public CrossObs() throws FileNotFoundException {
        rect = new Rectangle[4];
        setMyPosition(137.0f, 160.0f);
        createCrossObs();
    }

    public CrossObs(float x,float y) throws FileNotFoundException{
        rect = new Rectangle[4];
        setMyPosition(x,y);
        createCrossObs();
    }

    public CrossObs(float y) throws FileNotFoundException {
        rect = new Rectangle[4];
        setMyPosition(237.0f, 0.0f);
        if(y == 0){
            setMyPosition(getMyPosition().getX()+100,getMyPosition().getY()); }
        else if(y == 1){
            setMyPosition(getMyPosition().getX()-100,getMyPosition().getY());
        }
        createCrossObs();
    }

    private void createCrossObs() throws FileNotFoundException {
        double ht, wt,diff;
        ht = 100;
        wt = 15;
        diff=Math.ceil(ht/20)*10;
        for(int i = 0; i<4; i++){
            rect[i] = new Rectangle();
            rect[i].setArcWidth(100);
            rect[i].setArcHeight(20);
            rect[i].setHeight(ht);
            rect[i].setWidth(wt);
            if(i%2 == 0) {
                rect[i].setRotate(90.0f);
                rect[i].setLayoutY(getMyPosition().getY());
            }
            else{
                rect[i].setLayoutX(getMyPosition().getX()+diff);
            }
            rect[i].setFill(colourMap.get(i+1));
        }
        s=new Star(getMyPosition().getY(),true);
        rect[0].setLayoutX(getMyPosition().getX());
        rect[1].setLayoutY(getMyPosition().getY()-diff);
        rect[2].setLayoutX(getMyPosition().getX()+ht);
        rect[3].setLayoutY(getMyPosition().getY()+diff);
        s.retGrp().toBack();
        grp.getChildren().addAll(rect[0],rect[1],rect[2],rect[3]);
        grp1.getChildren().addAll(grp,s.retGrp());
        Game.getInstance().getMyStars().add(s);
        RotateTransition rotate=new RotateTransition(Duration.millis(getRotateSpeed()), grp);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setCycleCount(1000);
        rotate.play();
    }

    public double pos(){
        return grp.getTranslateY();
    }

    @Override
    public Group retGrp(){
        return grp1;
    }

    @Override
    public Shape[] getShapes(){
        return rect;
    }
}
