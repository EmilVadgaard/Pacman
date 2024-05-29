package com.example;

import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class used to display the game of Pac-man on the screen.
 */
public class Display {
    private Image sheet = new Image(getClass().getResource("/sprite-sheet.png").toString());
    private SpriteCollections collection = new Collections(sheet);
    private Game game;
    private Canvas canvas;
    private GraphicsContext gc;

    private int ghostFrame;
    private int playerFrame;
    private int[][] wallTypes;

    private final int gameOffsetx = 6;
    private final int gameOffsety = 7;
    private final int factor = 15;

    private boolean powerupRunningOut;

    /**
     * Creates an instance of the Display class.
     * @param game The game object.
     */
    public Display(Game game){
        this.game = game;
        this.canvas = new Canvas(600,800);
        this.gc = canvas.getGraphicsContext2D();
        this.playerFrame = 0;
        this.ghostFrame = 0;
        this.powerupRunningOut = false;
        addWalls();
    }

    /*
     * Displays all the walls in the game.
     */
    private void updateWalls() {
        for (int y = 0; y < wallTypes.length; y++) {
            for (int x = 0; x < wallTypes[0].length; x++) {
                int wallType = wallTypes[y][x];
                if (wallType == -1){
                    collection.drawEntitySprite(gc,"door",0,x+gameOffsetx,y+gameOffsety,factor);
                }
                if (wallType > 0) {
                    collection.drawEntitySprite(gc,"walls",wallType,x+gameOffsetx,y+gameOffsety,factor);
                } else if (wallType == -1) {
                    collection.drawEntitySprite(gc,"door",0,x+gameOffsetx,y+gameOffsety,factor);
                }
            }
        }
    }

    /*
     * Populates the display with walls.
     */
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

    /*
     * Calculates the wall type.
     * Used to detemermine the type of wall that will be displayed.
     */
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

    /*
     * Used to catch walls that are identical to others but give a different
     * result using calculateWallType
     */
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

    /**
     * Updates the screen with current position of all entities and characters.
     */
    public void update() {
        gc.clearRect(0, 0, 600, 650);
        updatePellets();
        updateWalls();
        updateScore();
        updateCharacters();
    }

    /*
     * Displays all the remaining pellets.
     */
    private void updatePellets() {
        Grid grid = game.getGrid();
        for (int y = 0; y < grid.getLengthY(); y++){
            for (int x = 0; x < grid.getLengthX(); x++){
                if (grid.getEntity(x, y) == Entity.pellet){
                    collection.drawEntitySprite(gc, "pellet", 0, x+gameOffsetx,y+gameOffsety, factor);
                } else if (grid.getEntity(x, y) == Entity.bigPellet) {
                    collection.drawEntitySprite(gc, "bigPellet", 0, x+gameOffsetx,y+gameOffsety, factor);
                }
                if (grid.getEntity(x, y) == Entity.bigPellet){
                    collection.drawEntitySprite(gc, "bigPellet", 0, x+gameOffsetx,y+gameOffsety, factor);
                }
            }
        }
    }

    /*
     * Displays all the characters at their current location.
     */
    private void updateCharacters() {
        // Ghosts
        int ghostCounter = 1;
        for (Ghost ghost: game.getGhosts()) {
            if (ghost.hasCollision() && ghost.canBeEaten()) {
                if (powerupRunningOut) {
                    collection.drawCharacterSprite(gc, "scared", Direction.north, ghostFrame, ghost.getPosX()+gameOffsetx,ghost.getPosY()+gameOffsety, factor);
                } else {
                    collection.drawCharacterSprite(gc, "scared", Direction.north, ghostFrame / 2, ghost.getPosX()+gameOffsetx,ghost.getPosY()+gameOffsety, factor);
                }
                } else if (!ghost.hasCollision() && !ghost.canBeEaten()) {
                collection.drawCharacterSprite(gc, "dead", ghost.getDirection(), 0, ghost.getPosX()+gameOffsetx,ghost.getPosY()+gameOffsety, factor);
            } else {
                collection.drawCharacterSprite(gc, "ghost" + ghostCounter, ghost.getDirection(), ghostFrame / 2, ghost.getPosX()+gameOffsetx,ghost.getPosY()+gameOffsety, factor);
            }
            // the cap on ghostCounter is equal to: the amount of ghost sprites - 1
            ghostCounter = (ghostCounter > 3) ? 1: ghostCounter + 1;
        }
        // Pacman
        collection.drawCharacterSprite(gc, "pacman", game.getPlayer().getDirection(), playerFrame, game.getPlayer().getPosX()+gameOffsetx,game.getPlayer().getPosY()+gameOffsety, factor);
    }

    /**
     * Increments the frames used for the animated sprites.
     */
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

    /**
     * Returns the Canvas object used by the display.
     */
    public Canvas getCanvas() {
        return this.canvas;
    }

    /**
     * Used to determine whether ghosts should start flashing blue and white.
     * @param bool true if ghosts should start flashing blue and white.
     */
    public void setPowerupRunningOut(boolean bool) {
        powerupRunningOut = bool;
    }

    /*
     * Displays the current score on the screen. 
     */
    private void updateScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Daydream"));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Score:\n" + game.getScore(), 300, 60);
        gc.fillText("Lives: ", 70, 60);
        switch(game.getLifeCounter()){
            case 2:
                collection.drawSprite(gc, "pacman", Direction.east, 0, 30, 70, factor*2);
                collection.drawSprite(gc, "pacman", Direction.east, 0, 60, 70, factor*2);
                break;
            case 1:
                collection.drawSprite(gc, "pacman", Direction.east, 0, 30, 70, factor*2);
            default:    
                break;
        }
    }

    /**
     * Clears the screen and displays the score along with the given message.
     * @param message The text which should be displayed.
     */
    public void showResetMenu(String message) {
        gc.clearRect(0, 0, 600, 800);
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + game.getScore(), 300, 280);
        gc.fillText(message, 300, 240);
    }

    /**
     * Displays ready text on screen.
     */
    public void showReady() {
        gc.setFill(Color.YELLOW);
        gc.fillText("Ready!", 300, 340);
    }

}
