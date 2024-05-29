package com.example;

/**
 * Interface for search algorithms in the grid based world.
 */
public interface SearchAlgorithm {
    /**
     * Find the initial direction of the optimal path from the start position to the goal position.
     */
    public Direction search(int startX, int startY, int goalX, int goalY);
}