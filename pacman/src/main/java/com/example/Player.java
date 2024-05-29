package com.example;

/**
 * The player in the Pac-man game.
 */
public class Player extends Character {

    /**
     * @param posX Starting position x-value.
     * @param posY Starting position y-value.
     * @param speed The speed at which the player moves.
     */
    public Player(int posX, int posY, int speed) {
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.currentDirection = Direction.west;
    }

    /**
    * @return Always true.
    */
    public boolean hasCollision() {
        return true;
    }
}