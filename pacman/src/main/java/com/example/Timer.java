package com.example;

public class Timer {
    private int currentTime;
    private int maxTime;

    public Timer(int time) {
        this.maxTime = time;
        this.currentTime = time;
    }

    public void decrementTime() {
        this.currentTime--;
        
    }

    public int getTime() {
        return this.currentTime;
    }

    public void setTime(int time) {
        this.maxTime = time;
    }

    public void reset() {
        this.currentTime = this.maxTime;
    }
}