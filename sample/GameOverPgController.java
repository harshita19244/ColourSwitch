package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;


public class GameOverPgController {

    @FXML
    private Label ScoreLabel;

    @FXML
    private Label StarLabel;

    @FXML
    private Label BestLabel;

    @FXML
    private AnchorPane GameOver;

    @FXML
    private AnchorPane Popup;

    @FXML
    public void goToMainMenu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FrontPg.fxml"));
        GameOver.getChildren().setAll(pane);
    }

    @FXML
    public void initialize() throws IOException {
        updateScores();
        updateHighScore();
        updateStarCount();

    }

    public void updateScores(){
        if(ScoreLabel != null)
            ScoreLabel.setText(Game.getInstance().getScore().toString());
    }

    public void updateHighScore(){
        if(BestLabel != null)
            BestLabel.setText(String.valueOf(Game.getInstance().getHighScore()));
    }

    public void updateStarCount(){
        if(StarLabel != null)
            StarLabel.setText(String.valueOf(Game.getInstance().getStarCounter()));

    }

    @FXML
    public void useStars() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("ResumeGamePopup.fxml"));
        GameOver.getChildren().setAll(pane);
    }

    @FXML
    public void resumeNo() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FrontPg.fxml"));
        Popup.getChildren().setAll(pane);
    }

    @FXML
    public void resumeYes() throws IOException, ClassNotFoundException {
        if(Game.getInstance().getStarCounter()>10) {
            Game.getInstance().getScore().setCurrentScore(Game.getInstance().getScore().getCurrentScore() - 10);
            Game.getInstance().setStarCounter(Game.getInstance().getStarCounter() - 10);
            Game.getInstance().setOver(false);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("PlayGame.fxml"));
            pane.getChildren().removeIf(n -> !(n instanceof Label));
            String filename = "Database\\";
            filename = filename.concat("temp.txt");
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
                    System.out.println((Arrays.toString(Game.getInstance().getObstacle().get(Game.getInstance().getObstacle().size() - 1).getShapes())));
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
                        Popup.getChildren().setAll(pane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            Popup.getChildren().setAll(pane, imageView);
            Game.getInstance().getObstacle().removeIf(o -> o.getShapes() == null);
            Game.getInstance().getMyStars().removeIf(o -> o.circ() == null);
            Game.getInstance().getMypalette().removeIf(p -> p.circ() == null);
        }
        else {
            errLabel();
        }
    }

    public void errLabel(){
        Label errLabel = new Label();
        errLabel.setLayoutX(180);
        errLabel.setLayoutY(600);
        errLabel.setFont(Font.font("Verdana Bold"));
        errLabel.setFont(new Font(28.0));
        errLabel.setText("Not enough stars!");
        errLabel.setTextFill(Color.WHITE);
        Popup.getChildren().add(errLabel);
    }

    @FXML
    public void reloadGame() throws IOException {
        Game.getInstance().setOver(false);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("PlayGame.fxml"));
        GameOver.getChildren().setAll(pane);
    }

    @FXML
    public void demo() throws IOException {
        VBox v = new VBox();
        Stage x = new Stage();
        x.setTitle("Game Demo");
        FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\demo.gif");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(348.0);
        imageView.setFitHeight(600.0);
        v.getChildren().addAll(imageView);
        Scene scene1=new Scene(v,348,600);
        x.setScene(scene1);
        x.show();
    }

    @FXML
    public void leaderboard() throws FileNotFoundException {
        Stage x = new Stage();
        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-background-color: black;");
        Label l = new Label("Beat Your HighScore!");
        l.setTranslateX(100.0);
        l.setFont(new Font("Verdana",15));
        l.setTextFill(Color.PINK);
        l.setLayoutY(210);
        Label ll = new Label();
        ll.setLayoutX(120);
        ll.setLayoutY(270);
        ll.setText(String.valueOf(Game.getInstance().getHighScore()));
        ll.setTextFill(Color.WHITE);
        ll.setFont(new Font("Verdana Bold",40));
        FileInputStream input = new FileInputStream("C:\\Users\\User\\IdeaProjects\\ColourSwitch2\\src\\assets\\logo.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(270);
        imageView.setFitHeight(270);
        imageView.setPreserveRatio(true);
        imageView.setLayoutY(20); imageView.setLayoutX(30);
        pane.getChildren().addAll(l, ll, imageView);
        Scene s1 = new Scene(pane,348,400);
        x.setScene(s1);
        x.setTitle("Leaderboard");
        x.show();
    }

}
