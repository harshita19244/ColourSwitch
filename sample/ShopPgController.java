package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.IOException;

public class ShopPgController {

    private static int ballFlag;
    private static int addonFlag;
    private static boolean enoughstars;

    public ShopPgController(){
        ballFlag = 0;
        addonFlag = 0;
    }

    public static int isBallFlag() {
        return ballFlag;
    }

    public static int isAddonFlag() {
        return addonFlag;
    }

    public static void setBallFlag(int ballFlag) {
        ShopPgController.ballFlag = ballFlag;
    }

    public static void setAddonFlag(int addonFlag) {
        ShopPgController.addonFlag = addonFlag;
    }

    public static boolean isEnoughstars(){return enoughstars;}

    public static void setEnoughStars(boolean enoughstars){ShopPgController.enoughstars=enoughstars;}

    @FXML
    private AnchorPane ShopPg;

    private RadioButton myrb[] = new RadioButton[6];
    private int lastClickedIndex;

    @FXML
    public void initialize() {
        lastClickedIndex = -1;
        makeRadio();
    }

    @FXML
    public void goToMainMenu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("PausePg.fxml"));
        ShopPg.getChildren().setAll(pane);
    }

    public void errLabel(){
        Label errLabel = new Label();
        errLabel.setLayoutX(220);
        errLabel.setLayoutY(670);
        errLabel.setFont(Font.font("Verdana Bold"));
        errLabel.setFont(new Font(16.0));
        errLabel.setText("Not enough stars!");
        errLabel.setTextFill(Color.WHITE);
        ShopPg.getChildren().add(errLabel);
    }

    public void makeRadio(){
        ToggleGroup grp = new ToggleGroup();
        String[] arr={"IceBall","Fireball","PinkFire","GreenLite","Clover","Rocket"};
        int val[]={15,20,23,12,20,30};
        for(int i=0;i<6;i++){
            myrb[i]=new RadioButton();
            myrb[i].setPrefSize(18,64);
            myrb[i].setMnemonicParsing(false);
            myrb[i].setToggleGroup(grp);
            myrb[i].setText(arr[i]);
            if(i<2){
                myrb[i].setLayoutY(320);
                if(i==1){myrb[i].setLayoutX(13);}else{myrb[i].setLayoutX(185);}
            }
            else if(i==2 || i==3){
                myrb[i].setLayoutY(505);
                if(i==2){myrb[i].setLayoutX(13);}else{myrb[i].setLayoutX(185);}
            }
            else{
                myrb[i].setLayoutX(338);
                if(i==4){myrb[i].setLayoutY(315);}else{myrb[i].setLayoutY(505);}
            }
            ShopPg.getChildren().add(myrb[i]);
            int finalI = i;
            int finalI1 = i;
            myrb[i].setOnAction(e -> {
                if(finalI <4){
                    Game.getInstance().setBallflag(finalI1);
                    ballFlag=finalI+1;
                    if(Game.getInstance().getStarCounter()<val[finalI]){
                        errLabel();
                        setEnoughStars(false);
                    }
                    else{
                        setEnoughStars(true);
                    }
                }
                else{
                    Game.getInstance().setAddflag(finalI1);
                    addonFlag=finalI;
                    if(Game.getInstance().getStarCounter()<val[finalI]){
                        errLabel();
                        setEnoughStars(false);
                    }
                    else{
                        setEnoughStars(true);
                    }
                }
                lastClickedIndex = finalI;
            });
        }
    }
}
