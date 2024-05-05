package com.example;

public abstract class Character {
    int speed;
    int posX;
    int posY;
    Direction currentDirection;

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

    public void switchDirection(Direction desiredDirection){
        this.currentDirection = desiredDirection;
    }

    public Direction getDirection(){
        return this.currentDirection;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }
}