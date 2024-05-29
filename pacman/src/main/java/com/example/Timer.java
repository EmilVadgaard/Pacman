package com.example;

/**
 * Timer for counting.
 */
public class Timer {
    private int currentTime;
    private int maxTime;

    /**
     * Constructs a timer, starting at the specified time.
     */
    public Timer(int time) {
        this.maxTime = time;
        this.currentTime = time;
    }

    /**
     * Decrements timer by 1.
     */
    public void decrementTime() {
        if (currentTime >= 0) {
            this.currentTime--;
        }
    }

    /**
     * @return The current time.
     */
    public int getTime() {
        return this.currentTime;
    }

    /**
     * Sets the upper limit of timer to specified time.
     */
    public void setTime(int time) {
        this.maxTime = time;
    }

    /**
     * Resets timer to upper limit.
     */    
    public void reset() {
        this.currentTime = this.maxTime;
    }
}