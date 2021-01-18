package sample;

import java.io.Serializable;

public class Player implements Serializable {

    private int stars;
    private volatile int bestScore;

    public Player(){
        bestScore = 0;
    }

    public int getStars(){
        return stars;
    }

    public void setStars(int s){
        stars = s;
    }

    public int getBestScore(){
        return bestScore;
    }

    public void setBestScore(int s){
        if(bestScore < s)
            bestScore = s;
    }

    @Override
    public String toString(){
        return bestScore + " " + stars;
    }

    @Override
    public boolean equals(Object obj){
        return true;
    }
}

