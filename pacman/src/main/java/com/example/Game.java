package com.example;

import java.net.URL;
import java.io.File;

public class Game {
    Grid grid;
    Player player;
    

    public Game(String filepath) {
        URL url = this.getClass().getResource("/" + filepath);
        File map = new File(url.getPath());
        this.grid = new Grid(map);
        this.player = new Player(14, 23, 8);
    }

    public void moveCharacter(Character character){
        /**
         * Moves a given character.
         */
        character.move();
    }

    public boolean isLegal(Character character, Direction direction) {
        /**
         * Returns true if the given character can move in the given direction.
         */
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
            //addScore(10);
        } else if (grid.getEntity(x, y) == Entity.bigPellet) {
            //addScore(50);
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
}