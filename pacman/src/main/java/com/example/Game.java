package com.example;

import java.net.URL;
import java.io.File;
import java.util.Random;
import java.util.ArrayList;

public class Game {
    Grid grid;
    Player player;
    SearchAlgorithm breadthFirst;
    ArrayList<Ghost> ghosts;
    Random numberGenerator;
    

    public Game(String filepath) {
        URL url = this.getClass().getResource("/" + filepath);
        File map = new File(url.getPath());
        this.grid = new Grid(map);
        this.player = new Player(13, 18, 3);
        this.breadthFirst = new BreadthFirstSearch();
        this.numberGenerator = new Random();

        this.ghosts = new ArrayList<Ghost>();
        ghosts.add(new Ghost(13,8,3,8));
        ghosts.add(new Ghost(14, 8, 3, 12));
    }

    public void moveCharacter(Character character) {
        character.move();
    }

    public void moveGhosts() {
        for (Ghost ghost: ghosts) {
            Direction direction = generateGhostDirection(ghost);
            switchDirection(ghost, direction);
            ghost.move();
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
        Direction desiredDirection = breadthFirst.search(ghost, grid.getMap(), player.getPosX(),player.getPosY());
        return desiredDirection;
    }

    public boolean isLegal(Character character, Direction direction) {
        if (direction == null) {
            return false;
        }
        switch(direction) {
            case north:
                return !CollisionDetection.wallInFront(grid.getEntity(character.getPosX(), character.getPosY() - 1));
            case west:
                return !CollisionDetection.wallInFront(grid.getEntity(character.getPosX() - 1, character.getPosY()));
            case east:
                return !CollisionDetection.wallInFront(grid.getEntity(character.getPosX() + 1, character.getPosY()));
            case south:
                return !CollisionDetection.wallInFront(grid.getEntity(character.getPosX(), character.getPosY() + 1));
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

    public void resetCharacters() {
        player.setPosition(13, 18);
        for (Ghost ghost: ghosts) {
            ghost.setPosition(13, 8);
        }
    } 
}