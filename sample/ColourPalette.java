package sample;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class ColourPalette extends GameComponents {
    private transient Group grp;
    private static ImageView imageView;
    private transient Circle c;
    private boolean flag2;
    private String colours;

    protected static HashMap<Integer, Color> colourMap;

    protected transient static ArrayList<Integer> shuffler;

    public ColourPalette() throws FileNotFoundException {
        grp = new Group();
        colours = new String();
        flag2 = false;
        shuffler = new ArrayList<>(4);
        shuffler.add(1);
        shuffler.add(2);
        shuffler.add(3);
        shuffler.add(4);
        makeColourPalette(0,false);
    }

    public ColourPalette(float y, Color c1, Color c2) throws FileNotFoundException {
        grp = new Group();
        colours = new String();
        shuffler = new ArrayList<>(4);
        shuffler.add(1);
        shuffler.add(2);
        shuffler.add(3);
        shuffler.add(4);
        flag2 = false;
        chooser();
        colourMap = new HashMap<>();
        colourMap.put(1, c1);
        colourMap.put(2, c2);
        makeColourPalette(y,true);
    }

    private ArrayList<Integer> chooser() {
        Collections.shuffle(shuffler);
        return shuffler;
    }

    public void setColours() {
        chooser();
        colourMap = new HashMap<>();
        colourMap.put(shuffler.get(0), Color.YELLOW);
        colourMap.put(shuffler.get(1), Color.rgb(17, 231, 243));
        colourMap.put(shuffler.get(2), Color.rgb(100, 33, 204));
        colourMap.put(shuffler.get(3), Color.rgb(243, 17, 130));
    }

    private void makeColourPalette(float y,boolean flag) throws FileNotFoundException {
        if(y!=5.5){
            setColours();
        }
        FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\palette.png");
        Image image = new Image(input);
        imageView = new ImageView(image);
        imageView.setFitWidth(40.0);
        imageView.setFitHeight(39.0);
        imageView.setLayoutX(271.0f);
        c = new Circle(18, Color.BLACK);
        c.setCenterX(290);
        if (flag) {
            c.setCenterY(y/2+180);
            imageView.setLayoutY(y/2+160);
        } else {
            c.setCenterY(390.0f);
            imageView.setLayoutY(370.0f);
        }
        grp.getChildren().addAll(c, imageView);
        imageView.setPreserveRatio(true);
    }

    public String getColour() {
        return "col";
    }

    public Circle circ() {
        return c;
    }

    @Override
    public String toString() {
        return colours;
    }

    @Override
    public Node retGrp() {
        return grp;
    }

    public void checkCollision(Ball ball) {
        Shape intersect = Shape.intersect(Ball.ball, (this).c);
        if (intersect.getBoundsInLocal().getWidth() != -1 && !flag2) {
            flag2 = true;
            if (ball.ball.getFill().equals(colourMap.get(1))) {
                ball.setMyColour(colourMap.get(2));
                colours = "" + colourMap.get(2);
            } else {
                ball.setMyColour(colourMap.get(1));
                colours = "" + colourMap.get(1);
            }
        }
    }

    public Group rg() {
        return grp;
    }
}
