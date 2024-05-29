package com.example;

import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;

import java.net.URL;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color; //Baggrund farve

public class Display {
    Image sheet = new Image(getClass().getResource("/sprite-sheet.png").toString());
    SpriteCollections collection = new Collections(sheet);
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

    boolean powerupRunningOut;

    public Display(Game game){
        this.game = game;
        this.canvas = new Canvas(600,800);
        this.gc = canvas.getGraphicsContext2D();
        this.playerFrame = 0;
        this.ghostFrame = 0;
        this.powerupRunningOut = false;
        addWalls();
    }

    private void updateWalls() {
        for (int y = 0; y < wallTypes.length; y++) {
            for (int x = 0; x < wallTypes[0].length; x++) {
                int wallType = wallTypes[y][x];
                if (wallType == -1){
                    collection.getEntitySprite(gc,"door",0,x+gameOffsetx,y+gameOffsety,factor);
                }
                if (wallType > 0) {
                    collection.getEntitySprite(gc,"walls",wallType,x+gameOffsetx,y+gameOffsety,factor);
                } else if (wallType == -1) {
                    collection.getEntitySprite(gc,"door",0,x+gameOffsetx,y+gameOffsety,factor);
                }
            }
        }
    }

    private void addWalls(){
        Grid grid = game.getGrid();
        int[][] wallTypes = new int[grid.getLengthY()][grid.getLengthX()];
        for (int y = 0; y < grid.getLengthY(); y++){
            for (int x = 0; x < grid.getLengthX(); x++){
                if (grid.getEntity(x, y) == Entity.wall){
                    wallTypes[y][x] = calculateWallType(x, y, grid);
                } else if (grid.getEntity(x, y) == Entity.door) {
                    wallTypes[y][x] = -1;
                } else {
                    wallTypes[y][x] = 0;
                }
            }
        }
        this.wallTypes = wallTypes;
    }

    private int calculateWallType(int x, int y, Grid grid) {
        int wallType = 0;
        int multiplier = 1;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (y+i >= 0 && y+i < grid.getLengthY() && x+j >= 0 && x+j < grid.getLengthX()) {
                    if (grid.getEntity(x+j, y+i) == Entity.wall) {
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
        gc.clearRect(0, 0, 600, 650);
        updatePellets();
        updateWalls();
        updateScore();
        updateCharacters();
    }

    private void updatePellets() {
        Grid grid = game.getGrid();
        for (int y = 0; y < grid.getLengthY(); y++){
            for (int x = 0; x < grid.getLengthX(); x++){
                if (grid.getEntity(x, y) == Entity.pellet){
                    collection.getEntitySprite(gc, "pellet", 0, x+gameOffsetx,y+gameOffsety, factor);
                } else if (grid.getEntity(x, y) == Entity.bigPellet) {
                    collection.getEntitySprite(gc, "bigPellet", 0, x+gameOffsetx,y+gameOffsety, factor);
                }
                if (grid.getEntity(x, y) == Entity.bigPellet){
                    collection.getEntitySprite(gc, "bigPellet", 0, x+gameOffsetx,y+gameOffsety, factor);
                }
            }
        }
    }

    private void updateCharacters() {
        // Ghosts
        int ghostCounter = 1;
        for (Ghost ghost: game.getGhosts()) {
            if (ghost.hasCollision() && ghost.canBeEaten()) {
                if (powerupRunningOut) {
                    collection.getCharacterSprite(gc, "scared", Direction.north, ghostFrame, ghost.getPosX()+gameOffsetx,ghost.getPosY()+gameOffsety, factor, offset);
                } else {
                    collection.getCharacterSprite(gc, "scared", Direction.north, ghostFrame / 2, ghost.getPosX()+gameOffsetx,ghost.getPosY()+gameOffsety, factor, offset);
                }
                } else if (!ghost.hasCollision() && !ghost.canBeEaten()) {
                collection.getCharacterSprite(gc, "dead", ghost.getDirection(), 0, ghost.getPosX()+gameOffsetx,ghost.getPosY()+gameOffsety, factor, offset);
            } else {
                collection.getCharacterSprite(gc, "ghost" + ghostCounter, ghost.getDirection(), ghostFrame / 2, ghost.getPosX()+gameOffsetx,ghost.getPosY()+gameOffsety, factor, offset);
            }
            // the cap on ghostCounter is equal to: the amount of ghost sprites - 1
            ghostCounter = (ghostCounter > 3) ? 1: ghostCounter + 1;
        }
        // Pacman
        collection.getCharacterSprite(gc, "pacman", game.getPlayer().getDirection(), playerFrame, game.getPlayer().getPosX()+gameOffsetx,game.getPlayer().getPosY()+gameOffsety, factor, offset);
    }

    public void incrementFrames() {
        playerFrame++;
        if (playerFrame == 4){
            playerFrame = 0;
        }
        ghostFrame++;
        if (ghostFrame == 4) {
            ghostFrame = 0;
        }
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public void setPowerupRunningOut(boolean bool) {
        powerupRunningOut = bool;
    }

    private void updateScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Daydream"));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Score:\n" + game.getScore(), 300, 60);
        gc.fillText("Lives: ", 70, 60);
        switch(game.getLifeCounter()){
            case 2:
                collection.getSprite(gc, "pacman", Direction.east, 0, 30, 70, factor*2);
                collection.getSprite(gc, "pacman", Direction.east, 0, 60, 70, factor*2);
                break;
            case 1:
                collection.getSprite(gc, "pacman", Direction.east, 0, 30, 70, factor*2);
            default:    
                break;
        }
    }

    public void showResetMenu(String message) {
        gc.clearRect(0, 0, 600, 800);
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + game.getScore(), 300, 280);
        gc.fillText(message, 300, 240);
    }

    public void showReady() {
        gc.setFill(Color.YELLOW);
        gc.fillText("Ready!", 300, 340);
    }

    public void showCount(int count) {
        gc.setFill(Color.WHITE);
        gc.fillText(count + "", 300, 280);
    }

}
