package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class PausePgController {

    @FXML
    private AnchorPane PausePg;

    public PausePgController(){
        Game.getInstance().setHasbeenPaused(true);
    }

    @FXML
    void goToMainMenu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FrontPg.fxml"));
        PausePg.getChildren().setAll(pane);
    }

    @FXML
    void saveGame() throws IOException {
        for(Obstacle o : Game.getInstance().getObstacle()){
            o.setMyPosition(o.getMyPosition().getX(), (float)o.retGrp().getTranslateY());
            if(o.retMyStar()!=null) {
                o.retMyStar().setMyPosition(o.retMyStar().getMyPosition().getX(), (float) (o.retMyStar()).getTranslate());
            }
        }
        for(Star s: Game.getInstance().getMyStars()){
            if(s instanceof SuperStar)
                s.setMyPosition(s.getMyPosition().getX(), (float)((SuperStar) s).getTranslate());
        }
        String filename = "Database\\";
        filename = filename.concat(Game.getInstance().getGame());
        filename = filename.concat(".txt");
        Game.getInstance().getCurrPlayer().setBestScore(Game.getInstance().getScore().getCurrentScore());
        Game.getInstance().setHighScore();
        Game.getInstance().serialize(filename);
    }

    @FXML
    void resumeButton() throws IOException, ClassNotFoundException {
        Game.getInstance().setHasbeenPaused(false);
        String filename = "Database\\";
        filename = filename.concat("temp.txt");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("PlayGame.fxml"));
        pane.getChildren().removeIf(n -> !(n instanceof Label));
        Game.getInstance().deserialize(filename);
        Game.getInstance().getMyComps().clear();
        GameComponents g = new GameComponents() {
            @Override
            public Node retGrp() {
                return null;
            }

            @Override
            public Group rg() {
                return null;
            }
        };
        Group grpp = new Group();
        for (Obstacle o : Game.getInstance().getObstacle()) {
            if (o.getMyPosition().getY() == 0.0) {
                continue;
            }
            if (o instanceof CrossObs) {
                CrossObs c = new CrossObs(o.getMyPosition().getX(), o.getMyPosition().getY());
                grpp.getChildren().add(c.retGrp());
                Game.getInstance().getObstacle().add(c);
                Game.getInstance().getMyComps().add(c);
            } else if (o instanceof LineObs) {
                LineObs l = new LineObs(o.getMyPosition().getX(), o.getMyPosition().getY());
                grpp.getChildren().add(l.retGrp());
                Game.getInstance().getObstacle().add(l);
                Game.getInstance().getMyComps().add(l);
            } else if (o instanceof CircleObs) {
                CircleObs cc = new CircleObs(o.getMyPosition().getX(), o.getMyPosition().getY());
                grpp.getChildren().add(cc.retGrp());
                Game.getInstance().getObstacle().add(cc);
                Game.getInstance().getMyComps().add(cc);
                } else if (o instanceof RectangleObs) {
                RectangleObs r = new RectangleObs(o.getMyPosition().getX(), o.getMyPosition().getY());
                Game.getInstance().getObstacle().add(r);
                Game.getInstance().getMyComps().add(r);
                grpp.getChildren().add(r.retGrp());
            } else if (o instanceof TriangleObs) {
                TriangleObs t = new TriangleObs(o.getMyPosition().getX(), o.getMyPosition().getY());
                Game.getInstance().getObstacle().add(t);
                Game.getInstance().getMyComps().add(t);
                grpp.getChildren().add(t.retGrp());
            }
        }
        for(Star s: Game.getInstance().getMyStars()){
            if(s instanceof SuperStar){
                SuperStar ss = new SuperStar(0, true, s.getMyPosition().getX(), s.getMyPosition().getY());
                Game.getInstance().getMyStars().add(ss);
                Game.getInstance().getMyComps().add(ss);
                grpp.getChildren().add(ss.retGrp());
            }
        }
        g.translate2(grpp);
        pane.getChildren().add(grpp);
        Ball b = new Ball(Game.getInstance().getBall().getColour(), pane);
        Game.getInstance().setBall(b);
        FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\paused.jpg");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setBlendMode(BlendMode.HARD_LIGHT);
        imageView.setFitWidth(60.0);
        imageView.setFitHeight(60.0);
        imageView.setPreserveRatio(true);
        imageView.setLayoutX(530.0f);
        imageView.setLayoutY(15.0f);
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("PausePg.fxml"));
                    PausePg.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        PausePg.getChildren().setAll(pane, imageView);
        Game.getInstance().getObstacle().removeIf(o -> o.getShapes() == null);
        Game.getInstance().getMyStars().removeIf(o -> o.circ() == null);
        Game.getInstance().getMypalette().removeIf(p -> p.circ() == null);
        Game.getInstance().setBallflag(ShopPgController.isBallFlag());
        Game.getInstance().setAddflag(ShopPgController.isAddonFlag());
        Game.getInstance().setEnoughstars(ShopPgController.isEnoughstars());
        Game.getInstance().ballSetter();
        if(Game.getInstance().isEnoughstars()){
            upgrader();
        }
    }

    public void errLabel(){
        Label errLabel = new Label();
        errLabel.setLayoutX(180);
        errLabel.setLayoutY(660);
        errLabel.setFont(Font.font("Verdana Bold"));
        errLabel.setFont(new Font(28.0));
        errLabel.setText("Not enough stars!");
        errLabel.setTextFill(Color.WHITE);
        PausePg.getChildren().add(errLabel);
    }

    public void upgrader() throws FileNotFoundException {
        int a = Game.getInstance().getAddflag();
        if(a == 4){
            if(Game.getInstance().getStarCounter()>20){
                Game.getInstance().setStarCounter(Game.getInstance().getStarCounter()-20);
            }
            else{
                errLabel();
            }
            FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\clover.png");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(70);
            imageView.setFitHeight(64);
            imageView.setPreserveRatio(true);
            imageView.setLayoutY(20); imageView.setLayoutX(70);
            PausePg.getChildren().add(imageView);
        }
        else if(a == 5){
            if(Game.getInstance().getStarCounter()>30){
                Game.getInstance().setStarCounter(Game.getInstance().getStarCounter()-30);
            }
            else{
                errLabel();
            }
            FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\rocket.png");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(120);
            imageView.setFitHeight(65);
            imageView.setPreserveRatio(true);
            imageView.setLayoutY(20); imageView.setLayoutX(70);
            PausePg.getChildren().add(imageView);
        }
    }

    @FXML
    void restartGame() throws IOException {
        Game.getInstance().setHasbeenPaused(false);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("PlayGame.fxml"));
        PausePg.getChildren().setAll(pane);
    }

    @FXML
    void endMenuActions() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("ShopPg.fxml"));
        PausePg.getChildren().setAll(pane);
    }
}
