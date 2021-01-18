package sample;

import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class TriangleObs extends Obstacle{
    private transient Rectangle[] rect;

    public TriangleObs() throws FileNotFoundException {
        rect = new Rectangle[3];
        setMyPosition(230.0f, 0.0f);
        makeTriangles();
    }

    public TriangleObs(float x,float y) throws FileNotFoundException {
        rect = new Rectangle[3];
        setMyPosition(x,y);
        makeTriangles();
    }

    private void makeTriangles() throws FileNotFoundException {
        double ht, wt;
        ht = 160;
        wt = 10;
        for(int i = 0; i<3; i++){
            rect[i] = new Rectangle();
            rect[i].setArcWidth(10);
            rect[i].setArcHeight(5);
            rect[i].setHeight(ht);
            rect[i].setWidth(wt);
            rect[i].setRotate(i*60.0f);
            rect[i].setFill(colourMap.get(i+1));
        }
        s=new Star(getMyPosition().getY(),true);
        ColourPalette c=new ColourPalette((float)5.5, colourMap.get(1), colourMap.get(2));
        s.retGrp().setLayoutY(-30);
        s.retGrp().setLayoutX(-10);
        c.retGrp().setLayoutY(45);
        rect[0].setLayoutX(getMyPosition().getX());
        rect[0].setLayoutY(getMyPosition().getY());
        rect[1].setLayoutX(getMyPosition().getX()+ht/2-wt/2);
        rect[1].setLayoutY(getMyPosition().getY()+ht/4);
        rect[2].setLayoutX(getMyPosition().getX()+ht/2-wt/2);
        rect[2].setLayoutY(getMyPosition().getY()-ht/4-wt/2);
        grp.getChildren().addAll(rect[0], rect[1], rect[2]);
        grp1.getChildren().addAll(grp,s.retGrp(),c.retGrp());
        s.retGrp().toBack();
        c.retGrp().toBack();
        Game.getInstance().getMyStars().add(s);
        Game.getInstance().getMypalette().add(c);
        RotateTransition rotate = new RotateTransition(Duration.millis(getRotateSpeed()), grp);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setCycleCount(1000);
        rotate.play();
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

    @Override
    public void checkCollision(Ball ball){
            for (int i = 0; i < 3; i++) {
                Shape intersect = Shape.intersect(ball.ball, (this).getShapes()[i]);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    if((this).getShapes()[i].getFill() != ball.getMyColour()){
                        Game.getInstance().setOver(true);
                    }
                    break;
                }
            }
        }

}
