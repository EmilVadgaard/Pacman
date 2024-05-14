package com.example;

import java.util.Random;

public class NormalGhostState implements GhostState {
    private Ruleset ruleset;
    private SearchAlgorithm bfs;
    private Random numberGenerator;

    public NormalGhostState(Ruleset ruleset) {
        this.ruleset = ruleset;
        this.bfs = new BreadthFirstSearch(ruleset);
        this.numberGenerator = new Random();
    }

    public boolean canBeEaten() {
        return false;
    }

    public boolean hasCollision() {
        return true;
    }

    public Direction nextDirection(int posX, int posY, int goalX, int goalY, int homeX, int homeY, int intelligence,
                                   Direction currentDirection) {

        Direction direction = null;
        switch(numberGenerator.nextInt(intelligence)) {
            case 0:
                if (ruleset.nextPosition(posX, posY, Direction.north) != null && !is180(currentDirection, Direction.north)) {
                    direction = Direction.north;
                }
                break;
            case 1:
                if (ruleset.nextPosition(posX, posY, Direction.west) != null && !is180(currentDirection, Direction.west)) {
                    direction = Direction.west;
                }
                break;
            case 2:
                if (ruleset.nextPosition(posX, posY, Direction.east) != null && !is180(currentDirection, Direction.east)) {
                    direction = Direction.east;
                }
                break;
            case 3:
                if (ruleset.nextPosition(posX, posY, Direction.south) != null && !is180(currentDirection, Direction.south)) {
                    direction = Direction.south;
                }
                break;
            default:
                direction = bfs.search(posX, posY, goalX, goalY);
                if (is180(currentDirection, direction)) {
                    direction = null;
                }
                break;
        }
        if (direction == null) {
            return nextDirection(posX, posY, goalX, goalY, homeX, homeY, intelligence, currentDirection);
        } else {
            return direction;
        }
    }

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
