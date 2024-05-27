package com.example;

import java.util.Random;

public class ScaredGhostState implements GhostState{
    private Ruleset ruleset;
    private SearchAlgorithm bfs;
    private Random numberGenerator;
    private boolean shocked;

    public ScaredGhostState(Ruleset ruleset) {
        this.ruleset = ruleset;
        this.bfs = new BreadthFirstSearch(ruleset,hasCollision());
        this.numberGenerator = new Random();
        this.shocked = true;
    }

    public boolean canBeEaten() {
        /**
         * Always returns true
         */
        return true;
    }

    public boolean hasCollision() {
        /**
         * Alwaus returns true
         */
        return true;
    }

    public Direction nextDirection(int posX, int posY, int goalX, int goalY, int homeX, int homeY, int intelligence,
                                   Direction currentDirection) {
        /**
         * Calculates the next direction for the ghost to move.
         * Picks a random direction but tries not to go towards the player if possible.
         */
                                    
        if (shocked) {
            return initialDirection(posX, posY, goalX, goalY);
        } else {
            return consecutiveDirection(posX, posY, goalX, goalY, currentDirection, 0, intelligence);
        }
    }

    private Direction initialDirection(int posX, int posY, int goalX, int goalY) {
        /*
         * Returns a random direction that is away from the player.
         */
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
            return initialDirection(posX, posY, goalX, goalY);
        } else if (direction == bfs.search(posX, posY, goalX, goalY)) {
            return initialDirection(posX, posY, goalX, goalY);
        } else {
            shocked = false;
            return direction;
        }
    }

    private Direction consecutiveDirection(int posX, int posY, int goalX, int goalY, Direction currentDirection, 
                                           int count, int intelligence) {
        /*
         * Returns a random direction that is away from the player if possible, but will never return a direction
         * that is a 180 degree turn from the previous direction.
         */
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
            return consecutiveDirection(posX, posY, goalX, goalY, currentDirection, count + 1, intelligence);
        } else if (direction == bfs.search(posX, posY, goalX, goalY) && count < intelligence) {
            return consecutiveDirection(posX, posY, goalX, goalY, currentDirection, count + 1, intelligence);
        } else if (is180(currentDirection, direction)) {
            return consecutiveDirection(posX, posY, goalX, goalY, currentDirection, count + 1, intelligence);
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
