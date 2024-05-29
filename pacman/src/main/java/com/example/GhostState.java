package com.example;

/**
 * Behavior for a ghost in the game of Pac-man.
 */
public interface GhostState {

    /**
     * @return Whether or not a ghost can be eaten in the current state.
     */
    public boolean canBeEaten();

    /**
     * @return Whether or not a ghost has collision in the current state.
     */
    public boolean hasCollision();
    
    /**
     * 
     * @param posX The current positions x-value
     * @param posY The current positions y-value
     * @param playerX The player positions x-value
     * @param playerY The player positions y-value
     * @param homeX The ghosts start position x-value
     * @param homeY The ghosts start position x-value
     * @param intelligence The intelligence value
     * @param currentDirection The ghosts current direction.
     * @return The direction the ghost wants to move with its current behavior.
     */
    public Direction nextDirection(int posX, int posY, int playerX, int playerY, int homeX, int homeY, int intelligence, Direction currentDirection);
}
