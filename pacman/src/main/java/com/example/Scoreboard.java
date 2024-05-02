package com.example;

public class Scoreboard {
    private int lifeCounter;
    private int score;

    public Scoreboard(){
        this.lifeCounter = 3;
        this.score = 0;
    }
    public int getLifeCounter() {
        return lifeCounter;
    }
    public int getScore(){
        return score;
    }

    public void setLifeCounter(int lives){
        this.lifeCounter = lives;
    }
    
    public void setScore(int score){
        this.score = score;
    }
}
