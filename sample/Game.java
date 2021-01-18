package sample;

import javafx.scene.layout.AnchorPane;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements Serializable {

    private String game;
    private transient int ballflag;
    private transient int addflag;
    private ArrayList<Player> myPlayers;
    private Player currPlayer;
    private volatile int highScore;
    private volatile int starCounter;
    private boolean enoughstars;
    private Ball ball;
    private CopyOnWriteArrayList<Obstacle> obstacle;
    private ArrayList<GameComponents> myComps;
    private CopyOnWriteArrayList<Star> myStars;
    private ArrayList<ColourPalette> mypalette;
    private volatile Score score;
    private boolean over;
    private static final long serialVersionUID = 42L;
    private ArrayList<Game> myGames;
    private volatile static Game myGame;
    private boolean flag;
    private transient AnchorPane myPane;
    private boolean hasbeenPaused;


    private Game() {
        myPane = new AnchorPane();
        myGames = new ArrayList<>();
        obstacle = new CopyOnWriteArrayList<>();
        mypalette = new ArrayList<>();
        myStars = new CopyOnWriteArrayList<>();
        myPlayers = new ArrayList<>();
        currPlayer = new Player();
        setAddflag(0);
        setBallflag(0);
        ShopPgController.setBallFlag(0);
        ShopPgController.setAddonFlag(0);
        highScore=0;
        starCounter=0;
        flag = false;
        over = false;
        setEnoughstars(false);
    }

    public int getAddflag() {
        return addflag;
    }

    public boolean isHasbeenPaused() {
        return hasbeenPaused;
    }

    public void setEnoughstars(boolean enoughstars) {
        this.enoughstars = enoughstars;
    }

    public boolean isEnoughstars() {
        return enoughstars;
    }

    public void setHasbeenPaused(boolean hasbeenPaused) {
        this.hasbeenPaused = hasbeenPaused;
    }

    public int getBallflag() {
        return ballflag;
    }

    public void setBallflag(int ballflag) {
        this.ballflag = ballflag;
    }

    public void setAddflag(int addflag) {
        this.addflag = addflag;
    }

    public int getStarCounter() {
        return starCounter;
    }

    public synchronized static Game getInstance() {
        if (myGame == null) {
            myGame = new Game();
        }
        return myGame;
    }

    public ArrayList<Player> getMyPlayers() {
        return myPlayers;
    }

    public CopyOnWriteArrayList<Star> getMyStars() {
        return myStars;
    }

    public int getHighScore(){
        return highScore;
    }

    public void setHighScore(){
        for(Player p:myPlayers){
            if(p.getBestScore() > highScore){
                highScore = p.getBestScore();
            }
            setStarCounter();
        }
    }

    public void setStarCounter(){
        starCounter = 0;
        for(Player p:myPlayers){
            starCounter += p.getBestScore();
        }
    }

    public Player getCurrPlayer(){
        return currPlayer;
    }

    public void setCurrPlayer(Player pp){
        currPlayer = pp;
        myPlayers.add(currPlayer);
    }

    public void setMyPlayers(ArrayList<Player> pp){
        myPlayers = pp;
    }

    public void setStarCounter(int starCounter) {
        this.starCounter = starCounter;
    }

    public void setMyStars(CopyOnWriteArrayList<Star> myStars) {
        this.myStars = myStars;
    }

    public Ball getBall() {
        return ball;
    }

    public ArrayList<ColourPalette> getMypalette() {
        return mypalette;
    }

    public void setBall(Ball b) {
        ball = b;
    }

    public CopyOnWriteArrayList<Obstacle> getObstacle() {
        return obstacle;
    }

    public void setObstacle(CopyOnWriteArrayList<Obstacle> obs) {
        obstacle = obs;
    }

    public ArrayList<GameComponents> getMyComps() {
        return myComps;
    }

    public void setMyComps(ArrayList<GameComponents> myComps) {
        this.myComps = myComps;
    }

    public synchronized Score getScore() {
        return score;
    }

    public synchronized void setScore(Score score) {
        this.score = score;
    }

    public synchronized void setGame(String str) {
        game = str;
    }

    public synchronized String getGame() {
        return game;
    }

    public boolean getOver() {
        return over;
    }

    public void setOver(boolean flag) {
        over = flag;
    }

    public void serialize(String filename) throws IOException {
        ObjectOutputStream out = null;
        FileOutputStream fil = null;
        try {
            fil = new FileOutputStream(filename);
            out = new ObjectOutputStream(fil);
            out.writeObject(this);
        } finally {
            assert out != null;
            out.close();
            fil.close();
        }
    }

    public void deserialize(String filename) throws ClassNotFoundException, IOException, ClassCastException {
        ObjectInputStream in;
        FileInputStream fil = null;
        try {
            fil = new FileInputStream(filename);
            in = new ObjectInputStream(fil);
            myGame = (Game) in.readObject();
            in.close();
        } catch (NullPointerException e) {
            myGame = Game.getInstance();
        } catch (FileNotFoundException e) {
            myGame = Game.getInstance();
        }
        catch(Exception e){
            myGame=Game.getInstance();
        } finally {
            if (fil != null)
                fil.close();
        }
    }

    public void ballSetter() throws FileNotFoundException {
        if(isEnoughstars()){
            ball.upgradeBall();
        }
    }

}
