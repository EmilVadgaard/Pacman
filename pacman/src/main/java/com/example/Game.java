package com.example;

import java.net.URL;
import java.io.File;
import java.util.Random;
import java.util.ArrayList;

public class Game {
    Grid grid;
    Player player;
    ArrayList<Ghost> ghosts;
    Random numberGenerator;
    
    

    public Game(String filepath) {
        URL url = this.getClass().getResource("/" + filepath);
        File map = new File(url.getPath());
        this.grid = new Grid(map);
        this.player = new Player(13, 18, 3);
        this.numberGenerator = new Random();

        this.ghosts = new ArrayList<Ghost>();
        ghosts.add(new Ghost(12,10,3,4));
        ghosts.add(new Ghost(13, 10, 3, 6));
        ghosts.add(new Ghost(14,10,3,8));
    }

    public void moveCharacter(Character character) {
        if (character.getPosX() == grid.getLengthX() - 1 && character.getDirection() == Direction.east) {
            character.setPos(0, character.getPosY());
        } else if (character.getPosX() == 0 && character.getDirection() == Direction.west) {
            character.setPos(grid.getLengthX() - 1, character.getPosY());
        } else if (character.getPosY() == 0 && character.getDirection() == Direction.north) {
            character.setPos(character.getPosX(), grid.getLengthY() - 1);
        } else if (character.getPosY() == grid.getLengthY() - 1 && character.getDirection() == Direction.south) {
            character.setPos(character.getPosX(), 0);
        } else {
            character.move();
        }
    }

    public void moveGhosts() {
        for (Ghost ghost: ghosts) {
            if (!ghost.isSleeping()) {
                Direction direction = generateGhostDirection(ghost);
                switchDirection(ghost, direction);
                moveCharacter(ghost);
            }
        }
    }

    private Direction generateGhostDirection(Ghost ghost) {
        Direction direction = null;
        switch(numberGenerator.nextInt(ghost.getIntelligence())) {
            case 0:
                if (isLegal(ghost, Direction.north) && ghost.getDirection() != Direction.south) {
                    direction = Direction.north;
                }
                break;
            case 1:
                if (isLegal(ghost, Direction.west) && ghost.getDirection() != Direction.east) {
                    direction = Direction.west;
                }
                break;
            case 2:
                if (isLegal(ghost, Direction.east) && ghost.getDirection() != Direction.west) {
                    direction = Direction.east;
                }
                break;
            case 3:
                if (isLegal(ghost, Direction.south) && ghost.getDirection() != Direction.north) {
                    direction = Direction.south;
                }
                break;
            default:
                direction = findGhostDirection(ghost);
                if (is180(ghost.getDirection(), direction)) {
                    direction = null;
                }
                break;
        }
        if (isLegal(ghost, direction)) {
            return direction;
        } else {
            return generateGhostDirection(ghost);
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

    private Direction findGhostDirection(Ghost ghost) {
        Direction desiredDirection = ghost.findDirection(player.getPosX(), player.getPosY(), this);
        return desiredDirection;
    }

    public boolean isLegal(Character character, Direction direction) {
        if (direction == null) {
            return false;
        }
        switch(direction) {
            case north:
                if (character.getPosY() == 0) {
                    return true;
                } else {
                    return !CollisionDetection.wallInFront(grid.getEntity(character.getPosX(), character.getPosY() - 1));
                }
            case west:
                if (character.getPosX() == 0) {
                    return true;
                } else {
                    return !CollisionDetection.wallInFront(grid.getEntity(character.getPosX() - 1, character.getPosY()));
                }
            case east:
                if (character.getPosX() == grid.getLengthX() - 1) {
                    return true;
                } else {
                    return !CollisionDetection.wallInFront(grid.getEntity(character.getPosX() + 1, character.getPosY()));
                }
            case south:
                if (character.getPosY() == grid.getLengthY() - 1) {
                    return true;
                } else {
                    return !CollisionDetection.wallInFront(grid.getEntity(character.getPosX(), character.getPosY() + 1));
                }
            default:
                return false;
        }
    }

    public void switchDirection(Character character, Direction desiredDirection){
        character.switchDirection(desiredDirection);
    }

    public Direction getCharacterDirection(Character character){
        return character.getDirection();
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public boolean characterCollision(Character ghost, Character player) {
        return ghost.getPosX() == player.getPosX() && ghost.getPosY() == player.getPosY();
    }

    public void handleCollision(Ghost ghost, Character player) {
        ghost.setPos(13,8);
    }

    public void resetCharacters() {
        player.setPos(13, 18);
        for (Ghost ghost: ghosts) {
            ghost.setPos(13, 8);
        }
    } 

    public boolean hasSleepingGhosts() {
        for (Ghost ghost: ghosts) {
            if (ghost.isSleeping()) {
                return true;
            }
        }
        return false;
    }

    public void wakeNextGhost() {
        boolean haveAwoken = false;
        for (Ghost ghost: ghosts) {
            if (ghost.isSleeping() && !haveAwoken) {
                ghost.wake();
                ghost.setPos(13, 8);
                haveAwoken = true;
            }
        }
    }
}