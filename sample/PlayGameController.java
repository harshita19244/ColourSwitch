package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayGameController {
    private Ball b;
    private String name;
    private ArrayList<GameComponents> gc;
    private boolean flag=false;
    private AnchorPane pane;

    @FXML
    private AnchorPane PlayGamePg;
    @FXML
    private Label ScoreLabel;

    public PlayGameController() {
        gc = new ArrayList<>();
        name = "Guest";
    }

    @FXML
    public void initialize() throws IOException {
        pane=PlayGamePg;
        playGameDemo();

    }


    @FXML
    public void pauseGame() throws IOException {
        flag=true;
        for(GameComponents g: gc){
            g.getTimeline().pause();
        }
        for(Obstacle o : Game.getInstance().getObstacle()){
            o.setMyPosition(o.getMyPosition().getX(), (float)o.retGrp().getTranslateY());
        }
        for(Star s: Game.getInstance().getMyStars()){
            if(s instanceof SuperStar)
                s.setMyPosition(s.getMyPosition().getX(), (float)((SuperStar) s).getTranslate());
        }
        Game.getInstance().setHighScore();
        String filename = "Database\\";
        filename = filename.concat("temp");
        filename = filename.concat(".txt");
        Game.getInstance().serialize(filename);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("PausePg.fxml"));
        PlayGamePg.getChildren().setAll(pane);
    }

    public void gameOver() throws IOException {
        Label l=new Label("Game Over");
        l.setTextFill(Color.WHITE);
        l.setFont(Font.font("Verdana", FontWeight.BOLD,30));
        l.setLayoutX(200);
        l.setLayoutY(200);
        PlayGamePg.setOpacity(4);
        PlayGamePg.getChildren().add(l);
        Game.getInstance().getCurrPlayer().setBestScore(Game.getInstance().getScore().getCurrentScore());
        if(!Game.getInstance().isHasbeenPaused()){
            Game.getInstance().setHighScore();
        }
        flag=true;
        for(double i=0;i<199999;i++){
        }
        AnchorPane pane = FXMLLoader.load(getClass().getResource("GameOverPg.fxml"));
        PlayGamePg.getChildren().setAll(pane);
    }


    public void updateScoreLabel() {
        ScoreLabel.setText(Game.getInstance().getScore().toString());
    }

    public void playGameDemo() throws IOException {
        Game.getInstance().setScore(new Score());
        Group grp = new Group();
        Game.getInstance().setMyComps(gc);
        GameComponents g=new Obstacle();
        CircleObs c1=new CircleObs();gc.add(c1);
        LineObs l1=new LineObs();
        CrossObs cr1=new CrossObs();gc.add(cr1);
        ColourPalette cp=new ColourPalette();gc.add(cp);
        grp.getChildren().addAll(c1.retGrp(),l1.retGrp2(),l1.retGrp(),cr1.retGrp(),cp.retGrp());
        Game.getInstance().getMypalette().add(cp);
        pane.getChildren().addAll(grp);gc.add(l1);
        Game.getInstance().getObstacle().add(c1);
        Game.getInstance().getObstacle().add(l1);
        Game.getInstance().getObstacle().add(cr1);
        b=new Ball(pane);
        Ball.ball.toFront();
        Game.getInstance().setBall(b);
        Timer timer = new java.util.Timer();
        Random rand=new Random();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    updateScoreLabel();
                    if(b.moveUp(0)==1){
                       g.translate2(grp);
                    }
                    //UNCOMMEN______________________________________________________________________
                    if(b.moveUp(0) > 1) {
                        for (Obstacle o : Game.getInstance().getObstacle()) {
                            try {
                                o.checkCollision(b);
                            } catch (FileNotFoundException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            }
                        }
                    }
                    if(Game.getInstance().getOver()) {
                        timer.cancel();
                        try {
                            gameOver();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            };
        }, 0, 1000);
        Timer timer2 = new java.util.Timer();
        timer2.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    if(!flag){
                            int need=rand.nextInt(8);
                            GameComponents g= null;
                            boolean lineflag=false;
                            try {
                                g = GameComponents.createComp(need);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            if(g instanceof LineObs){
                                lineflag=true;
                            }
                            else if(g instanceof Obstacle){
                                Game.getInstance().getObstacle().add((Obstacle) g);
                            }
                            else if(g instanceof SuperStar){
                                Game.getInstance().getMyStars().add((SuperStar)g);
                            }

                            if (gc.size()!=4){
                                if(lineflag){
                                    pane.getChildren().add(g.retGrp2());
                                    g.translate2(g.retGrp2());
                                }
                                else{
                                    pane.getChildren().add(g.retGrp());
                                    g.translate2(g.retGrp());
                                }
                            }
                            gc.add(g);
                    }
                    } );
            };
        }, 0, 10000);
    }

}





