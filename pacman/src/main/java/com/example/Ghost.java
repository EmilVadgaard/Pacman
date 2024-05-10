package com.example;

public class Ghost extends Character {
    boolean sleeping;
    int intelligence;

    public Ghost(int posX, int posY, int speed, int intelligence) {
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.currentDirection = Direction.north;
        this.sleeping = true;
        this.intelligence = intelligence;
    }

    public void search(Entity[][] map) {
        
    }

    public void wake() {
        this.sleeping = false;
    }

    public void sleep() {
        this.sleeping = true;
    }

    public int getIntelligence() {
        return intelligence;
    }
}
