package sample;

import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Star extends GameComponents{

    protected transient Group grp;
    protected transient Circle s;
    protected volatile int collided;
    protected transient static ImageView imageView;

    public Star(float y,boolean flag) throws FileNotFoundException {
        setMyPosition(270, -20);
        grp = new Group();
        collided = 0;
        createStar(y,flag);
    }

    public Star(float y,boolean flag,float x1, float y1) throws FileNotFoundException {
        setMyPosition(x1, y1);
        grp = new Group();
        collided = 0;
        createStar(y,flag);
    }

    public int getCollided(){
        return collided;
    }

    public void setCollided(int f){
        collided = f;
    }

    public void createStar(float y,boolean flag) throws FileNotFoundException {
        FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\Star.png");
        Image image = new Image(input);
        s = new Circle(15, Color.BLACK);
        imageView = new ImageView(image);
        s.setCenterX(290);
        imageView.setFitWidth(40.0);
        imageView.setFitHeight(50.0);
        if(flag){
            s.setCenterY(y/2+100);
            imageView.setLayoutX(290.0);
            imageView.setLayoutY(y/2+100);
        }
        else{
            s.setCenterY(y/2+320);
            imageView.setLayoutX(270.0);
            imageView.setLayoutY(y/2+300);
        }
        imageView.setBlendMode(BlendMode.HARD_LIGHT);
        imageView.setPreserveRatio(true);
        s.toBack();
        grp.getChildren().addAll(s,imageView);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Group retGrp(){
        return grp;
    }

    public Circle circ(){
        return s;
    }

    public synchronized int checkCollision(Ball ball){
        if(!(this instanceof SuperStar)) {
            Shape intersect = Shape.intersect(Ball.ball, (this).s);
            if (intersect.getBoundsInLocal().getWidth() != -1) {
                collided++;
                return 1;
            }
        }
        return 0;
    }
    public Group rg(){
        return grp;
    }

    public double getTranslate(){
        return grp.getTranslateY();
    }
}
