package com.example;

import java.net.URL;
import java.io.File;
import java.util.Random;
import java.util.ArrayList;

public class Game implements Ruleset {
    private Grid grid;
    private Player player;
    private int lifeCounter;
    private int score;
    private int pellets;
    private int multiplier;
    private ArrayList<Ghost> ghosts;
    private Random numberGenerator;

    public Game(String filepath){
        URL url = this.getClass().getResource("/" + filepath);
        File map = new File(url.getPath());
        this.grid = new Grid(map);
        this.player = new Player(14, 21, 8);
        this.lifeCounter = 3;
        this.score = 0;
        this.pellets = countPellets();
        this.numberGenerator = new Random();
        this.ghosts = new ArrayList<Ghost>();
        this.multiplier = 1;
        ghosts.add(new Ghost(12,13,3,6, new NormalGhostState(this)));
        ghosts.add(new Ghost(13, 13, 3, 10, new NormalGhostState(this)));
        ghosts.add(new Ghost(14,13,3,12, new NormalGhostState(this)));
    }

    public void moveCharacter(Character character){
        /**
         * Moves a given character.
         */
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
                Direction direction = ghost.nextDirection(player.getPosX(), player.getPosY());
                switchDirection(ghost, direction);
                moveCharacter(ghost);
            }
        }
    }


    public boolean isLegal(Character character, Direction direction) {
        /**
         * Returns true if the given character can move in the given direction.
         */
        if (direction == null) {
            return false;
        }
        return nextPosition(character.getPosX(), character.getPosY(), direction) != null;
    }

    public int[] nextPosition(int posX, int posY, Direction direction) {
        /**
         * Returns the next position of a move as an integer array of length 2.
         * Returns null if the move is illegal.
         */
        int[] coords = new int[2];
        switch(direction) {
            case north:
                if (posY == 0) {
                    coords[1] = grid.getLengthY() - 1;
                } else {
                    coords[1] = posY - 1;
                }
                coords[0] = posX;
                break;
            case west:
                if (posX == 0) {
                    coords[0] = grid.getLengthX() - 1;
                } else {
                    coords[0] = posX - 1;
                }
                coords[1] = posY;
                break;
            case east:
                if (posX == grid.getLengthX() - 1) {
                    coords[0] = 0;
                } else {
                    coords[0] = posX + 1;
                }
                coords[1] = posY;
                break;
            case south:
                if (posY == grid.getLengthY() - 1) {
                    coords[1] = 0;
                } else {
                    coords[1] = posY + 1;
                }
                coords[0] = posX;
                break;
            default:
                return null;
        }
        if (grid.getEntity(coords[0], coords[1]) == Entity.wall) {
            return null;
        } else {
            return coords;
        }
    }

    public boolean isEatable(int x, int y) {
        /**
         * Returns true if the entity at the coordinates given can be eaten.
         */
        return grid.getEntity(x, y) == Entity.pellet || grid.getEntity(x, y) == Entity.bigPellet;
    }

    public void eat(int x, int y) {
        /**
         * Removes an entity from the game grid, and adds score corresponding to the
         * entity removed.
         * Changes the gamestate to power state, if a big pellet is eaten. 
         */
        if (grid.getEntity(x, y) == Entity.pellet) {
            addScore(10);
        } else if (grid.getEntity(x, y) == Entity.bigPellet) {
            addScore(50);
            for (Ghost ghost: ghosts) {
                ghost.changeState(new ScaredGhostState(this));
            }
        }
        grid.setEntity(x,y,Entity.empty);
        pellets--;
    }

    public void switchDirection(Character character, Direction desiredDirection){
        /**
         * Changes a given character's direction.
         */
        character.switchDirection(desiredDirection);
    }

    public Direction getCharacterDirection(Character character){
        /**
         * Returns a given character's current direction.
         */
        return character.getDirection();
    }

    public Grid getGrid() {
        /**
         * Returns the current game grid.
         */
        return this.grid;
    }

    private int countPellets() {
        /**
         * Counts the pellets in the grid.
         */
        int pellets = 0;
        for (int y = 0; y < grid.getLengthY(); y++) {
            for (int x = 0; x < grid.getLengthX(); x++) {
                if (grid.getEntity(x, y) == Entity.pellet || grid.getEntity(x, y) == Entity.bigPellet) {
                    pellets++;
                }
            }
        }
        return pellets;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
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
                ghost.setPos(13, 11);
                haveAwoken = true;
            }
        }
    }

    public boolean characterCollision(Ghost ghost, Character player) {
        if (ghost.hasCollision()) {
            return ghost.getPosX() == player.getPosX() && ghost.getPosY() == player.getPosY();
        } else {
            return false;
        }
    }

    public void handleCollision(Ghost ghost, Character player) {
        if (ghost.canBeEaten()) {
            addScore(200 * multiplier);
            multiplier = multiplier * 2;
            ghost.setPos(ghost.getHomeX(), ghost.getHomeY());
            ghost.sleep();
        } else {
            subtractLifeCounter();
            resetCharacters();
        }
    }

    public void resetCharacters() {
        player.setPos(14, 21);
        for (Ghost ghost: ghosts) {
            ghost.setPos(ghost.getHomeX(), ghost.getHomeY());
            ghost.sleep();
        }
    } 

    public int getLifeCounter() {
        return lifeCounter;
    }
    public int getScore(){
        return score;
    }

    public void subtractLifeCounter(){
        lifeCounter--;
    }
    
    public void addScore(int n){
        score = score + n;
    }

    public boolean isGameOver() {
        return pellets == 0 || lifeCounter < 0;
    }
}