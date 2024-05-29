package com.example;

import java.util.Random;

/**
 * The behavior for a ghost when it has not been eaten and the game is not in powerup state.
 */
public class NormalGhostState implements GhostState {
    private Ruleset ruleset;
    private SearchAlgorithm bfs;
    private Random numberGenerator;
    private boolean veryAngry;

    /**
     * Sets up the state for ghost, according to the normal state.
     */
    public NormalGhostState(Ruleset ruleset) {
        this.ruleset = ruleset;
        this.bfs = new BreadthFirstSearch(ruleset,hasCollision());
        this.numberGenerator = new Random();
        this.veryAngry = true;
    }

    /**
     * Answers whether ghost can be eaten.
     * @return Always false
     */
    public boolean canBeEaten() {
        return false;
    }

    /**
     * Answers whether ghost has collision.
     * @return Always true
     */
    public boolean hasCollision() {
        return true;
    }

    /**
     * Tries to go towards the player, with a 4/intelligence chance to pick a random direction.
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
        if (veryAngry) {
            return initialDirection(posX, posY, playerX, playerY, intelligence);
        } else {
            return consecutiveDirection(posX, posY, playerX, playerY, intelligence, currentDirection);
        }
    }

    /*
     * Returns the direction that is directly towards the player with a 4/intelligence chance to pick randomly.
     */
    private Direction initialDirection(int posX, int posY, int playerX, int playerY, int intelligence) {
        Direction direction = null;
        switch(numberGenerator.nextInt(intelligence)) {
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
                direction = bfs.search(posX, posY, playerX, playerY);
                break;
        }
        if (ruleset.nextPosition(posX, posY, direction, hasCollision()) == null) {
            return initialDirection(posX, posY, playerX, playerY, intelligence);
        } else {
            veryAngry = false;
            return direction;
        }
    }

    /*
     * Returns the direction that is directly towards the player with a 4/intelligence chance to pick randomly.
     * Will never return a direction that is a 180 degree turn from the previous direction.
     */
    private Direction consecutiveDirection(int posX, int posY, int playerX, int playerY, int intelligence, Direction currentDirection) {
        Direction direction = null;
        switch(numberGenerator.nextInt(intelligence)) {
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
                direction = bfs.search(posX, posY, playerX, playerY);
                break;
        }

        if (ruleset.nextPosition(posX, posY, direction, hasCollision()) == null) {
            return consecutiveDirection(posX, posY, playerX, playerY, intelligence, currentDirection);
        } else if (is180(currentDirection, direction)) {
            return consecutiveDirection(posX, posY, playerX, playerY, intelligence, currentDirection);
        } 
        else {
            return direction;
        }
    }

    /*
     * Checks whether two direction are opposite of eachother.
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
