package com.example;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Collections {
    private Image spriteSheet;
    private Map<String, Map<Direction, Sprite[]>> characterCollection;
    private Map<Direction, Sprite[]> pacman;
    private Map<Direction, Sprite[]> ghost1;
    private Map<Direction, Sprite[]> ghost2;
    private Map<Direction, Sprite[]> ghost3;
    private Map<Direction, Sprite[]> ghost4;

    private Map<String, Map<Integer, Sprite>> entityCollection;
    private Map<Integer, Sprite> walls;
    private Map<Integer, Sprite> pellet;

    public Collections(Image spriteSheetPath){
        this.spriteSheet = spriteSheetPath;
        this.characterCollection = new HashMap<>();
        this.pacman = new HashMap<>();

        this.entityCollection = new HashMap<>();
        this.walls = new HashMap<>();
        this.pellet = new HashMap<>();

        this.characterCollection.put("pacman", this.pacman);
        this.entityCollection.put("walls", this.walls);
        this.entityCollection.put("pellet", this.pellet);

        initializeSprites();
    }
    
    private void initializeSprites(){
        Sprite[] pacmanNorth = new Sprite[4];
        pacmanNorth[0] = new Sprite(454, 0+32, 16);
        pacmanNorth[1] = new Sprite(454+16, 0+32, 16);
        pacmanNorth[2] = new Sprite(454+32, 0, 16);
        pacmanNorth[3] = new Sprite(454+16, 0+32, 16);
        this.pacman.put(Direction.north, pacmanNorth);

        Sprite[] pacmanSouth = new Sprite[4];
        pacmanSouth[0] = new Sprite(454, 0+48, 16);
        pacmanSouth[1] = new Sprite(454+16, 0+48, 16);
        pacmanSouth[2] = new Sprite(454+32, 0, 16);
        pacmanSouth[3] = new Sprite(454+16, 0+48, 16);
        this.pacman.put(Direction.south, pacmanSouth);

        Sprite[] pacmanEast = new Sprite[4];
        pacmanEast[0] = new Sprite(454, 0, 16);
        pacmanEast[1] = new Sprite(454 + 16, 0, 16);
        pacmanEast[2] = new Sprite(454 + 32, 0, 16);
        pacmanEast[3] = new Sprite(454 + 16, 0, 16);
        this.pacman.put(Direction.east, pacmanEast);

        Sprite[] pacmanWest = new Sprite[4];
        pacmanWest[0] = new Sprite(454, 16, 16);
        pacmanWest[1] = new Sprite(454+16, 16, 16);
        pacmanWest[2] = new Sprite(454+32, 0, 16);
        pacmanWest[3] = new Sprite(454+16, 16, 16);
        this.pacman.put(Direction.west, pacmanWest);

        this.pellet.put(0, new Sprite(8,8,8));
        this.pellet.put(1, new Sprite(24,8,8));

        this.walls.put(0, new Sprite(0,0,8));

    }

    public void getEntitySprite(GraphicsContext gc, String group, int type, int x, int y, int factor){
        Sprite sprite = entityCollection.get(group).get(type);

        if (sprite != null){    
            gc.drawImage(spriteSheet, sprite.get()[0], sprite.get()[1], sprite.get()[2], sprite.get()[2], (x*factor), (y*factor), factor, factor);
        }        
    }

    public void getCharacterSprite(GraphicsContext gc, String group, Direction type, int frame, int x, int y, int factor, int offset){
        Sprite sprite = characterCollection.get(group).get(type)[frame];

        if (sprite != null){    
            gc.drawImage(spriteSheet, sprite.get()[0], sprite.get()[1], sprite.get()[2], sprite.get()[2], (x*factor), (y*factor), factor, factor);
        }
    }


    
}
