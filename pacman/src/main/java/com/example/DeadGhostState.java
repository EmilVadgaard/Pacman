package com.example;

public class DeadGhostState implements GhostState {
    Ruleset ruleset;
    SearchAlgorithm bfs;

    public DeadGhostState(Ruleset ruleset) {
        this.ruleset = ruleset;
        this.bfs = new BreadthFirstSearch(ruleset,hasCollision());
    }

    public boolean canBeEaten() {
        /**
         * Always returns false.
         */
        return false;
    }

    public boolean hasCollision() {
        /**
         * Always returns false
         */
        return false;
    }

    // In reality this method should not be run on every single step the ghost takes,
    // but rather once and have every step of the optimal path saved.
    public Direction nextDirection(int posX, int posY, int goalX, int goalY, int homeX, int homeY, int intelligence,
            Direction currentDirection) {
        /**
         * Returns the direction that is directly towards the ghost's home.
         */
        return bfs.search(posX, posY, homeX, homeY);
    }
    
}
