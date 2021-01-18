package sample;

import javafx.scene.Group;
import javafx.scene.Node;

public class Score extends GameComponents {

    private int currentScore;

    public Score(){
        currentScore = 0;
    }

    public int getCurrentScore(){
        return currentScore;
    }

    public void setCurrentScore(int score){
        currentScore = score;
    }

    @Override
    public String toString() {
        return ""+currentScore;
    }

    @Override
    public Node retGrp() {
        return null;
    }

    public Group rg(){
        return null;
    }
}
