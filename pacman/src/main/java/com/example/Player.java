package com.example;

public class Player extends Character {

    public Player(int posX, int posY, int speed) {
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.currentDirection = Direction.north;
    }
}