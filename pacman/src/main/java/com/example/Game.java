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
        this.player = new Player(13, 18, 3);
    }

    public void moveCharacter(Character character){
        character.move();
    }

    public boolean isLegal(Character character, Direction direction) {
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

    public Character[] getCharacters() {
        Character[] characters = {this.player};
        return characters;
    }
}