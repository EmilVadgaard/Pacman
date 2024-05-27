package com.example;

public interface GhostState {
    public boolean canBeEaten();
    public boolean hasCollision();
    public Direction nextDirection(int posX, int posY, int goalX, int goalY, int homeX, int homeY, int intelligence, Direction currentDirection);
}
