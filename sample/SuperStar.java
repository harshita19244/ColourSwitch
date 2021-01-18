package sample;

import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SuperStar extends Star{

    public SuperStar(float y,boolean flag) throws FileNotFoundException {
        super(y,flag);
    }

    public SuperStar(float y, boolean flag, float x1, float y1) throws FileNotFoundException {
        super(y, flag, x1, y1);
    }

    @Override
    public void createStar(float y,boolean flag) throws FileNotFoundException {
        FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\superStar.png");
        Image image = new Image(input);
        s = new Circle(15, Color.BLACK);
        imageView = new ImageView(image);
        s.setCenterX(290);
        imageView.setFitWidth(40.0);
        imageView.setFitHeight(50.0);
        s.setCenterY(0);
        imageView.setLayoutX(getMyPosition().getX());
        imageView.setLayoutY(getMyPosition().getY());
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
    public synchronized int checkCollision(Ball ball){
        Shape intersect = Shape.intersect(Ball.ball, (this).s);
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            collided++;
            return 5;
        }
        return 0;
    }

}
