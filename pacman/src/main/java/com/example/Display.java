package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group; //Gruppe
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent; //Keyboard input
import javafx.scene.paint.Color; //Baggrund farve

public class Display {
    Image sheet = new Image(getClass().getResource("/sprite-sheet.png").toString());
    Collections collection = new Collections(sheet);
    int playerFrame = 0;
    int offset = 0;

    Game game;
    int factor = 20;

    Canvas canvas;
    GraphicsContext gc;

    Circle pellet;

    public Display(Game game){
        this.game = game;
        this.canvas = new Canvas(600,500);
        this.gc = canvas.getGraphicsContext2D();


        addWalls();
    }

    private void addWalls(){
        Grid grid = game.getGrid();

        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                if (grid.getEntity(x, y) == Entity.wall){
                    // gc.setFill(Color.BLUE);
                    // gc.fillRect(x*factor,y*factor,factor,factor);
                    
                    collection.getEntitySprite(gc, "walls", 0, x,y, factor);
                }
            }
        }
    }

    public void update() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,600,500);
        updatePellets();
        addWalls();

        Character[] characters = game.getCharacters();
        collection.getCharacterSprite(gc, "pacman", characters[0].getDirection(), playerFrame, characters[0].getPosX(),characters[0].getPosY(), factor, offset);
    }

    private void updatePellets() {
        Grid grid = game.getGrid();
        for (int y = 0; y < grid.getMap().length; y++){
            for (int x = 0; x < grid.getMap()[0].length; x++){
                if (grid.getEntity(x, y) == Entity.pellet){
                    collection.getEntitySprite(gc, "pellet", 0, x,y, factor);
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

    public void setOffest(int playerTime){
        this.offset = ((15 - playerTime)/15)*20;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    private void updatePlayer() {
        
    }

    private void updateGhosts() {
        
    }

}