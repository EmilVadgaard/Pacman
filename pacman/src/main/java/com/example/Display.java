package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group; //Gruppe
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent; //Keyboard input
import javafx.scene.paint.Color; //Baggrund farve

public class Display {
    Game game;
    int factor = 20;
    
    int gameOffsetx = 20;
    int gameOffsety = 110;

    Canvas canvas;
    GraphicsContext gc;

    private Scoreboard scoreboard = new Scoreboard();
    private int testCounter = 0; //FJERN DEN HER SENERE. DET BARE TEST FOR SCOREBOARD

    Circle pellet;

    public Display(Game game){
        this.game = game;
        this.canvas = new Canvas(600,650);
        this.gc = canvas.getGraphicsContext2D();
        
        addWalls();
        createScore(scoreboard);
    }

    private void addWalls(){
        Grid grid = game.getGrid();

        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                if (grid.getEntity(x, y) == Entity.wall){
                    gc.setFill(Color.BLUE);
                    gc.fillRect(x*factor+gameOffsetx,y*factor+gameOffsety,factor,factor);
                    // Rectangle wall = new Rectangle(x*factor, y*factor, factor, factor);
                    // wall.setFill(Color.BLUE);
                    //wall.setHeight(factor);
                    //wall.setWidth(factor);
                }
            }
        }
    }

    public void update() {
        //gc.setFill(Color.BLACK);
        //gc.fillRect(0,0,600,650); Erstatet med det der står lige under.
        gc.clearRect(gameOffsetx, gameOffsety, 28*factor, 28*factor);
        updatePellets();
        addWalls();
        Character[] characters = game.getCharacters();
        gc.setFill(Color.YELLOW);
        gc.fillRect(characters[0].getPosX()*factor+gameOffsetx,characters[0].getPosY()*factor+gameOffsety,factor,factor);
        //add(player, characters[0].getPosX(), characters[0].getPosY());
        updateScore(scoreboard);
    }

    private void updatePellets() {
        Grid grid = game.getGrid();
        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                if (grid.getEntity(x, y) == Entity.pellet){
                    gc.setFill(Color.WHITE);
                    gc.fillRect(x*factor+factor/2-factor/6/2+gameOffsetx,y*factor+factor/2-factor/6/2+gameOffsety,factor/6,factor/6);
                    //pellet.setRadius(factor/8);
                    
                }
            }
        }
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    private void updatePlayer() {
        
    }

    private void updateGhosts() {
        
    }

    private void createScore(Scoreboard scoreboard) {
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

    private void updateScore(Scoreboard Scoreboard) {
        testCounter++;
        gc.clearRect(250, 30, 100, 60);
        gc.setFill(Color.WHITE);
        gc.fillText("Score:\n" + testCounter, 300, 60);
        
        switch(Scoreboard.getLifeCounter()){
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
