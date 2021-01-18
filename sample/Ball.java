package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.io.*;

public class Ball extends GameComponents{

    private String colour;
    private int mov = 0;
    public static Circle ball;

    private transient Group e;

    public Ball(AnchorPane pane2) {
        e=new Group();
        colour = ""+Color.YELLOW;
        makeBall(pane2);

    }

    public Ball(String s, AnchorPane pane){
        e=new Group();
        colour = s;
        makeBall(pane);
    }
    @Override
    public String toString() {
        return "Ball{" +
                "colour='" + colour + '\'' + '}';
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setMyColour(Color c){
        colour = ""+c;
        ball.setFill(c);
    }

    public Color getMyColour(){
        return (Color) ball.getFill();
    }

    @Override
    public Position getMyPosition(){
        Position p = new Position((float)ball.getCenterX(), (float)ball.getCenterY());
        return p;
    }

    public synchronized void makeBall(AnchorPane canvas) {
        ball = new Circle(16.0, Color.web(colour));
        colour = ""+ball.getFill();
        ball.relocate(280, 600);
        this.setMyPosition(280, 600);
        EventHandler<MouseEvent> eventHandler = eve -> {
            ball.setCenterY(ball.getCenterY() - 20);
            e.setLayoutY(ball.getCenterY() - 20);
            ball.toFront();
            this.setMyPosition(this.getMyPosition().getX(), (float) (ball.getCenterY() - 20));
            //UNCOMMENT____________________________________________________________________________________________________
//            for(Obstacle o : Game.getInstance().getObstacle()){
//                try {
//                    o.checkCollision(this);
//                } catch (FileNotFoundException fileNotFoundException) {
//                    fileNotFoundException.printStackTrace();
//                }
//            }
            for(Star s: Game.getInstance().getMyStars()){
                    int x = s.checkCollision(this);
                    if (x != 0 && s.getCollided()==1) {
                        if(Game.getInstance().getAddflag()==4) {
                            Game.getInstance().getScore().setCurrentScore(Game.getInstance().getScore().getCurrentScore() + x*3);
                            Game.getInstance().getCurrPlayer().setBestScore(Game.getInstance().getScore().getCurrentScore());
                            break;
                        }

                        else if(Game.getInstance().getAddflag()==5){
                            Game.getInstance().getScore().setCurrentScore(Game.getInstance().getScore().getCurrentScore() + x*8);
                            Game.getInstance().getCurrPlayer().setBestScore(Game.getInstance().getScore().getCurrentScore());
                            break;

                        }
                        else{
                            Game.getInstance().getScore().setCurrentScore(Game.getInstance().getScore().getCurrentScore() + x);
                            Game.getInstance().getCurrPlayer().setBestScore(Game.getInstance().getScore().getCurrentScore());
                            break;
                        }
                    }
            }
            for(ColourPalette cp: Game.getInstance().getMypalette()){
                cp.checkCollision(this);
            }
            mov++;
        };
        ball.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        e.getChildren().add(ball);
        canvas.getChildren().addAll(e,ball);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new KeyValue(ball.layoutYProperty(), 700 - ball.getRadius())));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void errLabel(){
        Label errLabel = new Label();
        errLabel.setLayoutX(180);
        errLabel.setLayoutY(660);
        errLabel.setFont(Font.font("Verdana Bold"));
        errLabel.setFont(new Font(28.0));
        errLabel.setText("Not enough stars!");
        errLabel.setTextFill(Color.WHITE);
        e.getChildren().add(errLabel);
    }

    public void upgradeBall() throws FileNotFoundException {
        int s=Game.getInstance().getBallflag();
        if(s == 1){
            if(Game.getInstance().getStarCounter()>15){
                Game.getInstance().setStarCounter(Game.getInstance().getStarCounter()-15);
            }
            else{
                errLabel();
            }
            FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\iceball.gif");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(120);
            imageView.setFitHeight(120);
            imageView.setPreserveRatio(true);
            imageView.setLayoutY(600); imageView.setLayoutX(220);
            e.getChildren().add(imageView);
        }
        else if(s == 2){
            if(Game.getInstance().getStarCounter() > 20){
                Game.getInstance().setStarCounter(Game.getInstance().getStarCounter()-20);
            }
            else{
               errLabel();
            }
            FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\fireball.gif");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(120);
            imageView.setFitHeight(120);
            imageView.setPreserveRatio(true);
            imageView.setLayoutY(600); imageView.setLayoutX(220);
            e.getChildren().add(imageView);
        }
        else if(s == 3){
            if(Game.getInstance().getStarCounter() > 23){
                Game.getInstance().setStarCounter(Game.getInstance().getStarCounter()-23);
            }
            else{
                errLabel();
            }
            FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\pink.gif");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(190);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            imageView.setLayoutY(600); imageView.setLayoutX(220);
            e.getChildren().add(imageView);
        }
        else if(s == 4){
            if(Game.getInstance().getStarCounter()>12){
                Game.getInstance().setStarCounter(Game.getInstance().getStarCounter()-12);
            }
            else{
                errLabel();
            }
            FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\green.gif");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
            imageView.setLayoutY(600); imageView.setLayoutX(220);
            e.getChildren().add(imageView);
        }
    }

    public int moveUp(int i) {
        if(i==0){
            return mov;
        }
        return 0;
    }

    public Node retGrp(){
        return e;
    }

    public void serialize(String filename) throws IOException {
        ObjectOutputStream out = null;
        FileOutputStream fil = null;
        try {
            fil = new FileOutputStream(filename);
            out = new ObjectOutputStream (fil);
            System.out.println(this.getClass());
            out.writeObject(this);
        }
        finally {
            out.close();
            fil.close();
        }
    }

    public void deserialize(String filename) throws ClassNotFoundException, IOException{
        ObjectInputStream in = null;
        Ball b1;
        FileInputStream fil = null;
        try {
            fil = new FileInputStream(filename);
            in = new ObjectInputStream (fil);
            b1 = (Ball) in.readObject();
            in.close();
        }
        catch (FileNotFoundException e){
            b1 = new Ball(new AnchorPane());
        }
        finally {
            if(fil!=null)
                fil.close();
        }
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        try {
            os.defaultWriteObject();
            double x1 = this.getMyPosition().getX();
            double y1 = this.getMyPosition().getY();
            String c = this.getColour();
            os.writeDouble(x1);
            os.writeDouble(y1);
            os.writeUTF(getColour());
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        try {
            is.defaultReadObject();
            double x1 = (is.readDouble());
            double y1 = (is.readDouble());
            String c = is.readUTF();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Group rg(){
        return e;
    }
}
