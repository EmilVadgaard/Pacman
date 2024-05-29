package com.example;

/**
* An instance of a character.
*/
public abstract class Character {
    protected int speed;
    protected int posX;
    protected int posY;
    protected Direction currentDirection;

    /**
    * Moves the character one space, corresponding to it current direction.
    */
    public void move() {
        switch (currentDirection) {
            case north:
                posY--;
                break;
            case west:
                posX--;
                break;
            case east:
                posX++;
                break;
            case south:
                posY++;
                break;
        }
    }

    /**
    * Changes the current direction to the desired direction.
    * @param desiredDirection Desired direction.
    */
    public void switchDirection(Direction desiredDirection){
        this.currentDirection = desiredDirection;
    }

    /**
    * Returns true if the character has collision.
    */
    public abstract boolean hasCollision();
    
    /**
    * Returns the characters current direction.
    */
    public Direction getDirection(){
        return this.currentDirection;
    }

    /**
    * Returns X coordinate.
    */
    public int getPosX() {
        return this.posX;
    }

    /**
    * Returns Y coordinate.
    */
    public int getPosY() {
        return this.posY;
    }

    /**
    * Sets the X and Y coordinates.
    * @param x X-value position.
    * @param y Y-value position.
    */
    public void setPos(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    /**
    * Returns the speed.
    */
    public int getSpeed() {
        return this.speed;
    }

    /**
    * Sets the speed.
    * @param speed
    */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

}