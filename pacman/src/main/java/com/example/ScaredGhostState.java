package com.example;

import java.util.Random;

public class ScaredGhostState implements GhostState{
    private Ruleset ruleset;
    private SearchAlgorithm bfs;
    private Random numberGenerator;
    private boolean shocked;

    public ScaredGhostState(Ruleset ruleset) {
        this.ruleset = ruleset;
        this.bfs = new BreadthFirstSearch(ruleset);
        this.numberGenerator = new Random();
        this.shocked = true;
    }

    public boolean canBeEaten() {
        return true;
    }

    public boolean hasCollision() {
        return true;
    }

    public Direction nextDirection(int posX, int posY, int goalX, int goalY, int homeX, int homeY, int intelligence,
                                   Direction currentDirection) {
                                    
        if (shocked) {
            return initialDirection(posX, posY, goalX, goalY);
        } else {
            return consecutiveDirection(posX, posY, goalX, goalY, currentDirection, 0, intelligence);
        }
    }

    private Direction initialDirection(int posX, int posY, int goalX, int goalY) {
        Direction direction = null;
        switch(numberGenerator.nextInt(4)) {
            case 0:
                if (ruleset.nextPosition(posX, posY, Direction.north) != null) {
                    direction = Direction.north;
                }
                break;
            case 1:
                if (ruleset.nextPosition(posX, posY, Direction.west) != null) {
                    direction = Direction.west;
                }
                break;
            case 2:
                if (ruleset.nextPosition(posX, posY, Direction.east) != null) {
                    direction = Direction.east;
                }
                break;
            case 3:
                if (ruleset.nextPosition(posX, posY, Direction.south) != null) {
                    direction = Direction.south;
                }
                break;
            default:
                break;
        }
        if (direction == null || direction == bfs.search(posX, posY, goalX, goalY)) {
            return initialDirection(posX, posY, goalX, goalY);
        } else {
            shocked = false;
            return direction;
        }
    }

    private Direction consecutiveDirection(int posX, int posY, int goalX, int goalY, Direction currentDirection, 
                                           int count, int intelligence) {
        Direction direction = null;
        switch(numberGenerator.nextInt(4)) {
            case 0:
                if (ruleset.nextPosition(posX, posY, Direction.north) != null) {
                    direction = Direction.north;
                }
                break;
            case 1:
                if (ruleset.nextPosition(posX, posY, Direction.west) != null) {
                    direction = Direction.west;
                }
                break;
            case 2:
                if (ruleset.nextPosition(posX, posY, Direction.east) != null) {
                    direction = Direction.east;
                }
                break;
            case 3:
                if (ruleset.nextPosition(posX, posY, Direction.south) != null) {
                    direction = Direction.south;
                }
                break;
            default:
                break;
        }
        if (direction == bfs.search(posX, posY, goalX, goalY) && count < intelligence) {
            return consecutiveDirection(posX, posY, goalX, goalY, currentDirection, count + 1, intelligence);
        } else if (is180(currentDirection, direction) || direction == null) {
            return consecutiveDirection(posX, posY, goalX, goalY, currentDirection, count + 1, intelligence);
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
