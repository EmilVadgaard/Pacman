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

    public void subtractLifeCounter(int n){
        lifeCounter = lifeCounter - n;
    }
    
    public void addScore(int n){
        score = score + n;
    }
}
