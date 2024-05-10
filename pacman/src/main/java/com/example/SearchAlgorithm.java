package com.example;

public interface SearchAlgorithm {
    public Direction search(Character ghost, Entity[][] map, int goalX, int goalY);
}
