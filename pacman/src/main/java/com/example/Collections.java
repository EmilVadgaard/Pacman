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

        // straight walls
        this.walls.put(146, new Sprite(0,8,8));
        this.walls.put(219, new Sprite(0,24,8));
        this.walls.put(438, new Sprite(0,40,8));
        this.walls.put(56, new Sprite(8,0,8));
        this.walls.put(504, new Sprite(8,16,8));
        this.walls.put(63, new Sprite(8,32,8));

        this.walls.put(18, new Sprite(0,56,8));
        this.walls.put(144, new Sprite(8,48,8));
        this.walls.put(24, new Sprite(16,40,8));
        this.walls.put(48, new Sprite(16,56,8));

        // corner walls
        this.walls.put(176, new Sprite(24,0,8));
        this.walls.put(152, new Sprite(40,0,8));
        this.walls.put(50, new Sprite(24,16,8));
        this.walls.put(26, new Sprite(40,16,8));

        this.walls.put(432, new Sprite(56,0,8));
        this.walls.put(216, new Sprite(72,0,8));
        this.walls.put(54, new Sprite(56,16,8));
        this.walls.put(27, new Sprite(72,16,8));

        this.walls.put(248, new Sprite(24,32,8));
        this.walls.put(440, new Sprite(40,32,8));
        this.walls.put(59, new Sprite(24,48,8));
        this.walls.put(62, new Sprite(40,48,8));

        this.walls.put(182, new Sprite(56,32,8));
        this.walls.put(155, new Sprite(72,32,8));
        this.walls.put(434, new Sprite(56,48,8));
        this.walls.put(218, new Sprite(72,48,8));

        this.walls.put(255, new Sprite(88,00,8));
        this.walls.put(447, new Sprite(104,00,8));
        this.walls.put(507, new Sprite(88,16,8));
        this.walls.put(510, new Sprite(104,16,8));


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
            gc.drawImage(spriteSheet, sprite.get()[0], sprite.get()[1], sprite.get()[2], sprite.get()[2], (x*factor)-2, (y*factor)-2, factor+4, factor+4);
        }
    }


    
}
