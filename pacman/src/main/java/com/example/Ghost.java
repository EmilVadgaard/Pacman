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

    public Direction findDirection(int goalX, int goalY, Ruleset ruleset) {
        SearchAlgorithm bfs = new BreadthFirstSearch(ruleset);
        return bfs.search(this.posX, this.posY, goalX, goalY);
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
}