package com.example;

import java.util.Random;

/**
* The behavior for a ghost when it has not been eaten and the game is in powerup state.
*/
public class ScaredGhostState implements GhostState{
    private Ruleset ruleset;
    private SearchAlgorithm bfs;
    private Random numberGenerator;
    private boolean shocked;

    
    /**
     * Sets up the state for ghost, according to the scared state.
     */
    public ScaredGhostState(Ruleset ruleset) {
        this.ruleset = ruleset;
        this.bfs = new BreadthFirstSearch(ruleset,hasCollision());
        this.numberGenerator = new Random();
        this.shocked = true;
    }

    /**
     * Returns whether ghost can be eaten
     * @return Always true
     */
    public boolean canBeEaten() {
        return true;
    }

    /**
     * Returns whether ghost has collision
     * @return Always true
     */
    public boolean hasCollision() {
        return true;
    }

    /**
     * @param posX Current x-value position.
     * @param posY Current y-value position.
     * @param playerX Players x-value position.
     * @param playerY Players y-value position.
     * @param homeX X-value position for home.
     * @param homeY Y-value position for home.
     * @param intelligence Value of intelligence for ghost.
     * @return The calculated next direction for the ghost to move.
     * Picks a random direction but tries not to go towards the player if possible.
     */
    public Direction nextDirection(int posX, int posY, int playerX, int playerY, int homeX, int homeY, int intelligence,
                                   Direction currentDirection) {
        if (shocked) {
            return initialDirection(posX, posY, playerX, playerY);
        } else {
            return consecutiveDirection(posX, posY, playerX, playerY, currentDirection, 0, intelligence);
        }
    }

    /*
     * Returns a random direction that is away from the player.
     */
    private Direction initialDirection(int posX, int posY, int playerX, int playerY) {
        Direction direction = null;
        switch(numberGenerator.nextInt(4)) {
            case 0:
                direction = Direction.north;
                break;
            case 1:
                direction = Direction.west;
                break;
            case 2:
                direction = Direction.east;
                break;
            case 3:
                direction = Direction.south;
                break;
            default:
                break;
        }

        if (ruleset.nextPosition(posX, posY, direction, hasCollision()) == null) {
            return initialDirection(posX, posY, playerX, playerY);
        } else if (direction == bfs.search(posX, posY, playerX, playerY)) {
            return initialDirection(posX, posY, playerX, playerY);
        } else {
            shocked = false;
            return direction;
        }
    }

    /*
     * Returns a random direction that is away from the player if possible, but will never return a direction
     * that is a 180 degree turn from the previous direction.
     */
    private Direction consecutiveDirection(int posX, int posY, int playerX, int playerY, Direction currentDirection, 
                                           int count, int intelligence) {  
        Direction direction = null;
        switch(numberGenerator.nextInt(4)) {
            case 0:
                direction = Direction.north;
                break;
            case 1:
                direction = Direction.west;
                break;
            case 2:
                direction = Direction.east;
                break;
            case 3:
                direction = Direction.south;
                break;
            default:
                break;
        }

        if (ruleset.nextPosition(posX, posY, direction, hasCollision()) == null) {
            return consecutiveDirection(posX, posY, playerX, playerY, currentDirection, count + 1, intelligence);
        } else if (direction == bfs.search(posX, posY, playerX, playerY) && count < intelligence) {
            return consecutiveDirection(posX, posY, playerX, playerY, currentDirection, count + 1, intelligence);
        } else if (is180(currentDirection, direction)) {
            return consecutiveDirection(posX, posY, playerX, playerY, currentDirection, count + 1, intelligence);
        } else {
            return direction;
        }
    }

    /*
     * Checks whether two directions are opposite of eachother.
     */
    private boolean is180(Direction oldDir, Direction newDir) {  
        switch(oldDir) {
           case north:
               return newDir == Direction.south;
           case west:
               return newDir == Direction.east;
           case east:
               return newDir == Direction.west;
           case south:
               return newDir == Direction.north;
           default:
               return false;
        }
   }
    
}
