package com.example;

/**
 * Behavior for a ghost when it has been eaten.
 */
public class DeadGhostState implements GhostState {
    private SearchAlgorithm bfs;

    /**
     * Sets up the state for ghost, according to the dead state.
     */
    public DeadGhostState(Ruleset ruleset) {
        this.bfs = new BreadthFirstSearch(ruleset,hasCollision());
    }

    /**
     * Answers whether ghost can be eaten
     * @return always false
     */
    public boolean canBeEaten() {    
        return false;
    }

    /**
     * Answers whether ghost has collision
     * @return always false
     */
    public boolean hasCollision() {    
        return false;
    }

    /**
     * @param posX Current x-value position.
     * @param posY Current y-value position.
     * @param playerX To x-value position.
     * @param playerY To y-value position.
     * @param homeX X-value position for home.
     * @param homeY Y-value position for home.
     * @param intelligence Value of intelligence for ghost.
     * @return The calculated next direction for the ghost to move.
     */
    public Direction nextDirection(int posX, int posY, int playerX, int playerY, int homeX, int homeY, int intelligence,
            Direction currentDirection) {
        return bfs.search(posX, posY, homeX, homeY);
    }
    
}
