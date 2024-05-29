package com.example;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Collections implements SpriteCollections{

    private class Sprite {
        int x;
        int y;
        int size;
    
        public Sprite(int x, int y, int size){
            this.x = x;
            this.y = y;
            this.size = size;
        }
    
        public int[] get(){
            int[] res = {x,y, size};
            return res;
        }
    }

    private Image spriteSheet;
    private Map<String, Map<Direction, Sprite[]>> characterCollection;
    private Map<Direction, Sprite[]> pacman;
    private Map<Direction, Sprite[]> ghost1;
    private Map<Direction, Sprite[]> ghost2;
    private Map<Direction, Sprite[]> ghost3;
    private Map<Direction, Sprite[]> ghost4;
    private Map<Direction, Sprite[]> scaredGhost;
    private Map<Direction, Sprite[]> deadGhost;


    private Map<String, Map<Integer, Sprite>> entityCollection;
    private Map<Integer, Sprite> walls;
    private Map<Integer, Sprite> pellet;
    private Map<Integer, Sprite> bigPellet;
    private Map<Integer, Sprite> door;

    public Collections(Image spriteSheetPath){
        this.spriteSheet = spriteSheetPath;
        this.characterCollection = new HashMap<>();
        this.pacman = new HashMap<>();
        this.ghost1 = new HashMap<>();
        this.ghost2 = new HashMap<>();
        this.ghost3 = new HashMap<>();
        this.ghost4 = new HashMap<>();
        this.scaredGhost = new HashMap<>();
        this.deadGhost = new HashMap<>();

        this.entityCollection = new HashMap<>();
        this.walls = new HashMap<>();
        this.pellet = new HashMap<>();
        this.bigPellet = new HashMap<>();
        this.door = new HashMap<>();

        this.characterCollection.put("pacman", this.pacman);
        this.characterCollection.put("ghost1", this.ghost1);
        this.characterCollection.put("ghost2", this.ghost2);
        this.characterCollection.put("ghost3", this.ghost3);
        this.characterCollection.put("ghost4", this.ghost4);
        this.characterCollection.put("scared", this.scaredGhost);
        this.characterCollection.put("dead", this.deadGhost);

        
        this.entityCollection.put("walls", this.walls);
        this.entityCollection.put("pellet", this.pellet);
        this.entityCollection.put("bigPellet", this.bigPellet);
        this.entityCollection.put("door", this.door);

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


        //Pellet
        this.pellet.put(0, new Sprite(8,8,8));
        this.pellet.put(1, new Sprite(24,8,8));

        //BigPellet
        this.bigPellet.put(0, new Sprite(80,0,8));
        this.bigPellet.put(1, new Sprite(24,8,8)); //empty

        // ghost 1
        Sprite[] ghost1North = new Sprite[2];
        ghost1North[0] = new Sprite(455 + 64, 64, 16);
        ghost1North[1] = new Sprite(455 + 80, 64, 16);
        this.ghost1.put(Direction.north, ghost1North);

        Sprite[] ghost1South = new Sprite[2];
        ghost1South[0] = new Sprite(455 + 96, 64, 16);
        ghost1South[1] = new Sprite(455 + 112, 64, 16);
        this.ghost1.put(Direction.south, ghost1South);

        Sprite[] ghost1East = new Sprite[2];
        ghost1East[0] = new Sprite(455, 64, 16);
        ghost1East[1] = new Sprite(455 + 16, 64, 16);
        this.ghost1.put(Direction.east, ghost1East);

        Sprite[] ghost1West = new Sprite[2];
        ghost1West[0] = new Sprite(455 + 32, 64, 16);
        ghost1West[1] = new Sprite(455 + 48, 64, 16);
        this.ghost1.put(Direction.west, ghost1West);

        // ghost 2
        Sprite[] ghost2North = new Sprite[2];
        ghost2North[0] = new Sprite(455 + 64, 64 + 16, 16);
        ghost2North[1] = new Sprite(455 + 80, 64 + 16, 16);
        this.ghost2.put(Direction.north, ghost2North);

        Sprite[] ghost2South = new Sprite[2];
        ghost2South[0] = new Sprite(455 + 96, 64 + 16, 16);
        ghost2South[1] = new Sprite(455 + 112, 64 + 16, 16);
        this.ghost2.put(Direction.south, ghost2South);

        Sprite[] ghost2East = new Sprite[2];
        ghost2East[0] = new Sprite(455, 64 + 16, 16);
        ghost2East[1] = new Sprite(455 + 16, 64 + 16, 16);
        this.ghost2.put(Direction.east, ghost2East);

        Sprite[] ghost2West = new Sprite[2];
        ghost2West[0] = new Sprite(455 + 32, 64 + 16, 16);
        ghost2West[1] = new Sprite(455 + 48, 64 + 16, 16);
        this.ghost2.put(Direction.west, ghost2West);

        //ghost 3
        Sprite[] ghost3North = new Sprite[2];
        ghost3North[0] = new Sprite(455 + 64, 64 + 32, 16);
        ghost3North[1] = new Sprite(455 + 80, 64 + 32, 16);
        this.ghost3.put(Direction.north, ghost3North);

        Sprite[] ghost3South = new Sprite[2];
        ghost3South[0] = new Sprite(455 + 96, 64 + 32, 16);
        ghost3South[1] = new Sprite(455 + 112, 64 + 32, 16);
        this.ghost3.put(Direction.south, ghost3South);

        Sprite[] ghost3East = new Sprite[2];
        ghost3East[0] = new Sprite(455, 64 + 32, 16);
        ghost3East[1] = new Sprite(455 + 16, 64 + 32, 16);
        this.ghost3.put(Direction.east, ghost3East);

        Sprite[] ghost3West = new Sprite[2];
        ghost3West[0] = new Sprite(455 + 32, 64 + 32, 16);
        ghost3West[1] = new Sprite(455 + 48, 64 + 32, 16);
        this.ghost3.put(Direction.west, ghost3West);

        //ghost 4
        Sprite[] ghost4North = new Sprite[2];
        ghost4North[0] = new Sprite(455 + 64, 64 + 48, 16);
        ghost4North[1] = new Sprite(455 + 80, 64 + 48, 16);
        this.ghost4.put(Direction.north, ghost4North);

        Sprite[] ghost4South = new Sprite[2];
        ghost4South[0] = new Sprite(455 + 96, 64 + 48, 16);
        ghost4South[1] = new Sprite(455 + 112, 64 + 48, 16);
        this.ghost4.put(Direction.south, ghost4South);

        Sprite[] ghost4East = new Sprite[2];
        ghost4East[0] = new Sprite(455, 64 + 48, 16);
        ghost4East[1] = new Sprite(455 + 16, 64 + 48, 16);
        this.ghost4.put(Direction.east, ghost4East);

        Sprite[] ghost4West = new Sprite[2];
        ghost4West[0] = new Sprite(455 + 32, 64 + 48, 16);
        ghost4West[1] = new Sprite(455 + 48, 64 + 48, 16);
        this.ghost4.put(Direction.west, ghost4West);

        //Scared Ghost 584 64
        Sprite[] scaredGhostSprite = new Sprite[4];
        scaredGhostSprite[0] = new Sprite(455 + 112 + 16, 64, 16);
        scaredGhostSprite[1] = new Sprite(455 + 112 + 32, 64, 16);
        scaredGhostSprite[2] = new Sprite(455 + 112 + 48, 64, 16);
        scaredGhostSprite[3] = new Sprite(455 + 112 + 64, 64, 16);
        this.scaredGhost.put(Direction.north, scaredGhostSprite);

        //Dead Ghost 584 64+16
        Sprite[] deadGhostEast = new Sprite[1];
        deadGhostEast[0] = new Sprite(584, 64 + 16, 16);
        this.deadGhost.put(Direction.east, deadGhostEast);

        Sprite[] deadGhostWest = new Sprite[1];
        deadGhostWest[0] = new Sprite(584+16, 64 + 16, 16);
        this.deadGhost.put(Direction.west, deadGhostWest);

        Sprite[] deadGhostNorth = new Sprite[1];
        deadGhostNorth[0] = new Sprite(584+32, 64 + 16, 16);
        this.deadGhost.put(Direction.north, deadGhostNorth);

        Sprite[] deadGhostSouth = new Sprite[1];
        deadGhostSouth[0] = new Sprite(584+48, 64 + 16, 16);
        this.deadGhost.put(Direction.south, deadGhostSouth);

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

        //Door
        this.door.put(0, new Sprite(80,16,8));


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

    //character
    public void getSprite(GraphicsContext gc, String group, Direction type, int frame, int x, int y, int size){
        Sprite sprite = characterCollection.get(group).get(type)[frame];
        
        
        if (sprite != null){    
            gc.drawImage(spriteSheet, sprite.get()[0], sprite.get()[1], sprite.get()[2], sprite.get()[2], x, y, size, size);
        }
    }

    //entity
    public void getSprite(GraphicsContext gc, String group, int type, int x, int y, int size){
        Sprite sprite = entityCollection.get(group).get(type);
        
        
        if (sprite != null){    
            gc.drawImage(spriteSheet, sprite.get()[0], sprite.get()[1], sprite.get()[2], sprite.get()[2], x, y, size, size);
        }
    }


    
}
