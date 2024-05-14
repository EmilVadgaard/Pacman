package com.example;

public class Ghost extends Character {
    boolean sleeping;
    int intelligence;
    private GhostState state;
    private int homeX;
    private int homeY;

    public Ghost(int posX, int posY, int speed, int intelligence, GhostState state) {
        this.posX = posX;
        this.posY = posY;
        this.homeX = posX;
        this.homeY = posY;
        this.speed = speed;
        this.currentDirection = Direction.north;
        this.sleeping = true;
        this.intelligence = intelligence;
        this.state = state;
    }

    public Direction nextDirection(int goalX, int goalY) {
        return state.nextDirection(posX, posY, goalX, goalY, homeX, homeY, intelligence, currentDirection);
    }

    public boolean canBeEaten() {
        return state.canBeEaten();
    }

    public boolean hasCollision() {
        return state.hasCollision();
    }

    public void changeState(GhostState state) {
        this.state = state;
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

    public boolean isSleeping() {
        return sleeping;
    }

    public int getHomeX() {
        return homeX;
    }

    public int getHomeY() {
        return homeY;
    }
}