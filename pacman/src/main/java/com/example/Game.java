package com.example;

import java.net.URL;
import java.io.File;

public class Game {
    Grid grid;
    Player player;
    private int lifeCounter;
    private int score;

    public Game(String filepath) {
        URL url = this.getClass().getResource("/" + filepath);
        File map = new File(url.getPath());
        this.grid = new Grid(map);
        this.player = new Player(14, 21, 8);
        this.lifeCounter = 3;
        this.score = 0;
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

    public boolean isLegal(Character character, Direction direction) {
        /**
         * Returns true if the given character can move in the given direction.
         */
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
            //changeGameState(new PowerState);
        }
        grid.setEntity(x,y,Entity.empty);
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

    public Character[] getCharacters() {
        /**
         * Returns an array of the current characters in the order: player, ... 
         */
        Character[] characters = {this.player};
        return characters;
    }

    public int getLifeCounter() {
        return lifeCounter;
    }
    public int getScore(){
        return score;
    }

    public void subtractLifeCounter(int n){
        lifeCounter = lifeCounter - n;
    }
    
    public void addScore(int n){
        score = score + n;
    }
}