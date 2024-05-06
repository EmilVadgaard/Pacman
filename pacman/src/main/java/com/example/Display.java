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
    int playerFrame = 0;
    int offset = 0;

    Game game;
    int factor = 15;
    
    int gameOffsetx = 0;
    int gameOffsety = 7;

    Scoreboard scoreboard;

    Canvas canvas;
    GraphicsContext gc;

    private int testCounter = 0; //FJERN DEN HER SENERE. DET BARE TEST FOR SCOREBOARD

    Circle pellet;

    public Display(Game game, Scoreboard scoreboard){
        this.game = game;
        this.canvas = new Canvas(600,650);
        this.gc = canvas.getGraphicsContext2D();
        this.scoreboard = scoreboard;
    
        addWalls();
        createScore();
    }

    private void addWalls(){
        Grid grid = game.getGrid();

        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                if (grid.getEntity(x, y) == Entity.wall){

                    // gc.setFill(Color.BLUE);
                    // gc.fillRect(x*factor,y*factor,factor,factor);
                    
                    collection.getEntitySprite(gc, "walls", 0, x+gameOffsetx,y+gameOffsety, factor);

                }
            }
        }
    }

    public void update() {
        //gc.setFill(Color.BLACK);
        //gc.fillRect(0,0,600,650); Erstatet med det der står lige under.
        gc.clearRect(gameOffsetx, gameOffsety, 50*factor, 50*factor);
        updatePellets();
        addWalls();
        updateScore();
        Character[] characters = game.getCharacters();

        collection.getCharacterSprite(gc, "pacman", characters[0].getDirection(), playerFrame, characters[0].getPosX()+gameOffsetx,characters[0].getPosY()+gameOffsety, factor, offset);

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

    public void incrementPlayerFrame(){
        playerFrame++;
        if (playerFrame == 4){
            playerFrame = 0;
        }
    }

    public void setOffset(int playerTime){
        this.offset = ((15 - playerTime)/15)*20;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    private void updatePlayer() {
        
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
        gc.clearRect(250, 30, 100, 60);
        gc.setFill(Color.WHITE);
        gc.fillText("Score:\n" + scoreboard.getScore(), 300, 60);
        
        switch(scoreboard.getLifeCounter()){
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
