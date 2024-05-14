package com.example;

import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color; //Baggrund farve

public class Display {
    Image sheet = new Image(getClass().getResource("/sprite-sheet.png").toString());
    Collections collection = new Collections(sheet);
    int ghostFrame;
    int playerFrame;
    int offset = 0;
    int[][] wallTypes;

    Game game;
    int factor = 15;
    
    int gameOffsetx = 6;
    int gameOffsety = 7;

    Canvas canvas;
    GraphicsContext gc;

    Circle pellet;

    public Display(Game game){
        this.game = game;
        this.canvas = new Canvas(600,800);
        this.gc = canvas.getGraphicsContext2D();
        this.playerFrame = 0;
        this.ghostFrame = 0;
    
        addWalls();
        createScore();
    }

    private void updateWalls() {
        for (int y = 0; y < wallTypes.length; y++) {
            for (int x = 0; x < wallTypes[0].length; x++) {
                int wallType = wallTypes[y][x];
                if (wallType > 0) {
                    collection.getEntitySprite(gc,"walls",wallType,x+gameOffsetx,y+gameOffsety,factor);
                }
            }
        }
    }

    private void addWalls(){
        Grid grid = game.getGrid();
        int[][] wallTypes = new int[grid.getMap().length][grid.getMap()[0].length];
        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                if (grid.getEntity(x, y) == Entity.wall){
                    wallTypes[y][x] = calculateWallType(x, y, grid.getMap());
                } else {
                    wallTypes[y][x] = 0;
                }
            }
        }
        this.wallTypes = wallTypes;
    }

    private int calculateWallType(int x, int y, Entity[][] map) {
        int wallType = 0;
        int multiplier = 1;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (y+i >= 0 && y+i < map.length && x+j >= 0 && x+j < map[0].length) {
                    if (map[y+i][x+j] == Entity.wall) {
                        wallType += multiplier;
                    }
                }
                multiplier *= 2;
            }
        }
        wallType = catchWallEdgeCase(wallType);
        return wallType;
    }

    private int catchWallEdgeCase(int input) {
        switch(input) {
            case 120:
                return 56;
            case 150:
                return 146;
            case 402:
                return 146;
            case 57:
                return 56;
            case 312:
                return 56;
            case 147:
                return 146;
            case 210:
                return 146;
            case 60:
                return 56;
            case 439:
                return 438;
            case 223:
                return 219;
            case 127:
                return 63;
            case 505:
                return 504;
            case 319:
                return 63;
            case 508:
                return 504;
            case 502:
                return 438;
            case 475:
                return 219;
            case 211:
                return 146;
            case 406:
                return 146;
            case 61:
                return 56;
            case 376:
                return 56;
            case 304:
                return 48;
            case 52:
                return 48;
            case 25:
                return 24;
            case 88:
                return 24;
            default:
                return input;
        }
    }
    public void update() {
        gc.clearRect(gameOffsetx, gameOffsety+85, 50*factor, 50*factor);
        updatePellets();
        updateWalls();
        updateScore();
        updateCharacters();
    }

    private void updatePellets() {
        Grid grid = game.getGrid();
        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                if (grid.getEntity(x, y) == Entity.pellet){

                    collection.getEntitySprite(gc, "pellet", 0, x+gameOffsetx,y+gameOffsety, factor);

                }
            }
        }
    }

    private void updateCharacters() {
        collection.getCharacterSprite(gc, "pacman", game.getPlayer().getDirection(), playerFrame, game.getPlayer().getPosX()+gameOffsetx,game.getPlayer().getPosY()+gameOffsety, factor, offset);
        int ghostCounter = 1;
        for (Ghost ghost: game.getGhosts()) {
            collection.getCharacterSprite(gc, "ghost" + ghostCounter, ghost.getDirection(), ghostFrame, ghost.getPosX()+gameOffsetx,ghost.getPosY()+gameOffsety, factor, offset);
            // the cap on ghostCounter is equal to: the amount of ghost sprites - 1
            ghostCounter = (ghostCounter > 1) ? 1: ghostCounter + 1;
        }
    }

    public void incrementFrames() {
        playerFrame++;
        if (playerFrame == 4){
            playerFrame = 0;
        }
        ghostFrame++;
        if (ghostFrame == 2) {
            ghostFrame = 0;
        }
    }

    public void setOffset(int playerTime){
        this.offset = ((15 - playerTime)/15)*20;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    private void updatePlayer() {
        collection.getCharacterSprite(gc, "pacman", game.getPlayer().getDirection(), playerFrame, game.getPlayer().getPosX()+gameOffsetx,game.getPlayer().getPosY()+gameOffsety, factor, offset);
    }

    private void updateGhosts() {
        
    }

    private void createScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial Black", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Score:\n0", 300, 60);

        gc.fillText("Lives: ", 70, 60);

        gc.setFill(Color.YELLOW);
        gc.fillRect(40,70,factor/1.25,factor/1.25);
        gc.fillRect(62,70,factor/1.25,factor/1.25);
        gc.fillRect(84,70,factor/1.25,factor/1.25);
    }

    private void updateScore() {
        gc.clearRect(250, 30, 100, 60); //250
        gc.setFill(Color.WHITE);
        gc.fillText("Score:\n" + game.getScore(), 300, 60);
        
        switch(game.getLifeCounter()){
            case 3:
                break;
            case 2:
                gc.clearRect(84, 70, factor/1.25, factor/1.25);    
                break;
            case 1:
                gc.clearRect(62, 70, factor/1.25+12, factor/1.25);    
                break;
            default:
                gc.clearRect(40, 70, factor/1.25+24, factor/1.25);    
                break;
        }
    }



}
