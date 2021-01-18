package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class LoadGamePgController {
    @FXML
    private AnchorPane loadGamePg;

    private final float myPositions[] = new float[8];
    private RadioButton myrb[] = new RadioButton[16];
    private int lastClickedIndex = -1;
    private boolean flag = false;
    private Label l;

    public void initialize() {
        l = new Label();
        Game.getInstance();
        makeLabels();
    }

    @FXML
    void goToMainMenu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("FrontPg.fxml"));
        loadGamePg.getChildren().setAll(pane);
    }


    public void makeLabels() {
        myPositions[0] = 362f;
        myPositions[1] = 391f;
        myPositions[2] = 421f;
        myPositions[3] = 452f;
        myPositions[4] = 483f;
        myPositions[5] = 513f;
        myPositions[6] = 542f;
        myPositions[7] = 570f;
        String[] corrFiles = new File("Database").list();
        String[] allFiles = new String[corrFiles.length-1];
        int j = 0;
        for(int i = 0; i< corrFiles.length; i++){
            if(!corrFiles[i].contentEquals("temp.txt")){
                allFiles[j] = corrFiles[i];
                j++;
            }
        }
        int numFiles = allFiles.length;
        ToggleGroup grp = new ToggleGroup();
        for (int i = 0; i < 16; i++) {
            myrb[i] = new RadioButton();
            if (i < numFiles) {
                myrb[i].setText(allFiles[i]);
            } else {
                myrb[i].setText("Game " + (i + 1));
            }
            myrb[i].setMnemonicParsing(false);
            myrb[i].setPrefHeight(20);
            myrb[i].setPrefWidth(245);
            myrb[i].setTextFill(Color.WHITE);
            myrb[i].setToggleGroup(grp);
            loadGamePg.getChildren().add(myrb[i]);
            if (i < 8) {
                myrb[i].setLayoutX(80);
                myrb[i].setLayoutY(myPositions[i]);
            } else {
                myrb[i].setLayoutX(350);
                myrb[i].setLayoutY(myPositions[i - 8]);
            }
            int finalI = i;
            myrb[i].setOnAction(e -> {
                lastClickedIndex = finalI;
            });
        }
    }

    @FXML
    public synchronized void playGameDemo() throws IOException, ClassNotFoundException {
        if (lastClickedIndex == -1) {
            Label errLabel = new Label();
            errLabel.setLayoutX(160);
            errLabel.setLayoutY(660);
            errLabel.setFont(Font.font("Verdana Bold"));
            errLabel.setFont(new Font(22.0));
            errLabel.setText("Choose a Game first!");
            errLabel.setTextFill(Color.WHITE);
            loadGamePg.getChildren().add(errLabel);
        } else {
            Game.getInstance().setGame(myrb[lastClickedIndex].getText().substring(0, myrb[lastClickedIndex].getText().length() - 5));
            String filename = "Database\\";
            filename = filename.concat(myrb[lastClickedIndex].getText());
            AnchorPane pane = FXMLLoader.load(getClass().getResource("PlayGame.fxml"));
            pane.getChildren().removeIf(n -> !(n instanceof Label));
            Game.getInstance().deserialize(filename); Game.getInstance().getMyComps().clear();
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
                        loadGamePg.getChildren().setAll(pane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            loadGamePg.getChildren().setAll(pane, imageView);
            Game.getInstance().getObstacle().removeIf(o -> o.getShapes() == null);
            Game.getInstance().getMyStars().removeIf(o -> o.circ() == null);
            Game.getInstance().getMypalette().removeIf(p -> p.circ() == null);
        }
    }
}