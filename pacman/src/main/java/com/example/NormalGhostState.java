package com.example;

import java.util.Random;

public class NormalGhostState implements GhostState {
    private Ruleset ruleset;
    private SearchAlgorithm bfs;
    private Random numberGenerator;
    private boolean veryAngry;

    public NormalGhostState(Ruleset ruleset) {
        this.ruleset = ruleset;
        this.bfs = new BreadthFirstSearch(ruleset,hasCollision());
        this.numberGenerator = new Random();
        this.veryAngry = true;
    }

    public boolean canBeEaten() {
        /**
         * Always returns false.
         */
        return false;
    }

    public boolean hasCollision() {
        /**
         * Always returns true.
         */
        return true;
    }

    public Direction nextDirection(int posX, int posY, int goalX, int goalY, int homeX, int homeY, int intelligence,
                                   Direction currentDirection) {
        /**
         * Calculates the next direction for the ghost to move.
         * Tries to go towards the player, with a 4/intelligence chance to pick a random direction.
         */
        if (veryAngry) {
            return initialDirection(posX, posY, goalX, goalY, intelligence);
        } else {
            return consecutiveDirection(posX, posY, goalX, goalY, intelligence, currentDirection);
        }
    }

    private Direction initialDirection(int posX, int posY, int goalX, int goalY, int intelligence) {
        /*
         * Returns the direction that is directly towards the player with a 4/intelligence chance to pick randomly.
         */
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
                direction = bfs.search(posX, posY, goalX, goalY);
                break;
        }
        if (ruleset.nextPosition(posX, posY, direction, hasCollision()) == null) {
            return initialDirection(posX, posY, goalX, goalY, intelligence);
        } else {
            veryAngry = false;
            return direction;
        }
    }

    public Direction consecutiveDirection(int posX, int posY, int goalX, int goalY, int intelligence, Direction currentDirection) {
        /*
         * Returns the direction that is directly towards the player with a 4/intelligence chance to pick randomly.
         * Will never return a direction that is a 180 degree turn from the previous direction.
         */

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
                direction = bfs.search(posX, posY, goalX, goalY);
                break;
        }

        if (ruleset.nextPosition(posX, posY, direction, hasCollision()) == null) {
            return consecutiveDirection(posX, posY, goalX, goalY, intelligence, currentDirection);
        } else if (is180(currentDirection, direction)) {
            return consecutiveDirection(posX, posY, goalX, goalY, intelligence, currentDirection);
        } else {
            return direction;
        }
    }

    private boolean is180(Direction oldDir, Direction newDir) {
        /*
         * Checks whether two direction are opposite of eachother.
         */
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
